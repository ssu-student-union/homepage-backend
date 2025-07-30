package ussum.homepage.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {
    @Bean
    RedisMessageListenerContainer redisListenerContainer(RedisConnectionFactory cf,
	    MessageListenerAdapter listenerAdapter) {
	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	container.setConnectionFactory(cf);
	// emailQueue에 대한 구독자 등록
	container.addMessageListener(listenerAdapter, new PatternTopic("emailQueue"));
	return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(EmailListener listener) {
	return new MessageListenerAdapter(listener, "handleMessage");
    }
}