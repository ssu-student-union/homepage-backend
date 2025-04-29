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
    private String nickname;
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

    public static User of(Long id, String name, String nickname, String studentId, String kakaoId, String profileImage,
                          String accountId, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String refreshToken) {
        return new User(id, name, nickname, studentId, kakaoId, profileImage, accountId, password, String.valueOf(createdAt), String.valueOf(updatedAt), refreshToken);
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

    /**
     * 이름을 마스킹합니다.
     *
     * - 한 글자: 그대로 리턴           // ex) "이"   → "이"
     * - 두 글자: 첫 글자만 남기고 * 1개  // ex) "이진" → "이*"
     * - 세 글자 이상: 첫/마지막 글자만 남기고 나머지 * 처리
     *   ex) "장인호"   → "장*호"
     *   ex) "김영철수" → "김***수"
     */
    public static String maskedName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        int len = name.length();
        if (len == 1) {
            return name;
        }
        if (len == 2) {
            // 두 글자일 땐 뒤 한 글자만 마스킹
            return name.charAt(0) + "*";
        }
        // 세 글자 이상
        String stars = "*".repeat(len - 2);
        return name.charAt(0) + stars + name.charAt(len - 1);
    }
}
