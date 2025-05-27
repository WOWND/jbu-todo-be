package todo.todoapp.service;


import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import todo.todoapp.entity.Category;
import todo.todoapp.exception.SignupRequiredException;
import todo.todoapp.dto.member.LoginResponse;
import todo.todoapp.dto.kakao.KakaoTokenResponse;
import todo.todoapp.dto.kakao.KakaoUserInfoResponse;
import todo.todoapp.dto.member.KakaoSignupRequest;
import todo.todoapp.entity.Member;
import todo.todoapp.repository.MemberRepository;
import todo.todoapp.security.JwtProvider;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final CategoryService categoryService;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${app.domain}")
    private String serverUrl;


    private static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private static final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";


    private String getAccessTokenFromKakao(String code) {
        KakaoTokenResponse kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponse.class)
                .block();


        log.info(" [getAccessTokenFromKakao] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
        log.info(" [getAccessTokenFromKakao] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
        return kakaoTokenResponseDto.getAccessToken();
    }


    private KakaoUserInfoResponse getUserInfo(String accessToken) {

        KakaoUserInfoResponse userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();

        //기본 프사라면 우리의 기본 프사를 등록
        if (userInfo.getKakaoAccount().getProfile().getIsDefaultImage()) {
            userInfo.getKakaoAccount().getProfile().setProfileImageUrl(serverUrl + "/images/profile/default_image.jpg");
        }
        log.info("[ getUserInfo ] Auth ID ---> {} ", userInfo.getId());
        log.info("[ getUserInfo ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
        log.info("[ getUserInfo ] ProfileImageUrl ---> {} ", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        return userInfo;
    }



    @Transactional(readOnly = true)
    public LoginResponse kakaoLogin(String code) {
        String kakaoAccessToken = getAccessTokenFromKakao(code);
        KakaoUserInfoResponse userInfo = getUserInfo(kakaoAccessToken);

        Long kakaoId = userInfo.getId();

        Optional<Member> existingMember = memberRepository.findByKakaoId(kakaoId);
        if (existingMember.isPresent()) { //회원이면
            Member member = existingMember.get();

            String accessToken = jwtProvider.createAccessToken(member.getId());
            //String refreshToken = jwtProvider.createRefreshToken(member.getId());
            return LoginResponse.from(member, accessToken);

        } else {
            String temporaryToken = jwtProvider.createTemporaryToken(kakaoId);
            throw new SignupRequiredException(temporaryToken,
                    userInfo.getKakaoAccount().getProfile().getNickName(),
                    userInfo.getKakaoAccount().getProfile().getProfileImageUrl()
            );
        }
    }

    public LoginResponse signup(KakaoSignupRequest request, Long kakaoId) {
        Member member = memberRepository.save(request.toEntity(kakaoId));
        categoryService.createDefaultCategory(member);

        String accessToken = jwtProvider.createAccessToken(member.getId());
        //String refreshToken = jwtProvider.createRefreshToken(member.getId());


        return LoginResponse.from(member, accessToken);
    }
}
