package todo.todoapp.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Member;

@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    private String email;
    private String nickname;
    private String profileUrl;
    private String introText;

    public static LoginResponse from(Member member,String accessToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .email(member.getEmail())
                .nickname(member.getEmail())
                .profileUrl(member.getProfileUrl())
                .introText(member.getIntroText())
                .build();
    }
}

