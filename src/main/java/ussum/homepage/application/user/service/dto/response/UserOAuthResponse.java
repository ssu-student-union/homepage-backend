package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.user.User;
import ussum.homepage.global.jwt.JwtTokenInfo;

@Builder
public record UserOAuthResponse(
        Long id,
        String name,
        Long studentId,
        Boolean isFirst,
        String accessToken,
        String refreshToken,
        String profileImage,
        String createdAt
) {
    public static UserOAuthResponse of(User user, JwtTokenInfo tokenInfo, Boolean isFirst) {
        return UserOAuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .isFirst(isFirst)
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .createdAt(null)
                .build();
    }
}

