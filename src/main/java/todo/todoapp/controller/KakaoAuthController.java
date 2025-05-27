package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.member.SignupInitResponse;
import todo.todoapp.exception.SignupRequiredException;
import todo.todoapp.dto.member.SignupRequest;
import todo.todoapp.service.KakaoService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/kakao")
public class KakaoAuthController {
    private final KakaoService kakaoService;

    //카카오 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        try {
            return ResponseEntity.ok(kakaoService.kakaoLogin(code));
        } catch (SignupRequiredException exception) {
            return ResponseEntity.status(401).body(new SignupInitResponse(exception));
        }
    }

    //카카오 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request, @AuthenticationPrincipal Long kakaoId) {
        log.info("--------------------회원가입 요청-------------------------");
        return ResponseEntity.ok(kakaoService.signup(request,kakaoId));
    }

}