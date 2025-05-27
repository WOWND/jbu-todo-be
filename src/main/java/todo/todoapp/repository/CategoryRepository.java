package todo.todoapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
