package ussum.homepage.global.aspect.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ussum.homepage.infra.utils.HttpUtils;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper; // JSON 변환용
    private final HttpUtils httpUtils;

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {
        // 요청 정보 로깅
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Long userId = httpUtils.getUserId(args);
        String httpMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        String operationSummary = httpUtils.getOperationSummary(methodSignature.getMethod());
        String queryString = request.getQueryString();

        //if(requestUri.contains("/v3/api")) return null;

        log.info("""
            
            ----Request Log----
            API : {}
            Method : {}
            User Id : {}
            API Path : {}
            Query String : {}
            """,
            operationSummary, httpMethod, userId, requestUri, queryString);

        // 실제 메소드 실행
        Object response;
        long startTime = System.currentTimeMillis();
        try {
            response = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw ExceptionUtils.throwUnchecked(throwable);
        }
        long executionTime = System.currentTimeMillis() - startTime;
        ResponseEntity<?> responseBody = null;
        if (response instanceof ResponseEntity<?>) {
            responseBody = (ResponseEntity<?>) response;
        }


        // 응답 정보 로깅
        if(!requestUri.contains("/v3/api")) {
            assert responseBody != null;
            log.info("""
                
                ----Response Log----
                API : {}
                API Path : {}
                Status Code : {}
                Execution Time : {}ms
                """,
            operationSummary, requestUri, responseBody.getStatusCode().value(),executionTime);
        }
        
        return response;
    }
}
