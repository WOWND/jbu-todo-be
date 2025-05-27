package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import todo.todoapp.dto.member.*;
import todo.todoapp.service.MemberService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = memberService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        LoginResponse loginResponse = memberService.signup(request);
        return ResponseEntity.ok(loginResponse);
    }


    //ID 중복 검사 (exists?username=abc)
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@RequestParam String username) {
        boolean exists = memberService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

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