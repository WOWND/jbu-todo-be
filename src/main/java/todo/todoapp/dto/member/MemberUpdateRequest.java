package todo.todoapp.dto.member;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequest {
    public String nickName;
    public String introText;
}
