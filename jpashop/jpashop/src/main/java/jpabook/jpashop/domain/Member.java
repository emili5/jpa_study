package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue//@Id가 있어야 엔티티 식별가능
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입을 쓰겠다
    private Address address;

    @OneToMany(mappedBy = "member")//orders의 member 속성에 참조됨
    private List<Order> orders= new ArrayList<>();




}
