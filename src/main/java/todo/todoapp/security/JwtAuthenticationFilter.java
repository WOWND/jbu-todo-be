package todo.todoapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

<<<<<<< HEAD
        // 로그인과 이미지 패스
        if (path.equals("/kakao/login") || path.startsWith("/images/")) {
=======

        if (path.endsWith("/login") || path.equals("/api/members/exists") || path.equals("/api/members/signup") || path.startsWith("/images/")) {
>>>>>>> 648baeb (:memo: Swagger 추가)
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);
        log.info("요청 받은 토큰{}", token);

        //회원가입은 임시토큰을 발급해주자
        if (path.equals("/kakao/signup")) {
            if (token == null || !"temp".equals(jwtProvider.getTokenType(token)) || !jwtProvider.validateToken(token, "temp")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("임시 토큰만 접근할 수 있습니다.");
                return;
            }
            Long kakaoId = jwtProvider.getIdFromToken(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(kakaoId, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }

        //나머지 api는 엑세스 토큰으로 접근
        if (token != null && jwtProvider.validateToken(token, "access")) {
            Long memberId = jwtProvider.getIdFromToken(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(memberId, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
