package todo.todoapp.dto.routineLog;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RoutineLogDateRequest {
    public LocalDate date;
}
