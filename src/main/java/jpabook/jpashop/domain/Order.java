package jpabook.jpashop.domain;

import jpabook.jpashop.domain.codes.DeliveryStatus;
import jpabook.jpashop.domain.codes.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// -> protected 생성자와 동일한 역할 (lombok 지원)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // LocalDateTime: Java8 이후는 Hibernate에서 변환하기 떄문에 @Date 안써도됨
    private LocalDateTime orderDate;

    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    // === 연관관계 편의 매서드
    // 양방향 persist 해야할 때 한줄로 가능
    // 생성 위치는 메인 로직을 처리하는 엔터티 쪽
    public void setMember(Member member) {
        this.member = member;
        // 주문이 생성될때 멤버의 오더리스트에도 업데이트
        member.getOrders().add(this);
    }

    public void addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        // 주문이 생성될때 주문상품 테이블에 주문정보 업데이트
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        // 주문이 생성될때 배달 테이블에 주문정보 업데이트
        delivery.setOrder(this);
    }

    // 주문 생성은 여기서만 통제할거야 !! 다른데서 만들지마
    protected Order() {}

    // === 주문 생성매서드
    // * 장점 : 도메인에서 주문 로직을 완결시킴으로써, 주문 생성 유지보수는 여기서만 하면 된다
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItems(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // === 비즈니스로직
    public void cancelOrder() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 주문은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancelOrder();
        }
    }

    // === 조회로직
    // 전체 주문 금액 조회
    public int totalPrice() {
        // 주문 라인 별 합계금액 (주문가격*수량)을 sum 한 것이 최총 주문 가격
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
