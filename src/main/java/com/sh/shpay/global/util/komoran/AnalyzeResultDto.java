package com.sh.shpay.global.util.komoran;

import lombok.Getter;

@Getter
public class AnalyzeResultDto {

    private boolean isPrivacy;
    private boolean isSpecificBank;
    private String bankName;

    public AnalyzeResultDto(boolean isPrivacy, boolean isSpecificBank, String bankName) {
        this.isPrivacy = isPrivacy;
        this.isSpecificBank = isSpecificBank;
        this.bankName = bankName;
    }
}
