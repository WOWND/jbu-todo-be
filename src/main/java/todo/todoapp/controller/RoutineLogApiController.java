package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.routineLog.RoutineLogResponse;
import todo.todoapp.dto.routineLog.RoutineLogUpdateRequest;
import todo.todoapp.service.RoutineLogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine-logs")
public class RoutineLogApiController {
    private final RoutineLogService routineLogService;

    //루틴 로그 상태 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long routineLogId,
                                          @RequestBody RoutineLogUpdateRequest request, @AuthenticationPrincipal Long memberId) {
        routineLogService.changeStatus(routineLogId, request, memberId);
        return ResponseEntity.noContent().build();
    }

    //날짜로 조회
    @GetMapping
    public ResponseEntity<?> getLogsByDate(@RequestBody Map<String, LocalDate> request,
                                           @AuthenticationPrincipal Long memberId) {
        List<RoutineLogResponse> logs = routineLogService.findByDate(request.get("date"),memberId);
        return ResponseEntity.ok(logs);
    }
}

