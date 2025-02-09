package ussum.homepage.global.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper; // JSON 변환용

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {
        // 요청 정보 로깅
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Long userId = getUserId(args);
        String httpMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        String operationSummary = getOperationSummary(methodSignature.getMethod());
        String queryString = request.getQueryString();

        String requestBody = getRequestBody(args);
        log.info("\n----Request Log----\n"
                + "API : {}\n"
                + "Method : {}\n"
                + "User Id : {}\n"
                + "API Path : {}\n"
                + "Query String : {}\n"
                + "Request Body : {}\n"
                + "---------------",
            operationSummary, httpMethod, userId, requestUri, queryString, requestBody);

        // 실제 메소드 실행
        Object response;
        long startTime = System.currentTimeMillis();
        try {
            response = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        long executionTime = System.currentTimeMillis() - startTime;


        // 응답 정보 로깅
        String responseBody = getResponseBody(response);
        log.info("\n----Response Log----\n"
                + "API : {}\n"
                + "Response Body : {}\n"
                + "Execution Time : {}ms\n"
                + "---------------",
            operationSummary, responseBody, executionTime);

        return response;
    }

    private Long getUserId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        return null;
    }

    private String getRequestBody(Object[] args) {
        for (Object arg : args) {
            if (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse) && isDto(arg)) {
                try {
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arg);
                } catch (JsonProcessingException e) {
                    log.error("Failed to serialize request body: {}", e.getMessage());
                    return "Failed to serialize request body";
                }
            }
        }
        return "No Request Body";
    }


    private String getResponseBody(Object response) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize response body: {}", e.getMessage());
            return "Failed to serialize response body";
        }
    }

    private String getOperationSummary(Method method) {
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            return operation.summary();
        }
        return "Summary 없음";
    }

    private boolean isDto(Object obj) {
        if (obj == null) {
            return false;
        }
        String packageName = obj.getClass().getName();
        return packageName.startsWith("ussum.homepage") && packageName.contains(".dto");
    }

}