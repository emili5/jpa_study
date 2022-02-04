package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//전략을 부모 클래스에서 잡아줌, SINGLE TALbLE은 한 테이블에 다 넣는 전략
@DiscriminatorColumn(name ="dtype" )
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany
    @JoinTable(name = "category_item", //중간 테아블
        joinColumns = @JoinColumn(name = "category_id"), // 중간테이블의 하나는 이 속성에
            inverseJoinColumns = @JoinColumn(name = "item_id") //하나는 이 속성에 연결
    )
    private List<Category> categories =new ArrayList<>();

    //==비즈니스 로직==//
    public void addStock(int quantity){
        this.stockQuantity +=quantity;
    }

    public void removeStock(int quantity){
       int restStock= this.stockQuantity -quantity;
       if(restStock<0){
           throw new NotEnoughStockException("need more stock");
       }
       this.stockQuantity=restStock;
    }
}
