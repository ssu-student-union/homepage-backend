package ussum.homepage.infra.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.PostManageService;
import ussum.homepage.application.post.service.dto.request.EmailRequest;

@Component
@RequiredArgsConstructor
public class EmailListener {
    private final PostManageService postManageService;
    private final ObjectMapper objectMapper;

    public void handleMessage(String payload, String topic) throws JsonProcessingException {
	EmailRequest req = objectMapper.readValue(payload, EmailRequest.class);
	postManageService.sendEmail(req.getSubject(), req.getBody(), req.getTo());
    }

}
