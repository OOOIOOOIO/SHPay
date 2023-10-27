package com.sh.shpay.test.change.domain.Coupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long couponId;
    private String event; // 만약 이벤트가 있다면 나중에 객체로, 우선은 이벤트 이름만
    private String topic;
    private String coupon;
    private int usedFl; // tinyint

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Users users;
//
//    @Builder
//    private Coupon(String event, String topic, String coupon, int usedFl, Users users) {
//        this.event = event;
//        this.topic = topic;
//        this.coupon = coupon;
//        this.usedFl = usedFl;
//        this.users = users;
//    }
//
//    public static Coupon createCoupon(String event, String topic, int usedFl){
//        return Coupon.builder()
//                .event(event)
//                .topic(topic)
//                .coupon(generateCoupon())
//                .usedFl(0)
//                .users(null)
//                .build();
//
//    }

    private static String generateCoupon(){
        return UUID.randomUUID().toString().replace("-", "");
    }


}
