package ussum.homepage.domain.post.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.annotation.DirtiesContext;
import ussum.homepage.domain.event.LikeCountChangedEvent;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.infra.kafka.KafkaProducer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@DisplayName("<Kafka Petition Post Processor Test>")
public class PetitionPostProcessorKafkaTest {

    private static final String TOPIC = "post-like-count-changed";

    @Autowired
    private KafkaProducer kafkaProducer;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PetitionPostProcessor petitionPostProcessor;

    @Autowired
    private KafkaTemplate<String, LikeCountChangedEvent> kafkaTemplate;

    private CountDownLatch latch = new CountDownLatch(1);
    private LikeCountChangedEvent receivedEvent;

    @Test
    @DisplayName("좋아요 수 변경 이벤트가 Kafka를 통해 정상적으로 전송되고 처리되어야 한다")
    public void testLikeCountChangedEventKafkaFlow() throws InterruptedException {
        // given
        Long postId = 1L;
        LikeCountChangedEvent event = new LikeCountChangedEvent(postId);
        Post mockPost = mock(Post.class);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(mockPost));

        // when
        kafkaProducer.sendLikeCountChangedEvent(postId);

        // then
        assertThat(latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(receivedEvent).isNotNull();
        assertThat(receivedEvent.getPostId()).isEqualTo(postId);

        verify(petitionPostProcessor, timeout(5000)).onLikeCountChanged(postId);
    }

    @KafkaListener(topics = TOPIC, groupId = "test-group")
    public void listen(@Payload LikeCountChangedEvent event) {
        System.out.println("Received event: " + event);
        this.receivedEvent = event;
        latch.countDown();
    }
}