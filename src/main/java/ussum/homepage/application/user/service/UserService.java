package ussum.homepage.application.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.MyPageUpdateRequest;
import ussum.homepage.application.user.service.dto.response.MyPageInfoResponse;
import ussum.homepage.application.user.service.dto.response.UserInfoResponse;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.service.StudentCsvReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.config.auth.UserId;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.global.jwt.JwtTokenProvider;
import ussum.homepage.infra.jpa.user.UserMapper;
import ussum.homepage.infra.utils.DateUtils;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final StudentCsvReader studentCsvReader;
    private final MemberReader memberReader;
    private final JwtTokenProvider provider;
    private final UserReader userReader;
    private final UserModifier userModifier;
    private final UserMapper userMapper;

    /*
        * @param accessToken
        * 메소드 수정 필요
     */
    public UserInfoResponse getUserInfo(String accessToken) {
        Long userId = provider.getSubject(accessToken);
        User user = userReader.getUserWithId(userId);

        if (user.getStudentId() != null) {
            Optional<StudentCsv> optionalStudentCsv = studentCsvReader.getStudentWithStudentId(Long.valueOf(user.getStudentId()));
            if (optionalStudentCsv.isPresent()) {
                StudentCsv studentCsv = optionalStudentCsv.get();
                List<Member> members = memberReader.getMembersWithUserId(userId);
                Member member = members.isEmpty() ? null : members.get(0);
                return UserInfoResponse.of(studentCsv, member);
            }
        }

        List<Member> members = memberReader.getMembersWithUserId(userId);
        Member member = members.isEmpty() ? null : members.get(0);
        return UserInfoResponse.of(user, member);
    }

    public MyPageInfoResponse getMyPageInfo(Long userId){
        User user = userReader.getUserWithId(userId);
        List<Member> members = memberReader.getMembersWithUserId(userId);
        Member member = members.isEmpty() ? null : members.get(0);

        return MyPageInfoResponse.of(user, member);
    }
    public MyPageInfoResponse updateMyPageInfo(Long userId, MyPageUpdateRequest myPageUpdateRequest) {
        if (!MyPageUpdateRequest.validate(myPageUpdateRequest)){
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        User originUser = userReader.getUserWithId(userId);
        User user = User.of(userId, null,null, originUser.getProfileImage(), myPageUpdateRequest.name(), myPageUpdateRequest.password()
                , originUser.getAccountId(), LocalDateTime.now(), DateUtils.parseFromCustomString(originUser.getCreatedAt()), originUser.getRefreshToken());
        user = userModifier.save(user);

        return MyPageInfoResponse.of(user, memberReader.getMemberWithUserId(userId));
    }
}