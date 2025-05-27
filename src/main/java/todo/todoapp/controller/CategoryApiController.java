package todo.todoapp.controller;

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
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryRequest request,
                                    @AuthenticationPrincipal Long memberId) {
        Long id = categoryService.create(memberId, request);
        return ResponseEntity.ok(id);
    }

    //카테고리 목록 조회
    @GetMapping
    public ResponseEntity<?> getCategories(@AuthenticationPrincipal Long memberId) {
        List<CategoryResponse> categoryResponses = categoryService.getById(memberId);
        return ResponseEntity.ok(categoryResponses);
    }


    //카테고리 수정
    @PutMapping
    public ResponseEntity<?> updateCategories(@RequestBody CategoryRequest request,
                                              @AuthenticationPrincipal Long memberId) {

        CategoryResponse updated = categoryService.update(request, memberId);
        return ResponseEntity.ok(updated);
    }


    //카테고리 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteCategories(@RequestBody CategoryRequest request,
                                              @AuthenticationPrincipal Long memberId) {
        categoryService.delete(request,memberId);
        return ResponseEntity.noContent().build();
    }
}
