package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void join() throws Exception {
        Member member = new Member();
        member.setUsername("둥두니");
        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));  // 동일한 트랜잭션 안에서 ID값이 같으면 같은 영속성 컨텍스트 보장
    }

    @Test
    public void validateDuplicatedMember() throws Exception{
        Member member1 = new Member();
        member1.setUsername("둥두니");
        Member member2 = new Member();
        member2.setUsername("둥두니");

        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        fail("정상적으로 Exception 떨어지지 않음...");
    }
}