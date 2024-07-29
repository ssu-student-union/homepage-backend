package ussum.homepage.domain.user;
import lombok.*;
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
    private String profileImage;
    private String kakaoId;
    private String createdAt;
    private String updatedAt;
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    // 수정 필요
    public static User of(Long id, String name, String studentId, String profileImage, String kakaoId,
                          LocalDateTime createdAt, LocalDateTime updatedAt, String refreshToken) {
        return new User(id, name, studentId, profileImage, kakaoId, String.valueOf(createdAt), String.valueOf(updatedAt), refreshToken);
    }

    public static User createUser(KakaoUserInfoResponseDto userInfoResponseDto) {
        return User.builder()
                .kakaoId(Long.toString(userInfoResponseDto.getId()))
                .profileImage(userInfoResponseDto.getKakaoAccount().getProfile().getProfileImageUrl())
                .build();
    }

    public void updateOnBoardingUser(OnBoardingRequest request){
        this.setName(request.getName());
        this.setStudentId(request.getStudentId());    }
}
