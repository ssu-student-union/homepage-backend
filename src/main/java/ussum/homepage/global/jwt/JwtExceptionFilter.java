package ussum.homepage.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.exception.UnauthorizedException;
import ussum.homepage.global.error.status.ErrorStatus;

import java.io.IOException;

/*
Jwt 관련 exception 처리 filter
 */
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (UnauthorizedException e){
            handleUnauthorizedException(response, e);
        }catch (Exception e){
            handleException(response);
        }
    }

    private void handleUnauthorizedException(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        if (e instanceof UnauthorizedException ue) {
            response.setStatus(ue.getErrorReason().getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(ue.getErrorReason().getHttpStatus().value()));
        } else if (e instanceof InvalidValueException ie) {
            response.setStatus(ie.getErrorReason().getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(ie.getErrorReason().getHttpStatus().value()));
        }
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString((ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus().value())));
    }
}
