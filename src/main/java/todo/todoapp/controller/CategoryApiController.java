package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.category.CategoryRequest;
import todo.todoapp.dto.category.CategoryResponse;
import todo.todoapp.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;

    //카테고리 생성
    @Operation(summary = "카테고리 생성", description = "로그인한 사용자의 ID와 요청 정보를 기반으로 새 카테고리를 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CategoryRequest request,
                                    @AuthenticationPrincipal Long memberId) {
        Long id = categoryService.create(memberId, request);
        return ResponseEntity.ok(id);
    }

    //카테고리 목록 조회
    @Operation(summary = "카테고리 목록 조회", description = "로그인한 사용자의 모든 카테고리 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(@AuthenticationPrincipal Long memberId) {
        List<CategoryResponse> categoryResponses = categoryService.getById(memberId);
        return ResponseEntity.ok(categoryResponses);
    }


    //카테고리 수정
    @Operation(summary = "카테고리 수정", description = "로그인한 사용자가 요청한 카테고리 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<CategoryResponse> updateCategories(@RequestBody CategoryRequest request,
                                              @AuthenticationPrincipal Long memberId) {

        CategoryResponse updated = categoryService.update(request, memberId);
        return ResponseEntity.ok(updated);
    }


    //카테고리 삭제
    @Operation(summary = "카테고리 삭제", description = "로그인한 사용자가 요청한 카테고리를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<?> deleteCategories(@RequestBody CategoryRequest request,
                                              @AuthenticationPrincipal Long memberId) {
        categoryService.delete(request,memberId);
        return ResponseEntity.noContent().build();
    }
}
