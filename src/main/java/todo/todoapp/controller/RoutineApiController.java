package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.routine.RoutineRequest;
import todo.todoapp.dto.routine.RoutineResponse;
import todo.todoapp.dto.routine.RoutineUpdateRequest;
import todo.todoapp.service.RoutineService;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
@Slf4j
public class RoutineApiController {
    private final RoutineService routineService;
    //루틴 생성
    @Operation(summary = "루틴 생성", description = "루틴을 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> createRoutine(@RequestBody RoutineRequest request, @AuthenticationPrincipal Long memberId) {
        log.info("======================{}", request.getCategoryId());
        log.info("======================{}", request.getBase());
        log.info("======================{}", request.getCategoryId());
        log.info("======================{}", request.getCategoryId());
        Long routineId = routineService.createRoutine(request, memberId);
        return ResponseEntity.ok(routineId);
    }


    //모든 루틴 조회
    @Operation(summary = "모든 루틴 조회", description = "회원이 가진 모든 루틴을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<RoutineResponse>> getRoutines(@AuthenticationPrincipal Long memberId) {
        List<RoutineResponse> routines = routineService.getRoutines(memberId);
        return ResponseEntity.ok(routines);
    }


    //개별 루틴 조회
    @Operation(summary = "개별 루틴 조회", description = "루틴 ID에 해당하는 루틴을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<RoutineResponse> getRoutine(@PathVariable("id") Long routineId, @AuthenticationPrincipal Long memberId) {
        RoutineResponse routine = routineService.getById(memberId, routineId);
        return ResponseEntity.ok(routine);
    }

    //루틴 수정
    @Operation(summary = "루틴 수정", description = "루틴 ID에 해당하는 루틴 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<RoutineResponse> updateRoutine(@PathVariable("id") Long routineId, @RequestBody RoutineUpdateRequest request,
                                           @AuthenticationPrincipal Long memberId) {
        RoutineResponse routine = routineService.update(request, routineId, memberId);
        return ResponseEntity.ok(routine);
    }

    //루틴 삭제
    @Operation(summary = "루틴 삭제", description = "루틴 ID에 해당하는 루틴을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoutine(@PathVariable("id") Long routineId, @AuthenticationPrincipal Long memberId) {
        routineService.delete(routineId,memberId);
        return ResponseEntity.noContent().build();
    }
}
