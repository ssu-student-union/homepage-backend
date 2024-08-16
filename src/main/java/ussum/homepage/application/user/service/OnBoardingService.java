package ussum.homepage.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.service.StudentCsvReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberAppender;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.error.exception.GeneralException;

import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnBoardingService {
    private final UserReader userReader;
    private final StudentCsvReader studentCsvReader;
    private final MemberAppender memberAppender;
    private final UserModifier userModifier;

    @Transactional
    public void saveUserOnBoarding(Long userId, OnBoardingRequest request){
        User user = userReader.getUserWithId(userId);
        String studentId = request.getStudentId();
//        studentCsvReader.getStudentWithStudentId(Long.valueOf(studentId), request)
//                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        userModifier.updateOnBoardingUser(request);
        memberAppender.saveMember(Member.of(null, false,
                request.getMemberCode(), request.getMajorCode(),
                userId, null));
    }
}