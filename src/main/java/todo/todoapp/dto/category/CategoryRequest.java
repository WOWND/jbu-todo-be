package todo.todoapp.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private Long id;
    private String title;
}
