package com.example.spring_playground.question.q2.service;

import com.example.spring_playground.question.q2.config.FixedDiscountConfig;
import com.example.spring_playground.question.q2.config.RateDiscountConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(FixedDiscountConfig.class)
@ActiveProfiles("fixed")
class FixedDiscountTest {

    @Autowired
    private Q2OrderService orderService;

    @Test
    void fixedDiscount() {
        int result = orderService.calculatePrice(20000);

        assertThat(result).isEqualTo(19000);
    }
}