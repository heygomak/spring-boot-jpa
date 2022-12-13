package jpabook.jpashop.repository;

import jpabook.jpashop.domain.codes.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@Getter @Setter

public class OrderSearch {
    private String userName;
    private OrderStatus orderStatus;

}