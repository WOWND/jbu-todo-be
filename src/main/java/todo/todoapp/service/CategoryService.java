package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.dto.category.CategoryCreateRequest;
import todo.todoapp.dto.category.CategoryResponse;
import todo.todoapp.entity.Category;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.CategoryRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberService memberService;

    public Long create(Long memberId, CategoryCreateRequest request) {
        Member member = memberService.findById(memberId);
        return categoryRepository.save(Category.builder()
                .title(request.getTitle())
                .member(member)
                .build()).getId();
    }

    public List<CategoryResponse> get(Long memberId) {
        return memberService.findById(memberId).getCategories().stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    public ResponseEntity<?> update() {
        return null;
    }

    public void delete() {

    }
}
