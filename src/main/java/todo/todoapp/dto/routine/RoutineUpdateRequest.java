package todo.todoapp.dto.routine;

import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Category;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RoutineUpdateRequest {
    private Long categoryId;
    private String title;
    private String description;
    private List<DayOfWeek> repeatDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String min;
    private String base;
    private String max;

    private UpdateType updateType;

    public enum UpdateType{
        CONTENT,SCHEDULE
    }
}
