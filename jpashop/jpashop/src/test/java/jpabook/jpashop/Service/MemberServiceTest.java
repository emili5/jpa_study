package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)//Junit실행할 때 스프링이랑 같이 실행
@SpringBootTest//스프링 부트를 띄운 상태로 테스트 실행, 스프링 컨테이너와 테스트를 함께 실행
@Transactional//Test에 있으면 테스트 시작 전 트랜잭션 시작, 완료 후는 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(value = false)//기본이 rollback,쿼리 반영이 안됨->반영해서 눈으로 확인하려면 false로 설정!
    public void 회원가입() throws Exception{
        //given
        Member member= new Member();
        member.setName("Lucy");

        //when
        Long saveId = memberService.join(member);

        //then
        //em.flush() 하면 transaction도 하고 쿼리도 반영
        assertEquals(member,memberRepository.findOne(saveId));
    }
    @Test(expected = IllegalStateException.class)//expected=예외이름 을 써주면 try-Exception을 쓰지 않아도 잡아줌
    public void 중복_회원_예약(){
        //given
        Member member1=new Member();
        member1.setName("Alice");

        Member member2=new Member();
        member2.setName("Alice");

        //when
        memberService.join(member1);
        memberService.join(member2);//예외가 발생

        //then
        fail("예외가 발생해야 함");//여기까지 오면 예외발생지점에서 제대로 실행이 안되었다는 뜻
    }

}