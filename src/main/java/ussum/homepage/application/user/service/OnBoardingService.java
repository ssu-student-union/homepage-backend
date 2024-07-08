package ussum.homepage.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserFormatter;
import ussum.homepage.domain.user.service.UserAppender;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.jwt.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OnBoardingService {
    private final JwtTokenProvider provider;
    private final UserAppender userAppender;
    private final UserFormatter userFormatter;
    private final UserModifier userModifier;
    private final UserReader userReader;

    @Transactional
    public void getUserOnBoarding(String accessToken, OnBoardingRequest request){
        Long kakaoId = provider.getSubject(accessToken);
        Optional<User> optionalUser = userReader.findBykakaoId(kakaoId);
        User user = optionalUser.get();
        user.updateOnBoardingUser(request);
        userModifier.save(user);
    }
}