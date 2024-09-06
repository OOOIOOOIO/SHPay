package com.sh.shpay.global.util.komoran.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalyzeResultDto {

    private boolean isPrivacy;
    private boolean isSpecificBank;
    private String bankName;
    private String sentence;

    public AnalyzeResultDto(boolean isPrivacy, boolean isSpecificBank, String bankName) {
        this.isPrivacy = isPrivacy;
        this.isSpecificBank = isSpecificBank;
        this.bankName = bankName;
    }
}
