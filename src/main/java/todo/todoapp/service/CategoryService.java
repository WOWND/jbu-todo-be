package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todo.todoapp.dto.category.CategoryRequest;
import todo.todoapp.dto.category.CategoryResponse;
import todo.todoapp.entity.Category;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.CategoryRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberService memberService;

    //카테고리 생성
    public Long create(Long memberId, CategoryRequest request) {
        Member member = memberService.findById(memberId);
        return categoryRepository.save(Category.builder()
                .title(request.getTitle())
                .member(member)
                .build()).getId();
    }


    //카테고리 조회
    public List<CategoryResponse> getById(Long memberId) {
        return memberService.findById(memberId).getCategories().stream()
                .map(CategoryResponse::from)
                .toList();
    }



    public void delete(CategoryRequest request, Long memberId) {
        Category category = findById(request.getId());
        validateOwnership(category, memberId);

        categoryRepository.delete(category);
    }

    public CategoryResponse update(CategoryRequest request, Long memberId) {
        Category category = findById(request.getId());
        validateOwnership(category, memberId);

        category.updateTitle(request.getTitle());
        return CategoryResponse.from(category);
    }

    //아이디로 찾기
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    //접근 권한 검증
    private void validateOwnership(Category category, Long memberId) {
        if (!Objects.equals(category.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("해당 카테고리에 대한 접근 권한이 없습니다. id=" + category.getId());
        }
    }
}
