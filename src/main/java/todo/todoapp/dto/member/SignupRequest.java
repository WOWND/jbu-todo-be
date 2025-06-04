package todo.todoapp.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Member;

@Getter
@Setter
public class SignupRequest {
    private String email;

    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")

    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
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
