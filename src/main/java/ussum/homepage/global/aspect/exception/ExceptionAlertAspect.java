package ussum.homepage.global.aspect.exception;


import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.event.DiscordEventHandler;
import ussum.homepage.infra.utils.HttpUtils;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionAlertAspect {

    private final DiscordEventHandler discordEventHandler;
    private final HttpUtils httpUtils;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestControllerAdvice *)")
    public void restControllerAdviceMethods() {
    }

    @Around("restControllerAdviceMethods()")
    public Object logAndNotifyException(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object response = joinPoint.proceed();
        Assert.isInstanceOf(ResponseEntity.class, response);
        ResponseEntity<?> entity = (ResponseEntity<?>) response;

        // request 가져옴
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
        String requestURI = request.getRequestURI();
        int statusCode = entity.getStatusCode().value();
        Object body = entity.getBody() != null ? entity.getBody() : "No response body";
        String operationSummary = httpUtils.getOperationSummary(methodSignature.getMethod());
        Long userId = httpUtils.getUserId(joinPoint.getArgs());

        String message = """
            📅 시간: %s
            🔗 API 경로: %s
            ❌ 상태 코드: %d
            💬 메시지: %s
            """
            .formatted(
                LocalDateTime.now(),
                requestURI,
                statusCode,
                ((ApiResponse<?>) body).getMessage()
            );

        log.error("""
                
                ----Request Log----
                API : {}
                Method : {}
                API Path : {}
                User Id : {}
                Query String : {}
                """,
            operationSummary, request.getMethod(), requestURI, userId, request.getQueryString());

        log.error("""
                
                ----Response Log----
                API : {}
                API Path : {}
                Status Code : {}
                Error Message : {}
                """,
            operationSummary, requestURI, entity.getStatusCode().value(),
            ((ApiResponse<?>) body).getMessage());

        discordEventHandler.send(message);
        return response;
    }
}
