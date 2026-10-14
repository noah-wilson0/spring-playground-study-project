package com.example.spring_playground.question.q2.service;

import com.example.spring_playground.question.q2.policy.DiscountPolicy;

public class Q2OrderService {

    private final DiscountPolicy discountPolicy;

    public Q2OrderService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public int calculatePrice(int price) {
        return price - discountPolicy.discount(price);
    }
}
