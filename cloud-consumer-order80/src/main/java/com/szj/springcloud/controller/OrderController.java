package com.szj.springcloud.controller;

import com.szj.springcloud.entities.CommonResult;
import com.szj.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";
    //负载均衡和@LoadBalanced一起使用
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin()
    {
        String result = restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin", String.class);
        return result;
    }


}
