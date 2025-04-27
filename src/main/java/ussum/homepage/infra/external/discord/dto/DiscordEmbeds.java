package ussum.homepage.infra.external.discord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiscordEmbeds(
    @JsonProperty("description")
    String decription
) {

}
