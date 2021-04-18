package com.szj.springcloud.service;

import com.szj.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPamentById(Long id);
}
