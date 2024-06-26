package com.sh.shpay.domain.openbanking.openbanking.dto.res;

import com.sh.shpay.domain.openbanking.openbanking.dto.OpenBankingAccountDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingAccountListResponseDto {
    private String api_tran_id;
    private String rsp_code;
    private String rsp_message;
    private String api_tran_dtm;
    private String user_name;
    private String res_cnt;
    private List<OpenBankingAccountDto> res_list;
}
