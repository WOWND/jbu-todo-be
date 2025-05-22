package todo.todoapp.dto.routineLog;

import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.RoutineStatus;

import java.time.LocalDate;

@Getter
@Setter
public class RoutineLogUpdateRequest {
    public RoutineStatus status;
}
