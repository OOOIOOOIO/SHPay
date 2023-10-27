package com.sh.shpay.test.change.domain.Coupon.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    //쿠폰저장
    //쿠폰삭제 BW
    //유효성체크

}
