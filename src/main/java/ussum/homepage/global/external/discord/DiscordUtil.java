package ussum.homepage.global.external.discord;

import static ussum.homepage.global.external.discord.dto.DiscordMessage.createDiscordMessage;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ussum.homepage.global.error.exception.GeneralException;

import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.global.external.discord.dto.DiscordMessage;
import ussum.homepage.global.external.discord.dto.EventMessage;

@RequiredArgsConstructor
@Component
public class DiscordUtil {
    private final DiscordFeignClient discordFeignClient;

    public void sendMessage(EventMessage eventMessage, String message) {
        DiscordMessage discordMessage = createDiscordMessage(eventMessage.getMessage() + message);
        sendMessageToDiscord(discordMessage);
    }

    private void sendMessageToDiscord(DiscordMessage discordMessage) {
        try {
            discordFeignClient.sendMessage(discordMessage);
        } catch (FeignException e) {
            throw new GeneralException(ErrorStatus.INVALID_DISCORD_MESSAGE);
        }
    }
}
