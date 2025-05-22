package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.routine.RoutineRequest;
import todo.todoapp.dto.routine.RoutineResponse;
import todo.todoapp.dto.routine.RoutineUpdateRequest;
import todo.todoapp.service.RoutineService;

import java.util.List;

@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineApiController {
    private final RoutineService routineService;
    //루틴 생성
    @PostMapping
    public ResponseEntity<?> createRoutine(@RequestBody RoutineRequest request, @AuthenticationPrincipal Long memberId) {
        Long routineId = routineService.createRoutine(request, memberId);
        return ResponseEntity.ok(routineId);
    }


    //모든 루틴 조회
    @GetMapping
    public ResponseEntity<?> getRoutines(@AuthenticationPrincipal Long memberId) {
        List<RoutineResponse> routines = routineService.getRoutines(memberId);
        return ResponseEntity.ok(routines);
    }


    //개별 루틴 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoutine(@PathVariable("id") Long routineId, @AuthenticationPrincipal Long memberId) {
        RoutineResponse routine = routineService.getRoutine(memberId, routineId);
        return ResponseEntity.ok(routine);
    }

    //루틴 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoutine(@PathVariable("id") Long routineId, @RequestBody RoutineUpdateRequest request,
                                           @AuthenticationPrincipal Long memberId) {
        RoutineResponse routine = routineService.updateRoutine(request, routineId, memberId);
        return ResponseEntity.ok(routine);
    }

    //루틴 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoutine(@PathVariable("id") Long routineId, @AuthenticationPrincipal Long memberId) {
        routineService.deleteRoutine(routineId,memberId);
        return ResponseEntity.noContent().build();
    }
}
