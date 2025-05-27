package todo.todoapp.dto.member;

import lombok.Getter;
import lombok.Setter;
import todo.todoapp.exception.SignupRequiredException;

@Getter
@Setter
public class SignupInitResponse {
    public String status;
    public String temporaryToken;
    public String nickname;
    public String profileUrl;

    public SignupInitResponse(SignupRequiredException e) {
        this.status = e.getStatus();
        this.temporaryToken = e.getTemporaryToken();
        this.nickname = e.getNickname();
        this.profileUrl = e.getProfileUrl();
    }
}
