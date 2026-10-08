package com.example.spring_playground.question.q1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class MemberQ1Test {

    @DisplayName("Q1. Entity_생성자_접근_제한_SAME_PACKAGE")
    @Test
    void samePackageConstructorAccessTest() {
        MemberQ1Public memberQ1Public1 = new MemberQ1Public();
        MemberQ1Public memberQ1Public2 = new MemberQ1Public(1L, "홍길동");
        //같은 pakege경로이므로 test code에서 가능함
        MemberQ1Restrict memberQ1Restrict1 = new MemberQ1Restrict();
        //AccessLevel.PRIVATE 설정으로 불가능
//        MemberQ1Restrict memberQ1Restrict2 = new MemberQ1Restrict(1L, "홍길동");

    }

}