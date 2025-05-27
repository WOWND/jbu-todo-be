package todo.todoapp.dto.member;

import lombok.Getter;
import lombok.Setter;
import todo.todoapp.exception.SignupRequiredException;

@Getter
@Setter
public class SignupInitResponse {
    public String status;
    public String accessToken;
    public String nickname;
    public String profileUrl;

    public SignupInitResponse(SignupRequiredException e) {
        this.status = e.getStatus();
        this.accessToken = e.getTemporaryToken();
        this.nickname = e.getNickname();
        this.profileUrl = e.getProfileUrl();
    }
}
