package com.sh.shpay.domain.acconut.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    MAIN("MAIN"),
    SUB("SUB");

    private final String type;
}
