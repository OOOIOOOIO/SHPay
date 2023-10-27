package com.sh.shpay.test.change.domain.Coupon.api;

import com.sh.shpay.test.change.util.pubsub.PubSubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {


    private final PubSubService pubSubService;
    private final ChannelTopic topic;


    // admin으로 보내기
    // publish, 다른 서버에게 쿠폰 발급하라는 뜻
    // 예를 들면 11월 1일전 까지 구독한 구독자들한테 쿠폰을 뿌리겠다.
    // 이후 만약 구독을 하면 바로 쿠폰이 나가는 느낌으로다가 할 거면 트리거를 써야겠지? pub sub이랑은 다른 느낌
    // 쿠폰을 발급한 내역이 있는지 유효성 체크하고, 만약 없으면 redis에서 쿠폰 발급
    @PostMapping("/pub/{event}")
    public void generateCoupon(@PathVariable(value = "event") String event,
                               @RequestParam(value = "message") String message){

        // event 추가
        pubSubService.publish(topic, message);

        //여기서 어떻게 쿠폰을 나눠줄 지 봐야겠다


    }

    // 이벤트 구독
    @PostMapping("/sub/{event}")
    public void subscribeChannel(@PathVariable(value = "event") String event){
        pubSubService.subscribeChannel(event);
    }

    // 이번트 구독 취소
    @PostMapping("/ussub/{event}")
    public void unsubscribeChannel(@PathVariable(value = "event") String event){
        pubSubService.unsubscribeChannel(event);
    }



    //쿠폰 사용
    // coupon으로 할
    @DeleteMapping("/use/{couponId}")
    public void useCoupon(@PathVariable(value = "couponId") Long couponId){


    }


    // admin으로 보내기
    @PostMapping("/gen/{event}")
    public void genNewTopic(@PathVariable(value = "event") String event) {

        pubSubService.genNewTopic(new ChannelTopic(event));
    }
}
