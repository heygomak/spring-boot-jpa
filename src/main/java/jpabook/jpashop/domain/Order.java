package jpabook.jpashop.domain;

import jpabook.jpashop.domain.codes.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
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
}
