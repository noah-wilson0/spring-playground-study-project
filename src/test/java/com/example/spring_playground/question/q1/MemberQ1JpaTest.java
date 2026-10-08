package com.example.spring_playground.question.q1;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberQ1JpaTest {

    @Autowired
    MemberQ1JpaRepository repository;

    @Autowired
    EntityManager em;

    /**
     * @NoArgsConstructor(access = AccessLevel.PRIVATE)로 했는데
     * org.hibernate.InstantiationException 예외 안 뜬다.
     *
     */
    @Test
    @DisplayName("Q1. JPA_Entity_기본_생성자_테스트")
    void noArgsConstructorTest() {

        MemberQ1Jpa member = new MemberQ1Jpa("홍길동");
        MemberQ1Jpa savedMember = repository.save(member);

        em.flush();
        em.clear();

        MemberQ1Jpa findMember =
                repository.findById(savedMember.getId()).orElseThrow();

        assertEquals("홍길동", findMember.getName());
    }

    /**
     * getReference를 사용하면 별도의 설정없이 프록시 객체를 받아볼 수 있다.
     */
    @Test
    @DisplayName("Q1. JPA Entity 프록시 생성 테스트")
    void proxyCreationTest() {

        // given
        MemberQ1Jpa member = new MemberQ1Jpa("홍길동");
        MemberQ1Jpa savedMember = repository.save(member);

        em.flush();
        em.clear();

        // when
        MemberQ1Jpa proxy =
                em.getReference(MemberQ1Jpa.class, savedMember.getId());

        // then
        System.out.println("Proxy Class = " + proxy.getClass());

        // 프록시 초기화
        System.out.println("Member Name = " + proxy.getName());

        assertEquals("홍길동", proxy.getName());
    }
}
