package ussum.homepage.domain.user.token;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@RedisHash(value = "refreshToken", timeToLive = 259200000)
public class RefreshToken {
    @Id
    private Long id;
    private String refreshToken;

    public static RefreshToken of(Long userId, String refreshToken) {
        return RefreshToken.builder()
                .id(userId)
                .refreshToken(refreshToken)
                .build();
    }

    public void updateToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
}
