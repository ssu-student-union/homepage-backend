package ussum.homepage.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ussum.homepage.global.error.status.ErrorStatus;

import java.io.IOException;

/*
필요한 권한이 존재하지 않는 경우,
403 Forbidden 에러를 리턴하기 위한 class
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handleException(response);
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(ErrorStatus._FORBIDDEN.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString((ErrorStatus._FORBIDDEN.getHttpStatus().value())));
    }
}
