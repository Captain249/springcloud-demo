package com.szj.springcloud.service.impl;

import com.szj.springcloud.dao.PaymentDao;
import com.szj.springcloud.entities.Payment;
import com.szj.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPamentById(Long id) {
        return paymentDao.getPamentById(id);
    }

}
