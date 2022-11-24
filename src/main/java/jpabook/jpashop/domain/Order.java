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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // LocalDateTime: Java8 이후는 Hibernate에서 변환하기 떄문에 @Date 안써도됨
    private LocalDateTime orderDate;

    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

}
