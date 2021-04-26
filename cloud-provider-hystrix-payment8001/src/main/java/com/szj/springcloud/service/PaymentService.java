package com.szj.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    //成功
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_OK,id：  " + id + "\t" + "哈哈哈";
    }

    //超时
    @HystrixCommand(fallbackMethod = "paymentInfo_timeOutHandler", commandProperties = {//出了问题 去找这个方法
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")//超过3秒就是不正常
    })
    public String paymentInfo_TimeOut(Integer id) {
        //int i = 10 / 0;
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_TimeOut,id：  " + id + "\t" + "呜呜呜" + " 耗时(秒)" + timeNumber;
        //return "报错";
    }

    public String paymentInfo_timeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "系统繁忙，请稍后再试";
    }

    //在10秒内 如果10次请求有60%失败 就会跳闸
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id <0){
            throw new RuntimeException("id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() +" 调用成功，流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试";
    }


}
