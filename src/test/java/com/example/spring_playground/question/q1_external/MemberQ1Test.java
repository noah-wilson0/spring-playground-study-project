package com.example.spring_playground.question.q1_external;

import com.example.spring_playground.question.q1.MemberQ1Public;
import com.example.spring_playground.question.q1.MemberQ1Restrict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberQ1Test {

    @DisplayName("Q1. Entity_생성자_접근_제한_EXTERNAL_Entity_생성자_접근_제한_SAME_PACKAGE")
    @Test
    void externalPackageConstructorAccessTest() {
        MemberQ1Public memberQ1Public1 = new MemberQ1Public();
        MemberQ1Public memberQ1Public2 = new MemberQ1Public(1L, "홍길동");
        // protected 생성자: 다른 패키지에서는 접근 불가
//        MemberQ1Restrict memberQ1Restrict1 = new MemberQ1Restrict()
        // private 생성자: 해당 클래스 외부에서는 접근 불가
//        MemberQ1Restrict memberQ1Restrict2 = new MemberQ1Restrict(1L, "홍길동");

        // public: 개발자가 정의한 안전한 생성자를 사용한다.
        MemberQ1Restrict member = MemberQ1Restrict.builder()
                .name("홍길동")
                .build();



    }

}