package ussum.homepage.domain.user;
import lombok.*;
import ussum.homepage.application.admin.service.dto.request.CouncilSignInRequest;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.application.user.service.dto.response.KakaoUserInfoResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String name;
    private String studentId;
    private String kakaoId;
    private String profileImage;
    private String accountId;
    private String password;
    private String createdAt;
    private String updatedAt;
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static User of(Long id, String name, String studentId, String kakaoId, String profileImage,
                          String accountId, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String refreshToken) {
        return new User(id, name, studentId, kakaoId, profileImage, accountId, password, String.valueOf(createdAt), String.valueOf(updatedAt), refreshToken);
    }

    public static User createUser(KakaoUserInfoResponseDto userInfoResponseDto) {
        return User.builder()
                .kakaoId(Long.toString(userInfoResponseDto.getId()))
                .profileImage(userInfoResponseDto.getKakaoAccount().getProfile().getProfileImageUrl())
                .build();
    }

    public static User createCouncilUser(CouncilSignInRequest request, String password){
        return User.builder()
                .accountId(request.accountId())
                .password(password)
                .name(request.councilName())
                .build();
    }
}
