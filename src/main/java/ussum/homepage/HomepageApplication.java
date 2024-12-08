package ussum.homepage;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing // 배치 사용을 위한 선언
@EnableScheduling // 스케줄러 사용을 위한 선언
@EnableAspectJAutoProxy // AOP 사용을 위한 선언
@EnableFeignClients // Feign 사용을 위한 선언
@SpringBootApplication
public class HomepageApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomepageApplication.class, args);
	}

}

