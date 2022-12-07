package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문생성
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAdress(member.getAdress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        /** -> Delivery, OrderItem, Order 각각 persist 할 필요 없는 이유는
         * Order 엔터티의 연관관계 CascadeType.ALL 옵션
         * 단, 참조하는 주인 엔터티가 유일, 명확한 경우에만 해당 옵션 사용할 것 */
        orderRepository.saveOrder(order);

        return order.getId();
    }

    // 주문취소


    // 검색
}
