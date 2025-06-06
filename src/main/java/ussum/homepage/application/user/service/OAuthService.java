package ussum.homepage.application.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.mail.MailErrorHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ussum.homepage.application.user.service.dto.request.CouncilLoginRequest;
import ussum.homepage.application.user.service.dto.response.CouncilLoginResponse;
import ussum.homepage.application.user.service.dto.response.KakaoUserInfoResponseDto;
import ussum.homepage.application.user.service.dto.response.UserOAuthResponse;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.exception.UserNotFoundException;
import ussum.homepage.domain.user.service.UserManager;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.external.oauth.KakaoApiProvider;
import ussum.homepage.global.jwt.JwtTokenInfo;
import ussum.homepage.global.jwt.JwtTokenProvider;
import ussum.homepage.infra.utils.HttpUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OAuthService {
    private final KakaoApiProvider kakaoApiProvider;
    private final UserReader userReader;
    private final MemberReader memberReader;
    private final GroupReader groupReader;
    private final UserModifier userModifier;
    private final JwtTokenProvider provider;
    private final UserManager userManager;


    @Transactional
    public String getKakaoLogin(HttpServletRequest request) {
        String baseUrl = clientBaseURLBuilder(request);
        return kakaoApiProvider.getKakaoLogin(baseUrl + "/auth/callback");
    }

    @Transactional
    public UserOAuthResponse signIn(String code, String origin) {
        String kakaoToken = kakaoApiProvider.getAccessToken(code, origin + "/auth/callback");
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

    @Transactional
    public CouncilLoginResponse councilLogin(CouncilLoginRequest request){
        User user = userReader.getUserWithAcountId(request.accountId()); // 예외 발생 가능
        userManager.validatePassword(request.password(), user.getPassword()); // 예외 발생 가능

        List<Member> memberList = memberReader.getMembersWithUserId(user.getId()); // List 비어있을 시 에러 발생
        String memberCode = memberList.get(0).getMemberCode();
        String majorCode = memberList.get(0).getMajorCode();

        List<String> groupCodeList = memberList.stream()
                .map(member -> groupReader.getGroupByGroupId(member.getGroupId()))
                .map(Group::getGroupCode)
                .toList();

        JwtTokenInfo tokenInfo = issueAccessTokenAndRefreshToken(user);
        updateRefreshToken(tokenInfo.getRefreshToken(), user);
        return CouncilLoginResponse.of(tokenInfo, groupCodeList, memberCode, majorCode);

        //        Member member = memberReader.getMemberWithUserId(user.getId()); // 멤버가 여러개 반환되는 유저가 많음
        //        Group group = groupReader.getGroupByGroupId(memberList.get(0).getGroupId());
    }


    private void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        userModifier.save(user);
    }

    private User saveUser(KakaoUserInfoResponseDto userInfo) {
        User createdUser = getUserByKakaoUserInfo(userInfo);
        return userModifier.save(createdUser);

    }

    private User getUserByKakaoUserInfo(KakaoUserInfoResponseDto userInfo) {
        Optional<User> optionalUser = userReader.getUserWithKakaoId(Long.toString(userInfo.getId()));
        return optionalUser.orElseGet(() -> User.createUser(userInfo));
    }

    private JwtTokenInfo issueAccessTokenAndRefreshToken(User user) {
        return provider.issueToken(user);
    }

    private String clientBaseURLBuilder(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        String portString = "";
        if (scheme.equals("https") && serverPort != 443 ||
                scheme.equals("http") && serverPort != 80) {
            portString = ":" + serverPort;
        }
        return scheme + "://" + serverName + portString;
    }
}
