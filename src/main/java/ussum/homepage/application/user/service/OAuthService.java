package ussum.homepage.application.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ussum.homepage.application.user.service.dto.response.KakaoUserInfoResponseDto;
import ussum.homepage.application.user.service.dto.response.UserOAuthResponse;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.external.oauth.KakaoApiProvider;
import ussum.homepage.global.jwt.JwtTokenInfo;
import ussum.homepage.global.jwt.JwtTokenProvider;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OAuthService {
    private final KakaoApiProvider kakaoApiProvider;
    private final UserReader userReader;
    private final UserModifier userModifier;
    private final JwtTokenProvider provider;


    @Transactional
    public String getKakaoLogin(){
        String authUrl = kakaoApiProvider.getKakaoLogin();
        return authUrl;
    }

    @Transactional
    public UserOAuthResponse signIn(String code) {
        String kakaoToken = kakaoApiProvider.getAccessToken(code);
        KakaoUserInfoResponseDto userInfo = kakaoApiProvider.getUserInfo(kakaoToken);
        Optional<User> optionalUser = userReader.getUserWithKakaoId(Long.toString(userInfo.getId()));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            JwtTokenInfo tokenInfo = issueAccessTokenAndRefreshToken(user);
            updateRefreshToken(tokenInfo.getRefreshToken(), user);
            return UserOAuthResponse.of(user, tokenInfo, Objects.isNull(user.getStudentId()));
        }else {
            // 사용자가 존재하지 않거나 예외가 발생한 경우에는 새로운 사용자로 처리
            User savedUser = saveUser(userInfo);
            JwtTokenInfo tokenInfo = issueAccessTokenAndRefreshToken(savedUser);
            Boolean isFirstLogin = Objects.isNull(savedUser.getStudentId());
            updateRefreshToken(tokenInfo.getRefreshToken(), savedUser);
            return UserOAuthResponse.of(savedUser, tokenInfo, isFirstLogin);
        }
    }
    private void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        userModifier.save(user);
    }

    private User saveUser(KakaoUserInfoResponseDto userInfo) {
        User createdUser = getUserByKakaoUserInfo(userInfo);
        log.info("카카오 id, 프로필 url 저장 완료");
        return userModifier.save(createdUser);

    }

    private User getUserByKakaoUserInfo(KakaoUserInfoResponseDto userInfo) {
        Optional<User> optionalUser = userReader.getUserWithKakaoId(Long.toString(userInfo.getId()));
        return optionalUser.orElseGet(() -> User.createUser(userInfo));
    }

    private JwtTokenInfo issueAccessTokenAndRefreshToken(User user) {
        return provider.issueToken(user);
    }

}
