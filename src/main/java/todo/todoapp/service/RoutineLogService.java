package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.dto.routine.RoutineUpdateRequest;
import todo.todoapp.dto.routineLog.RoutineLogResponse;
import todo.todoapp.dto.routineLog.RoutineLogUpdateRequest;
import todo.todoapp.entity.Routine;
import todo.todoapp.entity.RoutineLog;
import todo.todoapp.entity.RoutineStatus;
import todo.todoapp.repository.RoutineLogRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineLogService {

    private final RoutineLogRepository routineLogRepository;


    //루틴 로그 생성
    public void create(Routine routine,Long memberId) {
        List<LocalDate> targetDates = generateTargetDates(routine.getStartDate(),
                routine.getEndDate(), routine.getRepeatDays(), routine);

        for (LocalDate targetDate : targetDates) {
            RoutineLog log = RoutineLog.builder()
                    .routine(routine)
                    .dateTime(targetDate)
                    .memberId(memberId)
                    .build();
            routine.getRoutineLogs().add(log);
            routineLogRepository.save(log);
        }
    }

    //루틴 로그 수정
    public void update(Routine routine, RoutineUpdateRequest request,Long memberId) {
        List<LocalDate> targetDates = generateTargetDates(request.getStartDate(),
                request.getEndDate(), request.getRepeatDays(), routine);

        // 기존 로그 매핑
        List<RoutineLog> existingLogs = routine.getRoutineLogs();
        List<LocalDate> existingDates = existingLogs.stream()
                .map(RoutineLog::getDateTime)
                .toList();

        // 추가할 날짜 탐색 (기존에 없는 날짜)
        for (LocalDate date : targetDates) {
            if (!existingDates.contains(date)) {
                RoutineLog log = RoutineLog.builder()
                        .routine(routine)
                        .dateTime(date)
                        .memberId(memberId)
                        .build();
                routine.getRoutineLogs().add(log);
                routineLogRepository.save(log);
            }
        }

        // 제거할 날짜 탐색 (새 기준에 없는 날짜면서 아직 완료 상태가 아닌 로그만 삭제)
        List<RoutineLog> toRemove = new ArrayList<>();
        for (RoutineLog log : existingLogs) {
            LocalDate logDate = log.getDateTime();
            if (!targetDates.contains(logDate) && log.getRoutineStatus() == RoutineStatus.NONE) {
                toRemove.add(log);
            }
        }

        routine.getRoutineLogs().removeAll(toRemove);
        routineLogRepository.deleteAll(toRemove);
    }


    private List<LocalDate> generateTargetDates(LocalDate startDate, LocalDate endDate, List<DayOfWeek> repeatDays2, Routine routine) {
        LocalDate start = startDate;
        LocalDate end = endDate;
        List<DayOfWeek> repeatDays = repeatDays2;

        List<LocalDate> targetDates = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (repeatDays.contains(date.getDayOfWeek())){
                targetDates.add(date);
            }
        }
        return targetDates;
    }


    //루틴 로그 상태 변경
    public void changeStatus(Long routineLogId, RoutineLogUpdateRequest request, Long memberId) {
        RoutineLog log = routineLogRepository.findById(routineLogId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 루틴 로그입니다."));

        if (!log.getRoutine().getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 루틴 로그에 대한 권한이 없습니다.");
        }
        log.changeStatus(request.getStatus());
    }


    //루틴 로그 조회
    @Transactional(readOnly = true)
    public List<RoutineLogResponse> findByDate(LocalDate date,Long memberId) {
        return routineLogRepository.findByDateTimeAndMemberId(date,memberId).stream()
                .map(RoutineLogResponse::from)
                .toList();
    }
}