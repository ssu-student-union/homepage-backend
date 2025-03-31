package ussum.homepage.application.discord.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiscordEmbeds(
    @JsonProperty("description")
    String decription
) {

}
