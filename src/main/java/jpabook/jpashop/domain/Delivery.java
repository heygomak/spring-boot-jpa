package jpabook.jpashop.domain;

import jpabook.jpashop.domain.codes.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Adress adress;

    /**
     * EnumType
     * 1) ORIGINAL: 정수 자동 증감됨. 절대 사용 금지 !! (default)
     * 2) STRING :
     * */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;   // 배송상태 [READY, COMP]
}
