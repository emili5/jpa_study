package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)//데이터 변경은 반드시 트랜잭션이 있어야함!,readOnly는 읽기전용에서 써주면 됨
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    //@Autowired//스프링이 스프링빈에 등록되어있는 레포지토리를 인젝션해줌
   // public MemberService(MemberRepository memberRepository){
       // this.memberRepository=memberRepository;
    //}
    /**
     *회원가입
     */

    //회원가입
    @Transactional//디폴트가 readOnly=false
    public Long join(Member member){
        validateDuplicateMember(member);//중복회원 찾기
        memberRepository.save(member);
        return member.getId();

    }
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원전체조회
    public List<Member> findMembers(){
        return memberRepository.finaAll();
    }
     //회원 한명만 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
