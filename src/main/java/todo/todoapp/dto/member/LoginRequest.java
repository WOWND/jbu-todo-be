package todo.todoapp.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    public String username;
    public String password;
}
