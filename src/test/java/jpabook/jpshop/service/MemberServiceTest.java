package jpabook.jpshop.service;

import jpabook.jpshop.domain.Member;
import jpabook.jpshop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
/**
 * --테스트 요구사항--
 * 회원가입을 성공해야 한다.
 * 회원가입 시 같은 이름이 있으면 예외가 발생해야 한다.
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
  //  @Autowired EntityManager em;

    @Test
    //@Rollback(value = false)
    public void join() throws Exception{
        //given
        Member member = new Member();
        member.setName("kwon3");

        //when
        long saveId = memberService.join(member);

        //then
      //  em.flush(); //변경이나 등록내용을 쿼리로 반영됨
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void sameName() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야 한다.");
    }

//
//    @Test
//    public void sameName() throws Exception{
//        //given
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member2.setName("kwon");
//
//        //when
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }
//        //then
//        fail("예외가 발생해야한다.fail 타면 안됩니다.");
//    }

}