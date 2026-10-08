package com.example.spring_playground.question.q1;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_q1_jpa")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*
//@NoArgsConstructor 미 사용시
org.hibernate.InstantiationException  에러 발생.
 */
/*
//@NoArgsConstructor(access = AccessLevel.PRIVATE) 사용시
org.hibernate.InstantiationException  에러가 발생하지 않는다.
단순 저장-조회에서 JPA 프록시가 사용되지 않으며
hibernate는  NoArgsConstructor과 관계없다.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberQ1Jpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public MemberQ1Jpa(String name) {
        this.name = name;
    }
}