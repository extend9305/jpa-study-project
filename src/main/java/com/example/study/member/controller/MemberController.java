package com.example.study.member.controller;

import com.example.study.member.domain.Member;
import com.example.study.member.repository.MemberRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @RequestMapping("/member/save")
    public void printMember(){
        Member member = new Member();

//        member.setAge(23);
//        member.setUsername("leedongsu");

        memberRepository.save(member);

    }
}
