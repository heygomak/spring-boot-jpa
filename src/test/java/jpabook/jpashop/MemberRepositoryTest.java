package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

// Ctrl + Shift + T : 테스트 클래스 자동생성
@RunWith(SpringRunner.class)    //-> JUnit 에게 알려주기 위함
@SpringBootTest
public class MemberRepositoryTest {
    // Entity Manager 를 통한 모든 데이터 변경은 트랜잭션 안에서 일어나야함 !!
    @Autowired MemberRepository memberRepository;

    @Test             // 껍데기는 Settings > Live Templates 에 저장 (tdd + tab 단축키)
    @Transactional    // @Test와 함께쓰면 바로 rollback 시킨다
    @Rollback(false)  // rollback 시키지 않으려면 해당 어노테이션 선언
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("둥두니하또");

        //when
        Long saveID = memberRepository.save(member);
        Member findMember = memberRepository.find(saveID);

        //then
        // Assertions : 검증 Library
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }


}