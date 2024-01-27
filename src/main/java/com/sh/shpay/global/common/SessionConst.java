package com.sh.shpay.global.common;

public enum SessionConst {
    ADMIN_USER("admin"),
    COMMON_USER("user");
    private String rule;

    SessionConst(String rule) {
        this.rule = rule;
    }

    public String getRule(){
        return rule;
    }
}

