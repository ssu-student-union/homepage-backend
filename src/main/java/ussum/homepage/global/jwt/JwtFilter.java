package ussum.homepage.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ussum.homepage.global.error.exception.UnauthorizedException;

import java.io.IOException;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ACCESS_TOKEN;
import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ACCESS_TOKEN_VALUE;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtTokenProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessTokenFromRequest(request);
//        provider.validateAccessToken(accessToken); // 예외 발생 가능
//        Long userId = provider.getSubject(accessToken);
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (accessToken != null) {
            try {
                provider.validateAccessToken(accessToken);
                Long userId = provider.getSubject(accessToken);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext(); // 토큰 검증 실패 시
                throw new UnauthorizedException(INVALID_ACCESS_TOKEN_VALUE);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(TOKEN_PREFIX)) {
            return accessToken.substring(TOKEN_PREFIX.length());
        }
//        throw new UnauthorizedException(INVALID_ACCESS_TOKEN);
        return null; // 토큰이 없거나 형식이 잘못된 경우 null을 반환
    }
}
