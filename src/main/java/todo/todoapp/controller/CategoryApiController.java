package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.category.CategoryCreateRequest;
import todo.todoapp.dto.category.CategoryResponse;
import todo.todoapp.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryCreateRequest request,
                                    @AuthenticationPrincipal Long memberId) {
        Long id = categoryService.create(memberId, request);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<?> getCategories(@AuthenticationPrincipal Long memberId) {
        List<CategoryResponse> categoryResponses = categoryService.get(memberId);
        return ResponseEntity.ok(categoryResponses);
    }

    @PutMapping
    public ResponseEntity<?> updateCategories(@AuthenticationPrincipal Long memberId) {
        categoryService.update();

        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategories(@AuthenticationPrincipal Long memberId) {
        categoryService.delete();
        return ResponseEntity.ok(null);
    }
}
