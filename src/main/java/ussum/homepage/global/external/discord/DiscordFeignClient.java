package ussum.homepage.global.external.discord;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ussum.homepage.global.external.discord.dto.DiscordMessage;

@FeignClient(name = "${discord.name}", url = "${discord.webhook-url}")
public interface DiscordFeignClient {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        void sendMessage(@RequestBody DiscordMessage discordMessage);
}
