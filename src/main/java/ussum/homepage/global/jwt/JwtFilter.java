package ussum.homepage.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;
import ussum.homepage.global.error.exception.UnauthorizedException;

import java.io.IOException;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ACCESS_TOKEN;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtTokenProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessTokenFromRequest(request);
        provider.validateAccessToken(accessToken); // 예외 발생 가능
        Long userId = provider.getSubject(accessToken);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(TOKEN_PREFIX)) {
            return accessToken.substring(TOKEN_PREFIX.length());
        }
        throw new UnauthorizedException(INVALID_ACCESS_TOKEN);
    }
}
