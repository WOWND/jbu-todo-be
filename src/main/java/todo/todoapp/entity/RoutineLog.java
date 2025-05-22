package todo.todoapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineLog {
    @Id
    @GeneratedValue
    @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;


    @Column(name = "member_id")
    private Long memberId;


    private LocalDate dateTime;

    @Enumerated(EnumType.STRING) //db에 이름으로 기록
    private RoutineStatus routineStatus = RoutineStatus.NONE;

    @Builder
    public RoutineLog(Routine routine, LocalDate dateTime,Long memberId) {
        this.routine = routine;
        this.dateTime = dateTime;
        this.memberId = memberId;
    }

    public void changeStatus(RoutineStatus routineStatus) {
        this.routineStatus = routineStatus;
    }
}
