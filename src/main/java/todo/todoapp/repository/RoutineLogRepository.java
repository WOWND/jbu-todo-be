package todo.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.Member;
import todo.todoapp.entity.RoutineLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoutineLogRepository extends JpaRepository<RoutineLog, Long> {
    List<RoutineLog> findByDateTimeAndMemberId(LocalDate dateTime, Long memberId);
}
