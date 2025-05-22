package todo.todoapp.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import todo.todoapp.entity.Category;

@Getter
@Setter
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String title;

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle()
        );
    }
}
