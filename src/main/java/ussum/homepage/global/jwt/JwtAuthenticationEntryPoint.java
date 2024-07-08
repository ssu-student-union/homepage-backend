package ussum.homepage.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.dto.ErrorReasonDto;
import ussum.homepage.global.error.status.ErrorStatus;

import java.io.IOException;

/*
유효한 자격증명을 제공하지 않고 접근 시,
401 Unauthorized 에러를 리턴하는 class
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        handleException(response);
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(ErrorStatus._UNAUTHORIZED.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString((ErrorStatus._UNAUTHORIZED.getHttpStatus().value())));
    }
}
