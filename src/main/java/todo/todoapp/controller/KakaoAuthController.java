package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.kakao.KakaoCodeRequest;
import todo.todoapp.dto.member.LoginResponse;
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
    @Operation(summary = "카카오 로그인", description = "인가 코드를 받아 카카오 로그인 처리. 회원가입이 필요하면 401 응답과 초기 정보 제공.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "기존 회원 로그인 성공", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "회원가입 필요 - 초기 데이터 포함", content = @Content(schema = @Schema(implementation = SignupInitResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody KakaoCodeRequest request) {
        String code = request.getCode();
        try {
            return ResponseEntity.ok(kakaoService.kakaoLogin(code));
        } catch (SignupRequiredException exception) {
            return ResponseEntity.status(401).body(new SignupInitResponse(exception));
        }
    }

    //카카오 회원가입
    @Operation(summary = "카카오 회원가입", description = "카카오 로그인 후 받은 초기 정보와 함께 추가 정보를 입력해 회원가입을 완료.")
    @PostMapping("/signup")
<<<<<<< HEAD
    public ResponseEntity<?> signup(@RequestBody SignupRequest request, @AuthenticationPrincipal Long kakaoId) {
        log.info("--------------------회원가입 요청-------------------------");
=======
    public ResponseEntity<LoginResponse> signup(@RequestBody KakaoSignupRequest request, @AuthenticationPrincipal Long kakaoId) {
>>>>>>> 648baeb (:memo: Swagger 추가)
        return ResponseEntity.ok(kakaoService.signup(request,kakaoId));
    }

}