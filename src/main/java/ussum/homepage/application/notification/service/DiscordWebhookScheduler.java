package ussum.homepage.application.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscordWebhookScheduler {
    private final DiscordWebhookService discordWebhookService;


    @Scheduled(cron = "0 0 0 * * ?")
    public void sendUserStatisticsPeriodically() {
        discordWebhookService.sendToDiscord();
    }
}
