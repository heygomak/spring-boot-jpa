package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // Spring Bean 등록 대상
public class MemberRepository {
    @PersistenceContext
    EntityManager em;

    // 회원 저장
    public Long save(Member member) {
        em.persist(member);
        return null;
    }

    // 회원 단건 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 회원 전체 목록 조회
    public List<Member> findAll() {
        // JPQL (Java Persistence Query Language) : 엔터티 객체를 대상으로 쿼리를 생성
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    // 회원명 조건으로 회원 목록 조회
    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class).
                setParameter("username", username).getResultList();
    }
}