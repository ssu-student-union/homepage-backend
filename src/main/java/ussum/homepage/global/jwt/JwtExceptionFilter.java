package ussum.homepage.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ussum.homepage.global.config.auth.UserAuthentication;
import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.exception.UnauthorizedException;
import ussum.homepage.global.error.status.ErrorStatus;

import java.io.IOException;

/*
Jwt 관련 exception 처리 filter
 */
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private final JwtTokenProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = getAccessTokenFromHttpServletRequest(request);
        jwtProvider.validateAccessToken(accessToken);
        final Long userId = jwtProvider.getSubject(accessToken);
        setAuthentication(request, userId);
        filterChain.doFilter(request, response);
    }
    private String getAccessTokenFromHttpServletRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER)) {
            return accessToken.substring(BEARER.length());
        }
        throw new UnauthorizedException(ErrorStatus.INVALID_ACCESS_TOKEN);
    }

    private void setAuthentication(HttpServletRequest request, Long userId) {
        UserAuthentication authentication = new UserAuthentication(userId, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
