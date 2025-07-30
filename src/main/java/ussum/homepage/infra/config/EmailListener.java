package ussum.homepage.infra.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.PostManageService;
import ussum.homepage.application.post.service.dto.request.EmailRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailListener {
    private final PostManageService postManageService;
    private final ObjectMapper objectMapper;

    public void handleMessage(String payload, String topic) throws JsonProcessingException {
	EmailRequest req = objectMapper.readValue(payload, EmailRequest.class);
	postManageService.sendEmail(req.getSubject(), req.getBody(), req.getTo());
	log.info("\nsuccess send mail \n"+ req.getSubject() + "\n" + req.getBody() + "\n" + req.getTo());
    }

}
