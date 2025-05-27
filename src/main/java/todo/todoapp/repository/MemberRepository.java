package todo.todoapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(Long kakaoId);
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
}
