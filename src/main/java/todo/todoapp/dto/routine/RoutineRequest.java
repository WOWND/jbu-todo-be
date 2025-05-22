package todo.todoapp.dto.routine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Category;
import todo.todoapp.entity.Member;
import todo.todoapp.entity.Routine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoutineRequest {
    private Long categoryId;
    private String title;
    private String description;
    private List<DayOfWeek> repeatDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String min;
    private String base;
    private String max;

    public Routine toEntity(Member member,Category category) {
        return Routine.builder()
                .member(member)
                .category(category)
                .title(title)
                .description(this.description)
                .repeatDays(this.repeatDays)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .min(this.min)
                .base(this.base)
                .max(this.max)
                .build();
    }
}
