package todo.todoapp.dto.member;

import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Member;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String nickname;
    private String profileUrl;
    private String introText;

    public Member toEntity(Long kakaoId) {
        return Member.builder()
                .kakaoId(kakaoId)
                .nickName(this.nickname)
                .email(this.email)
                .profileUrl(this.profileUrl)
                .introText(this.introText)
                .build();
    }
}
