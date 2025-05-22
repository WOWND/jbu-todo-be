package todo.todoapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.entity.Routine;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
public class RoutineLogServiceTest {

    @Autowired
    RoutineService routineService;

    @Test
    public void 로그생성() throws Exception {
        //given

        //when

        //then
    }
}