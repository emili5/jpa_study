package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){ //상품저장
        if (item.getId() == null) {//id가 없으면 완전히 새로운 값으로 persist로 새롭게 등록
            em.persist(item);

        }else{ //기존에 있으면 그냥 merge..뒤에서 자세히 설명
            em.merge(item);
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    } //상품 하나 찾기

    public List<Item> FindAll(){
        return em.createQuery("select i from Item i",Item.class) //상품 여러 개 찾기
                .getResultList();
    }
}
