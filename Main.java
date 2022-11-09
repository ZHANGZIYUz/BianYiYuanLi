package com.whut.homework;

import java.io.IOException;
import java.util.*;

public class Main {
    public static List<String> nfaStatement = new ArrayList<>();    // 状态集
    public static List<String> nfaChars = new ArrayList<>();    // 字母表
    public static List<String> statenfaChars = new ArrayList<>();    // 字母表
    public static String[][] nfaTransf;       // 转换函数
    public static List<String> nfaStart = new ArrayList<>();    // 初态集
    public static List<String> nfaEnd = new ArrayList<>();    // 终态集

    public static List<String> dfaStatement = new ArrayList<>();    // 状态集
    public static List<String> dfaChars = new ArrayList<>();    // 字母表
    public static String[][] dfaTransf;       // 转换函数
    public static List<String> dfaStart = new ArrayList<>();    // 初态集
    public static List<String> dfaEnd = new ArrayList<>();    // 终态集

    public static void main(String[] args) throws IOException {
        DFA dfa = new DFA();
        NFA nfa = new NFA();

        StringBuffer stringBufferFinal = new StringBuffer();
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String s = input.nextLine();
            s = s.trim();
            stringBufferFinal.append(s);
            stringBufferFinal.append(" ");
        }
        String string = new String(stringBufferFinal);
        init(string);

