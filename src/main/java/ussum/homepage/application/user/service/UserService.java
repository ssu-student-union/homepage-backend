package ussum.homepage.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.TokenRequest;
import ussum.homepage.application.user.service.dto.response.UserInfoResponse;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.service.StudentCsvReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final StudentCsvReader studentCsvReader;
    private final MemberReader memberReader;
    private final JwtTokenProvider provider;
    private final UserReader userReader;

    public UserInfoResponse getUserInfo(String accessToken){
        Long userId = provider.getSubject(accessToken);
        User user = userReader.getUserWithId(userId);
        StudentCsv studentCsv = studentCsvReader.getStudentWithStudentId(Long.valueOf(user.getStudentId())).get();
        Member member = memberReader.getMemberWithUserId(userId);
        return UserInfoResponse.of(studentCsv, member);
    }
}
