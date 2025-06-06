package todo.todoapp.dto.member;

import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Member;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String username;
    private String password;
    private String nickname;
    private String introText;

    //자체 로그인
    public Member toEntity(String password,String defaultProfile) {
        return Member.builder()
                .email(this.email)
                .profileUrl(defaultProfile)
                .username(this.username)
                .password(password)
                .nickName(this.nickname)
                .introText(this.introText)
                .build();
    }
}
