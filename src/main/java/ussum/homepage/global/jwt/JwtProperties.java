package ussum.homepage.global.jwt;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String secret;
    private int access_token_expire_time;
    private int refresh_token_expire_time;
}
