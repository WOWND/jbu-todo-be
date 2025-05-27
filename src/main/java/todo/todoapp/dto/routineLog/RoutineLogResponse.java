package todo.todoapp.dto.routineLog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.RoutineLog;
import todo.todoapp.entity.RoutineStatus;

@Getter
@Setter
@AllArgsConstructor
public class RoutineLogResponse {
    public Long id;
    public RoutineStatus status;
    public String title;

    public static RoutineLogResponse from(RoutineLog routineLog) {
        return new RoutineLogResponse(
                routineLog.getId(),
                routineLog.getRoutineStatus(),
                routineLog.getRoutine().getTitle()
        );
    }
}
