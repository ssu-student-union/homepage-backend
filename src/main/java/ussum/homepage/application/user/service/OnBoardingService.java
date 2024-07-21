package ussum.homepage.application.user.service;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.application.user.service.dto.response.CsvOnBoardingResponse;
import ussum.homepage.application.user.service.dto.response.OnBoardingResponse;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserFormatter;
import ussum.homepage.domain.user.service.UserAppender;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.jwt.JwtTokenProvider;
import ussum.homepage.infra.utils.CsvUtils;
import ussum.homepage.infra.utils.S3utils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OnBoardingService {
    private final JwtTokenProvider provider;
    private final UserModifier userModifier;
    private final UserReader userReader;
    private final CsvUtils csvUtils;
    private final S3utils s3utils;

    @Transactional
    public CsvOnBoardingResponse getUserOnBoarding(String accessToken, OnBoardingRequest request){
        Long kakaoId = provider.getSubject(accessToken);
        Optional<User> optionalUser = userReader.findBykakaoId(kakaoId);

        // S3에 저장된 CSV파일 가져오기
        S3ObjectInputStream objectInputStream = s3utils.getFile("학생목록.csv");
        CsvOnBoardingResponse response = csvUtils.getOnBoardingResponseFromCsv(request, objectInputStream);

        /*
        TO DO // MemberEntity, GroupEntity 연결
         */
        User user = optionalUser.get();
        user.updateOnBoardingUser(request);
        userModifier.save(user);
        return response;
    }
}