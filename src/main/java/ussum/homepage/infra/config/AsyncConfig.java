package ussum.homepage.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("mailTaskExecutor")
    public ThreadPoolTaskExecutor mailTaskExecutor() {
	ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
	exec.setCorePoolSize(2);
	exec.setMaxPoolSize(5);
	exec.setQueueCapacity(20);
	exec.setThreadNamePrefix("mail-");
	exec.initialize();
	return exec;
    }
}
