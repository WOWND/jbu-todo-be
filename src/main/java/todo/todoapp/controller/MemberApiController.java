package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import todo.todoapp.dto.member.MemberInfo;
import todo.todoapp.dto.member.MemberUpdateRequest;
import todo.todoapp.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

<<<<<<< HEAD

=======
    @Operation(summary = "자체 로그인", description = "username과 password를 이용한 자체 로그인 기능입니다.", security = @SecurityRequirement(name = ""))
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("=================={}", request.getUsername());
        log.info("=================={}", request.getPassword());
        LoginResponse loginResponse = memberService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "자체 회원가입", description = "email, username, password, nickname, introtext를 이용해 회원가입을 진행합니다.", security = @SecurityRequirement(name = ""))
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        LoginResponse loginResponse = memberService.signup(request);
        return ResponseEntity.ok(loginResponse);
    }


    @Operation(summary = "아이디 중복 확인", description = "회원가입 시 아이디가 이미 존재하는지 확인합니다.",
            security = @SecurityRequirement(name = ""))
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@RequestParam String username) {
        boolean exists = memberService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }
>>>>>>> 648baeb (:memo: Swagger 추가)

    //프로필 이미지 업로드
    @Operation(summary = "프로필 이미지 업로드", description = "프로필 이미지를 업로드하고 URL을 반환합니다.", security = @SecurityRequirement(name = "JWT"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImageUploadResponse.class)))
    })
    @PostMapping("/me/upload-profile")
    public ResponseEntity<ImageUploadResponse> uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                                @AuthenticationPrincipal Long memberId) {
        String imageUrl = memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok().body(new ImageUploadResponse(imageUrl));
    }

    //회원 정보 조회
    @Operation(summary = "회원 정보 조회", description = "로그인된 사용자의 회원 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<MemberInfo> getInfo(@AuthenticationPrincipal Long memberId) {
        MemberInfo member = memberService.getById(memberId);

        return ResponseEntity.ok().body(member);
    }


    //회원 정보 수정
    @Operation(summary = "회원 정보 수정", description = "로그인된 사용자의 닉네임, 자기소개를 수정합니다.")
    @PutMapping("/me")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest updateRequest,
                                          @AuthenticationPrincipal Long memberId) {
        memberService.update(updateRequest, memberId);
        return ResponseEntity.ok(memberId);
    }


    //회원 탈퇴
    @Operation(summary = "회원 탈퇴", description = "로그인된 사용자의 계정을 삭제합니다.")
    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.noContent().build();
    }
}