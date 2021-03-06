package com.szj.springcloud.controller;

import com.szj.springcloud.entities.CommonResult;
import com.szj.springcloud.entities.Payment;
import com.szj.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果" + result);
        if (result > 0) {
            return new CommonResult(200, "插入成功"+port, result);
        }
        {
            return new CommonResult(444, "插入失败", null);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment result = paymentService.getPamentById(id);
        log.info("查询结果" + result);
        if (result != null) {
            return new CommonResult(200, "查询成功"+port, result);
        }
        {
            return new CommonResult(444, "查询失败，id： "+id, null);
        }
    }

    @GetMapping("/discoveryClient")
    public Object discoveryClient(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("element: "+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            URI uri = instance.getUri();
            String serviceId = instance.getServiceId();
            log.info(serviceId + "\t" +uri);
        }
        return discoveryClient;
    }

    @GetMapping(value = "/feign/timeout")
    public String paymentFeignTimeout(){
        try { TimeUnit.SECONDS.sleep(3); }catch (Exception e) {e.printStackTrace();}
        return port;
    }

    @GetMapping("/zipkin")
    public String paymentZipkin()
    {
        return "hi ,i'am paymentzipkin server fall back，welcome to atguigu，O(∩_∩)O哈哈~";
    }

}
