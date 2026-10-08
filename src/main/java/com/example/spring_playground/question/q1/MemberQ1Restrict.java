package com.example.spring_playground.question.q1;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_q1_restrict")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberQ1Restrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public MemberQ1Restrict(String name) {
        this.name = name;
    }
}
