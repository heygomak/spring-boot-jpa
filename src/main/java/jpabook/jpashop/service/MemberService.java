package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/** Transactional
 * - 클래스 레벨에 선언하면 public 매서드에는 모두 적용
 * - 읽기전용 옵션 붙히면 JPA 조회 성능을 최적화 시킬 수 있음 (디폴트는 false)
 * */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    /* 1. Field Injection : Spring Bean 에 등록되어있는 MemberRepository 주입
    @Autowired
    private MemberRepository memberRepository; **/

    /* 2. Setter Injection : 테스트 코드 작성 등 레파지토리 직접 주입
    private MemberRepository memberRepository;
    @Autowired
    private void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } **/

    /* 3. Constructor Injection : 서비스에 하나의 생성자만 있으면 자동으로 @Autowired 시켜줌
    private final MemberRepository memberRepository;    // 변경할 일 없으므로 final, 없으면 컴파일시 걸림

    private MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }**/

    // 4. Constructor Injection By Lombok : @RequiredArgsConstructor 를 통해 final 필드의 생성자 자동생성
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        // 서비스에서 검증하더라도 멀티쓰레드 상황을 고려해 테이블의 PK or UK로 지정해 최후 방어 할것
        validateDuplicatedMember(member);
        // persist 실행시 영속성 컨텍스트에 Key 값인 ID가 반드시 올라감이 보장됨
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 체크 로직
    private void validateDuplicatedMember(Member member) {
        List<Member> findMember = memberRepository.findByName(member.getUsername());
        if(findMember.size() > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 회원 목록 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원명으로 회원 목록 조회
    public List<Member> findByName(String username) {
        return memberRepository.findByName(username);
    }
}
