package com.szj.springcloud.service.impl;

import com.szj.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "feign 的服务降级_paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "feign 的服务降级_paymentInfo_TimeOut";
    }
}
