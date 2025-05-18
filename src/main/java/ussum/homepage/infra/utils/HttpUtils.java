package ussum.homepage.infra.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class HttpUtils {

    private final ObjectMapper objectMapper;

    public String getOperationSummary(Method method) {
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            return operation.summary();
        }
        return "Summary 없음";
    }

    public Long getUserId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        return null;
    }

    public String getRequestBody(Object[] args) {
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

    private boolean isDto(Object obj) {
        if (obj == null) {
            return false;
        }
        String packageName = obj.getClass().getName();
        return packageName.startsWith("ussum.homepage") && packageName.contains(".dto");
    }
}