        nfa.setNfaStart(nfaStart);
        nfa.setNfaEnd(nfaEnd);
        nfa.setNfaStatement(nfaStatement);
        nfa.setNfaChars(nfaChars);
        nfa.setNfaTransf(nfaTransf);
        dfaStart = nfa.nfaStart;
        dfaEnd = nfa.nfaEnd;
        String[][] diagram = change("X");
        print(diagram);
    }

    public static void init(String string) {
        int flag = 0;
        int flagbreak = 0;
        int i = 1, j = 1;
        String[] strings = string.split(" ");
        nfaStart.add(strings[0]);
        nfaEnd.add(strings[2]);
        String begin, middle, end;
        for (String s : strings) {
            if (s.length() == 1) {
                nfaStatement.add(s);
            } else if (s.length() == 0) {
                continue;
            } else {
                String s1 = s.substring(2, 3);
                Iterator<String> iterator = nfaChars.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().equals(s1)) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    nfaChars.add(s1);
                }
                if (!s1.equals("~") && flag == 0) {
                    statenfaChars.add(s1);
                }
                flag = 0;
            }
        }

        nfaTransf = new String[nfaStatement.size() + 1][nfaChars.size() + 1];
        Iterator<String> iterator1 = nfaStatement.iterator();
        while (iterator1.hasNext()) {
            nfaTransf[i][0] = iterator1.next();
            i++;
        }
        Iterator<String> iterator2 = nfaChars.iterator();
        while (iterator2.hasNext()) {
            nfaTransf[0][j] = iterator2.next();
            j++;
        }
        for (String s : strings) {
            flagbreak = 0;
            if (s.length() != 1) {
                begin = s.substring(0, 1);
                middle = s.substring(2, 3);
                end = s.substring(5);
                for (int m = 0; m <= nfaStatement.size(); m++) {
                    if (begin.equals(nfaTransf[m][0])) {
                        for (int n = 0; n <= nfaChars.size(); n++) {
                            if (middle.equals(nfaTransf[0][n])) {
                                if (nfaTransf[m][n] == null) {
                                    nfaTransf[m][n] = end;
                                    flagbreak = 1;
                                    break;
                                } else {
                                    StringBuffer temp = new StringBuffer(nfaTransf[m][n]);
                                    temp.append(end);
                                    nfaTransf[m][n] = end;
                                    flagbreak = 1;
                                    break;
                                }
                            }
                        }
                        if (flagbreak == 1) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public static String closure(String target, String procedure) {
        StringBuffer temp = new StringBuffer(target);
        Queue<String> resp = new LinkedList<>();
        resp.add(target);
        for (int i = 0; i <= nfaStatement.size(); i++) {
            if (target.equals(nfaTransf[i][0])) {
                for (int j = 0; j <= nfaChars.size(); j++) {
                    if (procedure.equals(nfaTransf[0][j])) {
                        if (nfaTransf[i][j] != null) {
                            target = nfaTransf[i][j];
                            temp.append(nfaTransf[i][j]);
                            resp.add(nfaTransf[i][j]);
                        }
                    }
                }
                resp.remove();
            }
            if (resp.isEmpty()) {
                break;
            }
        }
        return temp.toString();
    }

    public static String stateClosure(String target, String procedure) {
        for (int i = 0; i <= nfaStatement.size(); i++) {
            if (target.equals(nfaTransf[i][0])) {
                for (int j = 0; j <= nfaChars.size(); j++) {
                    if (procedure.equals(nfaTransf[0][j])) {
                        if (nfaTransf[i][j] != null) {
                            return nfaTransf[i][j];
                        }
                    }
                }

            }
        }
        return null;
    }

    public static String[][] change(String nfaStart) {
        int flag = 1;
        int flagCheck = 0;
        Queue<String> resp = new LinkedList<>();
        char[] initChar;
        int diagramRow = 1;
        String[][] diagram = new String[10][statenfaChars.size() + 1];
        Iterator<String> iterator1 = statenfaChars.iterator();
        int index = 1;
        while (iterator1.hasNext()) {
            diagram[0][index] = iterator1.next();
            index++;
        }
        index = -1;
        StringBuffer[] state = new StringBuffer[statenfaChars.size()];
        String initString = closure(nfaStart, "~");
        if (initString != null) {
            resp.add(initString);
        } else {
            resp.add(nfaStart);
        }
        while (!resp.isEmpty()) {
            String temp = resp.remove();
            initChar = temp.toCharArray();
            diagram[diagramRow][0] = temp;
            for (int i = 0; i < initChar.length; i++) {
                Iterator<String> iterator = statenfaChars.iterator();
                while (iterator.hasNext()) {
                    index++;
                    String t = iterator.next();
                    String closure = stateClosure(String.valueOf(initChar[i]), t);
                    if (closure == null) {
                        continue;
                    }
                    closure = closure(closure, "~");
                    //System.out.println("!!!" + closure + "!!!");
                    if (flag == 1 || flag == 2) {
                        state[index] = new StringBuffer(" ");
                        flag++;
                    }
                    if (closure.length() == 1) {
                        if ((state[index].indexOf(closure) == -1)) {
                            state[index].append(closure);
                        }
                    } else {
                        char[] closureChar = closure.toCharArray();
                        for (int j = 0; j < closureChar.length; j++) {
                            if ((state[index].indexOf(String.valueOf(closureChar[j]))) == -1) {
                                state[index].append(closureChar[j]);
                            }
                        }
                    }

                }
                index = -1;
            }
            for (int j = 1; j <= statenfaChars.size(); j++) {
                if (state[j - 1] != null) {
                    diagram[diagramRow][j] = state[j - 1].substring(1).toString();
                }
            }
            index = -1;
            for (int n = 0; n < state.length; n++) {
                for (int m = diagramRow - 1; m > 0; m--) {
                    for (int o = 0; o < nfaChars.size(); o++) {
                        //System.out.println(state[n].substring(1).toString().equals((diagram[m][o])));
                        if (state[n] != null && state[n].substring(1).toString().equals((diagram[m][o]))) {
                            flagCheck = 1;
                            break;
                        }
                    }
                }
                //System.out.println(state[n]);
                if (flagCheck == 0 && state[n] != null) {
                    resp.add(state[n].substring(1).toString());
                }
                flagCheck = 0;
            }
            for (int i = 0; i < state.length; i++) {
                state[i] = null;
            }
            flagCheck = 0;
            flag = 1;
            diagramRow++;
        }
        return diagram;
    }

    public static void print(String[][] diagram) {
        int row = 0;
        String[][] finalDiagram;
        List<String> newStatement = new ArrayList<>();
        String[] name;
        int flag = 0;
        for (int i = 1; i < diagram.length; i++) {
            if (diagram[i][0] == null) {
                row = i;
                break;
            }
        }
        finalDiagram = new String[row][diagram[0].length];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < diagram[0].length; j++) {
                finalDiagram[i][j] = diagram[i][j];
            }
        }
        for (int i = 1; i < finalDiagram.length; i++) {
            for (int j = 0; j < finalDiagram[0].length; j++) {
                Iterator<String> iterator = newStatement.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (finalDiagram[i][j] != null && finalDiagram[i][j].equals(next)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0 && finalDiagram[i][j] != null) {
                    newStatement.add(finalDiagram[i][j]);
                }
                flag = 0;
            }
        }
        name = new String[newStatement.size()];
        Iterator<String> iterator = newStatement.iterator();
        int number = 0;
        while (iterator.hasNext()) {
            name[number] = iterator.next();
            number++;
        }
        int numberY = 0;
        for (int i = 0; i < name.length; i++) {
            if (name[i].indexOf("Y") != -1) {
                numberY++;
            }
        }
        if (numberY == 1) {
            for (int i = 1; i < finalDiagram.length; i++) {
                for (int j = 0; j < finalDiagram[0].length; j++) {
                    for (int m = 0; m < name.length; m++) {
                        if (name[m].equals(finalDiagram[i][j]) && m == 0) {
                            finalDiagram[i][j] = "X";
                        } else if (name[m].equals(finalDiagram[i][j]) && m == newStatement.size() - 1) {
                            finalDiagram[i][j] = "Y";
                        } else if (name[m].equals(finalDiagram[i][j]) && m != 0 && m != newStatement.size() - 1) {
                            finalDiagram[i][j] = String.valueOf(m - 1);
                        }
                    }
                }
            }
        } else {
            for (int i = 1; i < finalDiagram.length; i++) {
                for (int j = 0; j < finalDiagram[0].length; j++) {
                    for (int m = 0; m < name.length; m++) {
                        if (name[m].equals(finalDiagram[i][j]) && m == 0) {
                            finalDiagram[i][j] = "X";
                        } else if (name[m].equals(finalDiagram[i][j]) && m == newStatement.size() - 1) {
                            finalDiagram[i][j] = "Y1";
                        } else if (name[m].equals(finalDiagram[i][j]) && m == newStatement.size() - 2) {
                            finalDiagram[i][j] = "Y";
                        } else if (name[m].equals(finalDiagram[i][j]) && m != 0 && m != newStatement.size() - 1 && m != newStatement.size() - 2) {
                            finalDiagram[i][j] = String.valueOf(m - 1);
                        }
                    }
                }
            }
        }
        int begin = 1;
        System.out.print("X ");
        for (int i = 1; i <= statenfaChars.size(); i++) {
            if (finalDiagram[begin][i] != null) {
                System.out.print("X-" + finalDiagram[0][i] + "->" + finalDiagram[begin][i] + " ");
            }
        }
        System.out.println();
        int end = finalDiagram.length - 1;
        for (int i = 0; i < finalDiagram.length; i++) {
            if (finalDiagram[i][0] == "Y") {
                end = i;
            }
        }
        System.out.printf("Y ");
        for (int i = 1; i <= statenfaChars.size(); i++) {
            if (finalDiagram[end][i] != null) {
                System.out.print("Y-" + finalDiagram[0][i] + "->" + finalDiagram[end][i] + " ");
            }
        }
        System.out.println();
        for (int i = 2; i < finalDiagram.length; i++) {
            if(finalDiagram[i][0]!="Y"){
                System.out.print(finalDiagram[i][0] + " ");
                for (int j = 1; j <= statenfaChars.size(); j++) {
                    if (finalDiagram[i][j] != null) {
                        System.out.print(finalDiagram[i][0] + "-" + finalDiagram[0][j] + "->" + finalDiagram[i][j] + " ");
                    }
                }
                System.out.println();
            }
        }
    }
}
