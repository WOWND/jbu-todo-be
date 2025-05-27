package todo.todoapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    @Column(unique = true)
    private Long kakaoId;
    private String nickName;
    private String profileUrl;
    private String introText;

    @OneToMany(mappedBy = "member")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routine> routines = new ArrayList<>();

    @Builder
    public Member(String email, String nickName, String profileUrl, String introText, Long kakaoId) {
        this.email = email;
        this.nickName = nickName;
        this.profileUrl = profileUrl;
        this.introText = introText;
        this.kakaoId = kakaoId;
    }

    public void updateProfile(String nickname, String introText) {
        this.nickName = nickname;
        this.introText = introText;
    }

    public void uploadProfileImage(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}

