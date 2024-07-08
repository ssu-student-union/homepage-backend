package ussum.homepage.domain.user.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoUserInfo {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("profile")
    private String profile;

    public static KakaoUserInfo of(Long id, String profile){
        return KakaoUserInfo.builder()
                .id(id)
                .profile(profile)
                .build();
    }
}
