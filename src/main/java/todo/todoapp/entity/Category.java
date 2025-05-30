package todo.todoapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private boolean isDefaultCategory;

    @Builder
    public Category(Member member,String title,boolean isDefaultCategory) {
        this.member = member;
        this.title = title;
        this.isDefaultCategory = isDefaultCategory;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
