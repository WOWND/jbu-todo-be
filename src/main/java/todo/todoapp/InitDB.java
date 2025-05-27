package todo.todoapp;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.entity.Member;
import todo.todoapp.service.RoutineService;

@Component
@RequiredArgsConstructor
public class InitDB {
    @Value("${app.backend-url}")
    private String serverUrl;

    private final InitService service;

    @PostConstruct
    public void init() {
        service.dbInit1(serverUrl);
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1(String serverUrl) {
//            Member member = Member.builder()
//                    .nickName("박재중")
//                    .email("testA@gmail.com")
//                    .kakaoId(4257046343L)
//                    .profileUrl(serverUrl + "/images/profile/default_image.jpg")
//                    .introText("테스트입니다")
//                    .build();
//            em.persist(member);
            Member member2 = Member.builder()
                    .nickName("전주은")
                    .email("testB@gmail.com")
                    .kakaoId(4260754239L)
                    .profileUrl(serverUrl + "/images/profile/default_image.jpg")
                    .introText("테스트입니다")
                    .build();
            em.persist(member2);
//
//            Category category1 = Category.builder()
//                    .title("학교")
//                    .member(member)
//                    .build();
//            em.persist(category1);
//
//            Category category2 = Category.builder()
//                    .title("개인")
//                    .member(member)
//                    .build();
//            em.persist(category2);
        }
    }
}