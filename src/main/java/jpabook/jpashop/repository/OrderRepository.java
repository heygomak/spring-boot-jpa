package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void saveOrder(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        /** JPA 동적 쿼리 생성
         * 1. 문자열 체크해서 조립 
         *      -> 노가다, 버그 발생 확률 높음
         * 2. JPA Criteria(JPQL을 자바코드로 생성할 수 있게 지원하는 JPA 표준 스펙) 
         *      -> 코드상에서 어떤 쿼리가 생성되는지 파악하기 어려움
         * 3. QueryDSL
         * */
//        QOrder order = new QOrder.order;
//        QMember member = new QMember.member;
        return em.createQuery("select o from Order o join o.member", Order.class)
                .setMaxResults(30)
                .getResultList();
    }
}
