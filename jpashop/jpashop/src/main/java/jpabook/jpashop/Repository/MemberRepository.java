package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository//스프링 빈에 자동으로 주입
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext
    //private EntityManager em;

    private final EntityManager em;

    //JPA가 멤버를 저장하는 메서드
    public void save(Member member){
        em.persist(member);//persist에 넣으면 바로 db에 반영되는게 아니라 em이 transaction commit()을 호출해야 db에 반영됨 -->flush
    }

    //멤버를 조회하는 메서드
    public Member findOne(Long id){
        return em.find(Member.class, id);//find(Type,PK)
    }

    //멤버 전체를 조회하는 메서드
    public List<Member> finaAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //특정 회원이름으로 결과 조회하는 메서드
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name= :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
