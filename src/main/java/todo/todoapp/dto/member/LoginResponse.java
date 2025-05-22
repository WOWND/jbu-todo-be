package todo.todoapp.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    //private String refreshToken;
    //private Long memberId;
    //private String email;
    //private String nickname;
    //private String profileUrl;
    //private String introText;
}

