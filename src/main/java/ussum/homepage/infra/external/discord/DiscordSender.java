package ussum.homepage.infra.external.discord;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ussum.homepage.infra.external.discord.dto.DiscordWebhookRequest;
import ussum.homepage.domain.discord.DiscordPort;
import ussum.homepage.infra.external.discord.api.DiscordFeignClient;

@Profile("main")
@Component
@RequiredArgsConstructor
public class DiscordSender implements DiscordPort {

    private final DiscordFeignClient discordClient;

    @Override
    public void sendMessage(DiscordWebhookRequest request) {
        discordClient.sendWebhook(request);
    }
}
