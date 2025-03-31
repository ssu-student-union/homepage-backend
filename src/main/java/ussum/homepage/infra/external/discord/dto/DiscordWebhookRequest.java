package ussum.homepage.infra.external.discord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiscordWebhookRequest(
    @JsonProperty("content")
    String content,
    @JsonProperty("embeds")
    List<DiscordEmbeds> embeds
) {

}