package com.example.spring_playground.question.q2.service;

import com.example.spring_playground.question.q2.config.RateDiscountConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(RateDiscountConfig.class)
@ActiveProfiles("rate")
class RateDiscountTest {

    @Autowired
    private Q2OrderService orderService;

    @Test
    void rateDiscount() {
        int result = orderService.calculatePrice(20000);

        assertThat(result).isEqualTo(18000);
    }
}
