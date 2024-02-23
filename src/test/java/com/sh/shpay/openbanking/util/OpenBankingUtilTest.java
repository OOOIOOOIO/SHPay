package com.sh.shpay.openbanking.util;


import com.sh.shpay.global.util.openbanking.OpenBankingUtil;
import com.sh.shpay.global.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles("test")
@Slf4j
@SpringBootTest
public class OpenBankingUtilTest {

    @Autowired
    OpenBankingUtil openBankingUtil;

    @Autowired
    RedisUtil redisUtil;

    @Test
//    @Transactional
    public void generateBankTranId(){
        String testBankTranId = "M999999999"; // 10자리

        String bankTranId = openBankingUtil.generateBankTranId(testBankTranId);

        String bankTranIdFromRedis = redisUtil.getByClassType(bankTranId, String.class);

        assertThat(bankTranId).isEqualTo(bankTranIdFromRedis);


    }

}
