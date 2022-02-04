package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //어딘가에 내장될 수 있음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { //상속x, 값 변경을 하지 않는 클래스-> protected로 설정가능
    }

    //생성자의 값 타입 변경 불가능한 클래스 생성!
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }



}
