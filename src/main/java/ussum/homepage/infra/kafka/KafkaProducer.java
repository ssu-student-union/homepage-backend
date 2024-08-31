package ussum.homepage.infra.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.event.LikeCountChangedEvent;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC = "post-like-count-changed";

    @Autowired
    private KafkaTemplate<String, LikeCountChangedEvent> kafkaTemplate;

    public void sendLikeCountChangedEvent(Long postId) {
        LikeCountChangedEvent event = new LikeCountChangedEvent(postId);
        CompletableFuture<SendResult<String, LikeCountChangedEvent>> future = kafkaTemplate.send(TOPIC, event);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + event + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" + event + "] due to : " + ex.getMessage());
            }
        });
    }
}