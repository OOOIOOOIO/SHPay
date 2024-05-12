package com.sh.shpay.domain.openbanking.openbanking.api.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Test {

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());


        LocalDate start = LocalDate.of( 2023, 3, 3);
        LocalDate end = LocalDate.of( 2023, 3, 1);


        /**
         * 시작일 < 알람일 = +1
         * 시작일 > 알람일 = 0
         *
         * 알람일 < 목표일 = +1
         * 알람일 > 목표일 = 0
         *
         * 시작일의 다음달 ~ 목표일의 전달 = +1씩
         *
         * Progress를 성공 유무로 하는 게 맞나.
         *
         */
// ChronoUnit 을 이용한 두 날짜 사이 간격 구하기
        long diffMonth = ChronoUnit.MONTHS.between( start, end ); // 14
        System.out.println("diffMonth = " + diffMonth);
        long diffWeek = ChronoUnit.WEEKS.between( start, end ); // 65
        System.out.println("diffWeek = " + diffWeek);
        long diffDay = ChronoUnit.DAYS.between( start, end ); // 456

        System.out.println("diffDay = " + diffDay);
    }
}
