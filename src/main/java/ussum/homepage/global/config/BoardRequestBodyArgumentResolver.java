package ussum.homepage.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import ussum.homepage.application.post.service.dto.request.GeneralPostCreateRequest;
import ussum.homepage.application.post.service.dto.request.RightsPostCreateRequest;
import ussum.homepage.global.config.custom.BoardRequestBody;

@Component
public class BoardRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper objectMapper;

    public BoardRequestBodyArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BoardRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Map<String, String> pathVariables = (Map<String, String>) webRequest.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                RequestAttributes.SCOPE_REQUEST
        );

        String boardCode = pathVariables.get("boardCode");

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Class<?> targetClass = "인권신고게시판".equals(boardCode) ?
                RightsPostCreateRequest.class : GeneralPostCreateRequest.class;

        Object result = objectMapper.readValue(body, targetClass);

        return result;
    }
}
