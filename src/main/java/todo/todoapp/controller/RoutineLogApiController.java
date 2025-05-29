package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.routineLog.RoutineLogDateRequest;
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
    @Operation(summary = "루틴 로그 상태 변경", description = "해당 루틴 로그의 상태를 변경합니다.( NONE, MIN, BASE, MAX )")
    @PatchMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long routineLogId,
                                          @RequestBody RoutineLogUpdateRequest request, @AuthenticationPrincipal Long memberId) {
        routineLogService.changeStatus(routineLogId, request, memberId);
        return ResponseEntity.noContent().build();
    }

    //날짜로 조회
    @Operation(summary = "루틴 로그 조회", description = "요청한 날짜에 해당하는 루틴 로그를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<RoutineLogResponse>> getLogsByDate(@RequestBody RoutineLogDateRequest request,
                                           @AuthenticationPrincipal Long memberId) {
        List<RoutineLogResponse> logs = routineLogService.findByDate(request.getDate(),memberId);
        return ResponseEntity.ok(logs);
    }
}
