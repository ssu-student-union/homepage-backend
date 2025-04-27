package ussum.homepage.infra.external.discord;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ussum.homepage.infra.external.discord.dto.DiscordWebhookRequest;
import ussum.homepage.domain.discord.DiscordPort;

@Profile("dev")
@Component
public class DiscordDevSender implements DiscordPort {

    @Override
    public void sendMessage(DiscordWebhookRequest request) {
        // DEV 알림 전송 기능 일단 없음
    }
}
