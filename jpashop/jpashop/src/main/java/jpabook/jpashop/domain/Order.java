package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //다대일에서 다쪽이 foreign key가 됨
    //@XToOne은 반드시 fetch를 LAZY로 설정해주어야 함!!
    @JoinColumn(name = "member_id")//Member 테이블의 member_id를 참조
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)//Order테이블의 order에 의해 참조됨
    //cascade를 하면 Order에 업데이트 되는 내용을 orderItems에 전파해주어 관련 내용을 똑같이 업데이트 해줌
    private List<OrderItem> orderItems=new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id ")//1:1관계에서 방향성을 따져서 FK설정
    private Delivery delivery;

    private LocalDateTime localDateTime;//주문시간, 제공클래스

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;//주문상태 [ORDER, CANCEL]

    //==양방향관계에서의 연관관계 메서드==// //핵심적으로 컨트롤하고 있는 쪽에 써준다
    public void setMember(Member member) {
        this.member = member;//order 입장에서 멤버설정
        member.getOrders().add(this); //member입장에서 order내용을 가져와서 추가하기
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem); //order입장에서 orderItem을 추가하기
        orderItem.setOrder(this); //orderItem입장에서 주문한 내용 설정하기

    }

    public void setDelivery(Delivery delivery) {
        this.delivery=delivery; //
        delivery.setOrder(this); //
    }

    //==생성메서드==//
    public static Order createOrder(Member member,Delivery delivery, OrderItem... orderItems){
        Order order=new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);//돌리면서 추가
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /* 주문취소*/
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException(("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /* 전체 주문 가격 조회*/
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
