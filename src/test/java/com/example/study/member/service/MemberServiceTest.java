package com.example.study.member.service;

import com.example.study.member.domain.Member;
import com.example.study.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("Kim");

        Long savedId = memberService.join(member);

        assertEquals(member,memberRepository.findOne(savedId));
    }
    @Test
    public void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("Kim");

        Member member2 = new Member();
        member2.setName("Kim");

        memberService.join(member1);
        assertThrows(IllegalStateException.class,()->{
            memberService.join(member2);
        });
    }
}