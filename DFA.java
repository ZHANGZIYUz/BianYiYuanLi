package com.whut.homework;

import java.util.ArrayList;
import java.util.List;

public class DFA {
    public List<String> dfaStatement = new ArrayList<>();    // 状态集
    public List<String> dfaChars = new ArrayList<>();    // 字母表
    public String[][] dfaTransf;       // 转换函数
    public List<String> dfaStart = new ArrayList<>();    // 初态集
    public List<String> dfaEnd = new ArrayList<>();    // 终态集

    public List<String> getDfaStatement() {
        return dfaStatement;
    }

    public void setDfaStatement(List<String> dfaStatement) {
        this.dfaStatement = dfaStatement;
    }

    public List<String> getDfaChars() {
        return dfaChars;
    }

    public void setDfaChars(List<String> dfaChars) {
        this.dfaChars = dfaChars;
    }

    public String[][] getDfaTransf() {
        return dfaTransf;
    }

    public void setDfaTransf(String[][] dfaTransf) {
        this.dfaTransf = dfaTransf;
    }

    public List<String> getDfaStart() {
        return dfaStart;
    }

    public void setDfaStart(List<String> dfaStart) {
        this.dfaStart = dfaStart;
    }

    public List<String> getDfaEnd() {
        return dfaEnd;
    }

    public void setDfaEnd(List<String> dfaEnd) {
        this.dfaEnd = dfaEnd;
    }
}