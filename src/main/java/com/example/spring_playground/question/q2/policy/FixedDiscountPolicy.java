package com.example.spring_playground.question.q2.policy;

import org.springframework.stereotype.Component;

@Component
public class FixedDiscountPolicy implements DiscountPolicy{
    @Override
    public int discount(int price) {
        return 1000;
    }
}
