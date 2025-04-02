package ussum.homepage.global.event;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ussum.homepage.infra.external.discord.dto.DiscordEmbeds;
import ussum.homepage.infra.external.discord.dto.DiscordWebhookRequest;
import ussum.homepage.domain.discord.DiscordPort;

@Component
@RequiredArgsConstructor
public class DiscordEventHandler {

    private final DiscordPort discordPort;

    @EventListener
    public void send(String message) {
        DiscordEmbeds embeds = new DiscordEmbeds(message);
        DiscordWebhookRequest request = new DiscordWebhookRequest(
            "🚨에러 발생🚨",
            List.of(embeds)
        );
        discordPort.sendMessage(request);
    }
}
