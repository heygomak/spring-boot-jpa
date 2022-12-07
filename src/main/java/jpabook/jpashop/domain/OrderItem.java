package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_itme_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    // 주문상품 생성은 여기서만 통제할거야 !! 다른데서 만들지마
    protected OrderItem() {}

    // === 주문상품 생성매서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        /** Item 엔터티의 price 필드를 사용하지 않는 것은
        할인 등의 로직이 생길 것을 고려해 별도 관리 위함 */
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // === 비즈니스로직
    public void cancelOrder() {
        getItem().addStock(count);
    }

    // === 조회로직
    // 주문 상품 전체 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
