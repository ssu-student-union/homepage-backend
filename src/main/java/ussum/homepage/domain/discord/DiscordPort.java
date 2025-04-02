package ussum.homepage.domain.discord;

import ussum.homepage.infra.external.discord.dto.DiscordWebhookRequest;

public interface DiscordPort {

    void sendMessage(DiscordWebhookRequest request);

}
