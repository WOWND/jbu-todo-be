package todo.todoapp.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import todo.todoapp.entity.Member;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfo {
    private String email;
    private String nickName;
    private String profileUrl;
    private String introText;


    public static MemberInfo from(Member member) {
        return new MemberInfo(
                member.getEmail(),
                member.getNickName(),
                member.getProfileUrl(),
                member.getIntroText()
        );
    }
}
