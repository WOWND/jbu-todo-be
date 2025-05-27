package todo.todoapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.todoapp.dto.routine.RoutineRequest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine {
    @Id
    @GeneratedValue
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;


    private String title;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "routine_repeat_days",
            joinColumns = @JoinColumn(name = "routine_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private List<DayOfWeek> repeatDays;


    private LocalDate startDate;
    private LocalDate endDate;

    private String min;
    private String base;
    private String max;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineLog> routineLogs = new ArrayList<>();

    @Builder
    public Routine(Member member, Category category,String title,String description, List<DayOfWeek> repeatDays, LocalDate startDate,
                   LocalDate endDate, String min, String base, String max) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.description = description;
        this.repeatDays = repeatDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.min = min;
        this.base = base;
        this.max = max;
    }

    public void updateContent(Category category,String title,String description,String min,String base,String max) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.min = min;
        this.base = base;
        this.max = max;
    }

    public void updateSchedule(List<DayOfWeek> repeatDays, LocalDate startDate, LocalDate endDate) {
        this.repeatDays.clear();
        this.repeatDays.addAll(repeatDays);
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
