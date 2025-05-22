package todo.todoapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.dto.routine.RoutineRequest;
import todo.todoapp.entity.Member;
import todo.todoapp.entity.Routine;
import todo.todoapp.repository.MemberRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
public class RoutineServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoutineService routineService;

    @Test
    @Rollback(value = false)
    public void 루틴생성() throws Exception {
        Member member = Member.builder()
                .kakaoId(12345L)
                .nickName("hello")
                .introText("안녕하세요22")
                .profileUrl("sdfasdfafs.url")
                .email("tset@gmail.com")
                .build();
        Member save = memberRepository.save(member);// 이 줄 필요함
//given
        //when

        //then

    }
}