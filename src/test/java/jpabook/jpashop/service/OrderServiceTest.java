package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Adress;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.codes.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Rollback(false)
    public void 주문생성() throws Exception {
        //given
        Member member = getMember();
        Book book = getBook(12000, 100);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order newOrder = orderRepository.findOne(orderId);

        //then
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, newOrder.getStatus());
        assertEquals("주문한 상품 종류 수", 1, newOrder.getOrderItems().size());
        assertEquals("주문 가격은 상품 가격 * 수량이다", 12000 * orderCount, newOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다", 98, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 주문생성_재고수량초과() throws Exception {
        //given
        Member member = getMember();
        Book book = getBook(12000, 100);

        //when
        int orderCount = 101;
        orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고수량부족 예외(NotEnoughStockException)가 발생해야한다");
    }
    
    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = getMember();
        Book book = getBook(12000, 100);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order exOrder = orderRepository.findOne(orderId);
        assertEquals("취소된 주문의 상태는 CANCEL", OrderStatus.CANCEL, exOrder.getStatus());
        assertEquals("주문이 취소된 상품의 재고가 원복되어야한다", 100, book.getStockQuantity());
    }

    @Test
    public void 주문검색() throws Exception {
        //given

        //when

        //then
    }

    private Book getBook(int price, int stockQuantity) {
        Book book = new Book();
        book.setName("텃밭백과");
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setUsername("동동이11");
        member.setAddress(new Address("서울", "중구", "123-123"));
        em.persist(member);
        return member;
    }
}