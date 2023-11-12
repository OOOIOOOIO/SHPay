package com.sh.shpay.util.openbanking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
@Slf4j
public class OpenBankingUtil {

    /**
     * 은행 거래 거유번호(bank_tran_id) 뒤 9자 생성
     */
    public static String generateBankTranId(String bank_tran_id){
        Random random = new Random();

        String ranStr = "";
        for(int i = 0; i < 9; i++){
            ranStr += Integer.toString(random.nextInt(10));
        }
        int i = random.nextInt(10);

        return bank_tran_id+ranStr;
    }

    /**
     * 거래시간 제공
     */
    public static String transTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.hh:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");

        return localDateTime.format(dateTimeFormatter);
    }


}
