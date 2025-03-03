package ussum.homepage.application.notification.service;

import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ussum.homepage.application.user.service.UserService;

@Service
@RequiredArgsConstructor
public class DiscordWebhookService {
    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/1345355644236075018/ZMpO9d7Jh30jvSKf5U3gw9i9xoczFAX9f6DLN8YadBYBZDM9WxRpSr-kz1KyQbniikQA"; // 🔥 여기에 실제 웹훅 URL을 입력하세요


    public void sendToDiscord() {
        try {
            String jsonMessage = userService.generateDiscordMessage();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    DISCORD_WEBHOOK_URL, HttpMethod.POST, requestEntity, String.class);

        } catch (Exception e) {
            // 디스코드로 웹훅으로 인해 앱이 죽지 않게 따로 처리하지 않음.
            System.err.println("디스코드 웹훅 전송 실패: " + e.getMessage());
        }
    }
}

