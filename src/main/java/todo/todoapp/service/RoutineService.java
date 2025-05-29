package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.dto.routine.RoutineRequest;
import todo.todoapp.dto.routine.RoutineResponse;
import todo.todoapp.dto.routine.RoutineUpdateRequest;
import todo.todoapp.entity.Category;
import todo.todoapp.entity.Member;
import todo.todoapp.entity.Routine;
import todo.todoapp.repository.RoutineRepository;
import static todo.todoapp.dto.routine.RoutineUpdateRequest.UpdateType.CONTENT;
import static todo.todoapp.dto.routine.RoutineUpdateRequest.UpdateType.SCHEDULE;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final RoutineLogService routineLogService;
    private final MemberService memberService;
    private final CategoryService categoryService;


    //루틴 생성
    public Long createRoutine(RoutineRequest routineRequest, Long memberId) {
        Member member = memberService.findById(memberId);
        Category category = categoryService.findById(routineRequest.getCategoryId());

        Routine routine = routineRequest.toEntity(member,category);

        routineRepository.save(routine);
        member.getRoutines().add(routine);

        routineLogService.create(routine,memberId); //날짜에 해당하는 루틴로그들 생성
        return routine.getId();
    }

    //모든 루틴 조회하기
    @Transactional(readOnly = true)
    public List<RoutineResponse> getRoutines(Long memberId) {
        return memberService.findById(memberId).getRoutines()
                .stream()
                .map(RoutineResponse::from)
                .toList();
    }


    //개별 루틴 조회하기
    @Transactional(readOnly = true)
    public RoutineResponse getById(Long memberId, Long routineId) {
        Routine routine = findRoutineById(routineId);
        validateOwnership(routine,memberId);

        return RoutineResponse.from(routine);
    }


    //루틴 수정하기
    public RoutineResponse update(RoutineUpdateRequest routineRequest, Long routineId, Long memberId) {
        Routine routine = findRoutineById(routineId);
        validateOwnership(routine,memberId);

        //컨텐츠 수정
        if (routineRequest.getUpdateType() == CONTENT) {
<<<<<<< HEAD
            Category category = categoryService.findById(routineRequest.getCategoryId());
            routine.updateContent(category,routineRequest.getTitle(), routineRequest.getDescription(),
=======
            Category category = categoryService.findById(routineRequest.getCategoryId(),memberId);
            routine.updateContent(category,routineRequest.getTitle(),
>>>>>>> 648baeb (:memo: Swagger 추가)
                    routineRequest.getMin(), routineRequest.getBase(), routineRequest.getMax());
        } else if (routineRequest.getUpdateType() == SCHEDULE) { //스케줄 수정
            routineLogService.update(routine,routineRequest,memberId);
            routine.updateSchedule(routineRequest.getRepeatDays(),routineRequest.getStartDate(),routineRequest.getEndDate());
        } else {
            throw new IllegalArgumentException("잘못된 수정 요청입니다.");
        }

        return RoutineResponse.from(routine);
    }

    //루틴 삭제하기
    public void delete(Long routineId, Long memberId) {
        Routine routine = findRoutineById(routineId);
        validateOwnership(routine,memberId);

        //양방향 동기화?
        routineRepository.delete(routine);
    }



    //접근 권한 검증
    private void validateOwnership(Routine routine, Long memberId) {
        if (!Objects.equals(routine.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("해당 루틴에 대한 접근 권한이 없습니다. id=" + routine.getId());
        }
    }

    //루틴 반환
    private Routine findRoutineById(Long routineId) {
        return routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("해당 루틴이 존재하지 않습니다. routineId=" + routineId));
    }
}
