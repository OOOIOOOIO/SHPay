package com.sh.shpay.global.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtTokenExpiredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JwtTokenExpiredException(String message) {
        super(message);

    }
}
