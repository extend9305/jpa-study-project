package com.example.study;

import com.example.study.member.domain.Member;
import com.example.study.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Test
    @Transactional
    public void testMember(){
//        Member member = new Member();
//        member.setUsername("TEST1");
//
//        memberRepository.save(member);
//
//        Member findMember = memberRepository.find(member.getId());
//
//        Assertions.assertThat(findMember.getId().equals(member.getId()));
//        Assertions.assertThat(findMember.getUsername().equals(member.getUsername()));

    }


}
