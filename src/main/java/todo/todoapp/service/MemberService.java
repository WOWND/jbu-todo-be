package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import todo.todoapp.dto.member.*;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.MemberRepository;
import todo.todoapp.security.JwtProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CategoryService categoryService;


    @Value("${app.domain}")
    private String serverUrl;

    @Value("${app.default-profile}")
    public String defaultProfile;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return LoginResponse.from(member, jwtProvider.createAccessToken(member.getId()));
    }



    public LoginResponse signup(SignupRequest request) {
        if (existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        Member member = request.toEntity(passwordEncoder.encode(request.getPassword()),defaultProfile);
        Member saved = memberRepository.save(member);
        categoryService.createDefaultCategory(saved);
        return LoginResponse.from(member, jwtProvider.createAccessToken(saved.getId()));
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }


    //회원 조회
    @Transactional(readOnly = true)
    public MemberInfo getById(Long memberId) {
        Member member = findById(memberId);
        return MemberInfo.from(member);
    }

    //회원 탈퇴
    public void delete(Long memberId) {
        Member member = findById(memberId);
        memberRepository.delete(member);
    }


    //프로필 사진 변경
    public String uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = findById(memberId);

        //기존 이미지 url
        String currentUrl = member.getProfileUrl();

        // 새 프로필 업로드
        String uploadDir = "uploads/profile/";
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf('.') != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        }
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + (extension.isEmpty() ? "" : "." + extension);

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        String imageUrl = serverUrl + "/images/profile/" + fileName;
        member.uploadProfileImage(imageUrl);

        // 기존 프로필 이미지가 기본 프로필이 아니면 삭제
        if (currentUrl != null && !currentUrl.contains("default")) {
            String oldFileName = currentUrl.substring(currentUrl.lastIndexOf('/') + 1);
            Path oldFilePath = Paths.get("uploads/profile/").resolve(oldFileName);
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                log.warn("이전 프로필 이미지 삭제 실패: {}", oldFilePath, e);
            }
        }

        return imageUrl;
    }

    //회원 수정
    public void update(MemberUpdateRequest request, Long memberId) {
        Member member = findById(memberId);
        member.updateProfile(request.nickName, request.introText);
    }


    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
