package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    // 값타입 변경 불가능하도록 하기 위해 초기화 작업, @Setter 제거
    // JPA 구현 라이브러리가 객체 생성할 때 리플렉션, 프록시 등을 사용할 수 있게 해야하기 떄문
    protected Address() {}

    private String city;

    private String street;

    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
