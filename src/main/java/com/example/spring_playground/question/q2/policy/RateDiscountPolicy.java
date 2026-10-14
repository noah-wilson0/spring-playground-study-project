package com.example.spring_playground.question.q2.policy;

import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    @Override
    public int discount(int price) {
        return price * 10 / 100;
    }
}
