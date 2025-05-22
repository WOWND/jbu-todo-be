package todo.todoapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.repository.MemberRepository;


@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void 회원삭제() throws Exception {
        //given
        memberService.deleteMember(1L);
        //when

        //then
    }

}