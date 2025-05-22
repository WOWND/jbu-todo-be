package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import todo.todoapp.dto.member.MemberInfo;
import todo.todoapp.dto.member.MemberUpdateRequest;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.MemberRepository;

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

    @Value("${ip.address}")
    private String serverUrl;

    @Transactional(readOnly = true)
    public MemberInfo getMember(Long memberId) {
        Member member = findById(memberId);

        return MemberInfo.from(member);
    }

    //회원 삭제
    public void deleteMember(Long memberId) {
        Member member = findById(memberId);
        memberRepository.delete(member);
    }


    //프로필 사진 변경
    public String uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = findById(memberId);

        String uploadDir = "uploads/profile/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

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
        return imageUrl;
    }

    //회원 수정
    public void updateMember(MemberUpdateRequest request,Long memberId) {
        Member member = findById(memberId);
        member.updateProfile(request.nickName, request.introText);
    }



    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
