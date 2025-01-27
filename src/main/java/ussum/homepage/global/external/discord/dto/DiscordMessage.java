package ussum.homepage.global.external.discord.dto;

public record DiscordMessage(
        String content
) {
    public static DiscordMessage createDiscordMessage(String message) {
        return new DiscordMessage(message);
    }
}
