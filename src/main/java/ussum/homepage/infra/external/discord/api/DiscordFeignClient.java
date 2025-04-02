package ussum.homepage.infra.external.discord.api;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ussum.homepage.infra.external.discord.dto.DiscordWebhookRequest;
import ussum.homepage.infra.config.OpenFeignConfig;

@FeignClient(
    name = "discordWebhook",
    url = "${webhook.discord.url}",
    configuration = OpenFeignConfig.class
)
public interface DiscordFeignClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json; charset=UTF-8")
    ResponseEntity<Void> sendWebhook(@RequestBody DiscordWebhookRequest request);
}
