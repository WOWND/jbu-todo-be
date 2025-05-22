package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.SignupRequiredException;
import todo.todoapp.dto.member.SignupRequest;
import todo.todoapp.service.KakaoService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoAuthController {
    private final KakaoService kakaoService;

    @PostMapping("/kakao/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String code = request.get("code");
        log.info("--------------------로그인 요청-------------------------");
        try {
            return ResponseEntity.ok(kakaoService.kakaoLogin(code));
        } catch (SignupRequiredException exception) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "SIGNUP_REQUIRED",
                    "temporaryToken", exception.getTemporaryToken(),
                    "nickname", exception.getNickname(),
                    "profileUrl", exception.getProfileUrl()
            ));
        }
    }

    @PostMapping("/kakao/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request, @AuthenticationPrincipal Long kakaoId) {
        log.info("--------------------회원가입 요청-------------------------");
        return ResponseEntity.ok(kakaoService.signup(request,kakaoId));
    }

}