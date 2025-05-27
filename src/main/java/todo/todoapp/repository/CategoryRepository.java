package todo.todoapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByMemberIdAndIsDefaultCategoryTrue(Long memberId);
}
