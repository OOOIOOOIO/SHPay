package com.sh.shpay.global.util.openbanking;

import com.sh.shpay.global.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenBankingUtil {

    private final RedisUtil redisUtil;

    /**
     * 은행 거래 거유번호(bank_tran_id) 뒤 9자 생성
     */
    public String generateBankTranId(String bank_tran_id){

        String result = "";
        Random random = new Random();

        while(true){
            result = "";
            String ranStr = "";
            for(int i = 0; i < 9; i++){
                ranStr += Integer.toString(random.nextInt(10));
            }
    //        int i = random.nextInt(10);

            result = bank_tran_id + ranStr;

            // redis에 쏘고 확인하기
            if(!redisUtil.isExists(result)){
                redisUtil.putString(result, new String(result), getRestTime());
                break;
            }


        }

        return result;
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


    private Long getRestTime(){
        long endOfDayTime = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
        long currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toEpochSecond(ZoneOffset.UTC);

        return endOfDayTime - currentTime;
    }

}
