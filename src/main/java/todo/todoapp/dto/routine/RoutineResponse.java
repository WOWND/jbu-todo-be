package todo.todoapp.dto.routine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Category;
import todo.todoapp.entity.Routine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoutineResponse {
    private Long id;
    private Long categoryId;
    private String title;
    //private String description;
    private List<DayOfWeek> repeatDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String min;
    private String base;
    private String max;


    public static RoutineResponse from(Routine routine) {
        return new RoutineResponse(
                routine.getId(),
                routine.getCategory().getId(),
                routine.getTitle(),
                routine.getRepeatDays(),
                routine.getStartDate(),
                routine.getEndDate(),
                routine.getMin(),
                routine.getBase(),
                routine.getMax()
        );
    }
}