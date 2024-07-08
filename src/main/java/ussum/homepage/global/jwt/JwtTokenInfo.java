package ussum.homepage.global.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtTokenInfo {
    private String accessToken;
    private String refreshToken;

    public static JwtTokenInfo of(String accessToken, String refreshToken) {
        return new JwtTokenInfo(accessToken, refreshToken);
    }
}