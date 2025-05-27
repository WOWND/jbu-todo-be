package todo.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.Routine;

public interface RoutineRepository extends JpaRepository<Routine,Long> {
}
