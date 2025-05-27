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
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;



    //프로필 이미지 업로드
    @PostMapping("/me/upload-profile")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                                @AuthenticationPrincipal Long memberId) {
        String imageUrl = memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok().body(Map.of("imageUrl", imageUrl));
    }

    //회원 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberInfo> getInfo(@AuthenticationPrincipal Long memberId) {
        MemberInfo member = memberService.getById(memberId);
        log.info("------------------{}", member.getEmail());
        log.info("------------------{}", member.getNickName());
        log.info("------------------{}", member.getProfileUrl());
        log.info("------------------{}", member.getIntroText());

        return ResponseEntity.ok().body(member);
    }


    //회원 정보 수정
    @PutMapping("/me")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest updateRequest,
                                          @AuthenticationPrincipal Long memberId) {
        memberService.update(updateRequest, memberId);
        return ResponseEntity.ok(memberId);
    }


    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.noContent().build();
    }
}