package com.whut.homework;

import java.util.ArrayList;
import java.util.List;

public class NFA {
    public List<String> nfaStatement = new ArrayList<>();    // 状态集
    public List<String> nfaChars = new ArrayList<>();    // 字母表
    public String[][] nfaTransf;       // 转换函数
    public List<String> nfaStart = new ArrayList<>();    // 初态集
    public List<String> nfaEnd = new ArrayList<>();    // 终态集

    public List<String> getNfaStatement() {
        return nfaStatement;
    }

    public void setNfaStatement(List<String> nfaStatement) {
        this.nfaStatement = nfaStatement;
    }

    public List<String> getNfaChars() {
        return nfaChars;
    }

    public void setNfaChars(List<String> nfaChars) {
        this.nfaChars = nfaChars;
    }

    public String[][] getNfaTransf() {
        return nfaTransf;
    }

    public void setNfaTransf(String[][] nfaTransf) {
        this.nfaTransf = nfaTransf;
    }

    public List<String> getNfaStart() {
        return nfaStart;
    }

    public void setNfaStart(List<String> nfaStart) {
        this.nfaStart = nfaStart;
    }

    public List<String> getNfaEnd() {
        return nfaEnd;
    }

    public void setNfaEnd(List<String> nfaEnd) {
        this.nfaEnd = nfaEnd;
    }
}
