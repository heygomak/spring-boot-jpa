package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    // @GeneratedValue: 자동 생성
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    // @Embedded: 내장타입 컬럼 명시
    @Embedded
    private Address address;

    // mappedBy: Orders 테이블의 member 컬럼에 의해 맵핑된 컬럼 명시 (읽기전용)
    @OneToMany(mappedBy  = "member")
    private List<Order> orders = new ArrayList<Order>();
}
