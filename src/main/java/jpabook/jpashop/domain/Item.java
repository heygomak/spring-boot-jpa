package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 상속관계 전략 타입
 *  1) SINGLE_TABLE : 부모 테이블에 모든 자식 테이블 컬럼을 생성 (디폴트)
 *  2) TABLE_PER_CLASS : 모든 자식 테이블에 부모 테이블의 모든 컬럼을 생성
 *  3) JOINED : 모든 자식 클래스에 부모 클래스의 PK를 생성 (가장 많이 사용)
 *
 *  상속관계 테이블 구분자
 *  @DiscriminatorColumn: 구분컬럼명
 *  @DiscriminatorValue: 구분값
 * */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> caterogies = new ArrayList<Category>();
}
