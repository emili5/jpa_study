package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name ="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded //Address는 내장타입이므로 이 어노테이션 써야됨
    private  Address address;

    //Enum타입은 디폴트가 ORDINAL인데 얘는 숫자로 기록됨
    //숫자면 READY, COMP의 두 상태에서 다른 하나가 추가되었을 때
    // 기존 숫자랑 헷갈려지게 되므로 반드시 STRING으로 써줘야 한다!!!
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; //READY,COMP
}
