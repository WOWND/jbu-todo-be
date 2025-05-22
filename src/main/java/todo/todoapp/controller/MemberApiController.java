package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import todo.todoapp.dto.member.MemberInfo;
import todo.todoapp.dto.member.MemberUpdateRequest;
import todo.todoapp.service.MemberService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/api/users")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/kakao/upload-profile")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                                @AuthenticationPrincipal Long memberId) {
        String imageUrl = memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok().body(Map.of("imageUrl", imageUrl));
    }

    @GetMapping("/kakao/user-info")
    public ResponseEntity<MemberInfo> getMemberInfo(@AuthenticationPrincipal Long memberId) {
        MemberInfo member = memberService.getMember(memberId);
        log.info("------------------{}", member.getEmail());
        log.info("------------------{}", member.getNickName());
        log.info("------------------{}", member.getProfileUrl());
        log.info("------------------{}", member.getIntroText());

        return ResponseEntity.ok().body(member);
    }


    @GetMapping("/api/test")
    public String testApi(@AuthenticationPrincipal Long memberId) {
        return memberId+"안녕하세요";
    }

    @PutMapping("/members")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest updateRequest,
                                          @AuthenticationPrincipal Long memberId) {
        memberService.updateMember(updateRequest, memberId);
        return ResponseEntity.ok(memberId);
    }
}