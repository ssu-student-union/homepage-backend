package ussum.homepage.application.user.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.MyPageUpdateRequest;
import ussum.homepage.application.user.service.dto.response.MyPageInfoResponse;
import ussum.homepage.application.user.service.dto.response.UserInfoResponse;
import ussum.homepage.domain.comment.service.PostCommentModifier;
import ussum.homepage.domain.comment.service.PostReplyCommentModifier;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.service.StudentCsvReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberManager;
import ussum.homepage.domain.member.service.MemberModifier;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.service.PostModifier;
import ussum.homepage.domain.postlike.service.PostReactionModifier;
import ussum.homepage.domain.reaction.service.PostCommentReactionModifier;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionModifier;
import ussum.homepage.domain.user.MonthlySignupStats;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserAnalyzer;
import ussum.homepage.domain.user.service.UserManager;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.global.jwt.JwtTokenProvider;
import ussum.homepage.infra.jpa.user.UserMapper;


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
    private final UserManager userManager;
    private final UserAnalyzer userAnalyzer;
    private final PasswordEncoder passwordEncoder;
    private final PostModifier postModifier;
    private final MemberManager memberManager;
    private final MemberModifier memberModifier;
    private final PostCommentModifier postCommentModifier;
    private final PostCommentReactionModifier postCommentReactionModifier;
    private final PostReactionModifier postReactionModifier;
    private final PostReplyCommentModifier postReplyCommentModifier;
    private final PostReplyCommentReactionModifier postReplyCommentReactionModifier;


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

    public MyPageInfoResponse getMyPageInfo(Long userId) {
        User user = userReader.getUserWithId(userId);
        List<Member> members = memberReader.getMembersWithUserId(userId); // 예외 발생 가능
        //TODO(inho): groups 테이블에서 학생자치기구 id가 11임. 임시로 하드코딩 해둠. 방법찾으면 바꿀 예정
        boolean isUnion = members.stream()
                .filter(member -> member.getGroupId() != null)
                .anyMatch(member -> member.getGroupId() == 11);

        return MyPageInfoResponse.of(user, members.get(0), isUnion);
    }

    public MyPageInfoResponse updateMyPageInfo(Long userId, MyPageUpdateRequest myPageUpdateRequest) {

        User user = userReader.getUserWithId(userId);
        List<Member> members = memberReader.getMembersWithUserId(userId); // 예외 발생 가능
        //TODO(inho): groups 테이블에서 학생자치기구 id가 11임. 임시로 하드코딩 해둠. 방법찾으면 바꿀 예정. getMyPageInfo에 쓰인 부분과 합칠 예정.
        boolean isUnion = members.stream()
                .filter(member -> member.getGroupId() != null)
                .anyMatch(member -> member.getGroupId() == 11);

        // 자치기구인지 확인
        if (!isUnion) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 기존 비밀번호 확인
        userManager.validatePassword(myPageUpdateRequest.currentPassword(), user.getPassword()); // 예외 발생 가능

        // 새 비밀번호 == 새 비밀번호 확인
        if (myPageUpdateRequest.newPassword().equals(myPageUpdateRequest.confirmNewPassword())) {
            String password = passwordEncoder.encode(myPageUpdateRequest.newPassword());
            user.setPassword(password);
        } else {
            throw new GeneralException(ErrorStatus.CONFIRM_PASSWORD_NOT_MATCH);
        }

        user.setNickname(myPageUpdateRequest.nickname());
        user = userModifier.save(user);

        return MyPageInfoResponse.of(user, members.get(0), isUnion);
    }

    @Transactional
    public void deleteUser(Long userId) {
        postModifier.deleteAllByUserId(userId);

        postReplyCommentReactionModifier.deletePostReplyCommentReactionWithUserId(userId);
        postReplyCommentModifier.deletePostReplyCommentWithUserId(userId);

        postCommentReactionModifier.deletePostCommentReactionWithUserId(userId);
        postCommentModifier.deleteCommentWithUserId(userId);

        postReactionModifier.deletePostReactionWithUserId(userId);

        memberModifier.deleteMemberWithUserId(userId);
        userModifier.deleteUser(userId);
    }

    public String generateDiscordMessage() {
        Long totalUsers = userAnalyzer.getTotalUserCount();
        Long yearlyUsers = userAnalyzer.getNewUserCountBetween(LocalDateTime.now().withDayOfYear(1), LocalDateTime.now());
        Long monthlyUsers = userAnalyzer.getNewUserCountBetween(LocalDateTime.now().withDayOfMonth(1), LocalDateTime.now());
        Long dailyUsers = userAnalyzer.getNewUserCountBetween(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS), LocalDateTime.now());
        Long last5MinUsers = userAnalyzer.getNewUserCountBetween(LocalDateTime.now().minusMinutes(5), LocalDateTime.now());

        List<MonthlySignupStats> monthlyStats = userAnalyzer.getMonthlySignupStats(LocalDateTime.now().getYear());
        StringBuilder monthlyStatsBuilder = new StringBuilder();
        for (MonthlySignupStats stat : monthlyStats) {
            monthlyStatsBuilder.append(String.format("%d월: %d명\n", stat.getMonth(), stat.getCount()));
        }

        // 🎯 JSON 변환을 위해 Map 사용
        Map<String, Object> payload = new HashMap<>();
        payload.put("content", "🔥사용자 통계가 업데이트되었습니다🔥");
        payload.put("embeds", List.of(Map.of(
                "title", "📊 사용자 통계",
                "fields", List.of(
                        Map.of("name", "[전체 가입자 수]", "value", totalUsers, "inline", false),
                        Map.of("name", "[신규 가입자 수]", "value", String.format("올해: %d\n이번 달: %d\n오늘: %d", yearlyUsers, monthlyUsers, dailyUsers), "inline", false),
                        Map.of("name", "[지난 5분간 가입자]", "value", last5MinUsers, "inline", false),
                        Map.of("name", "[월별 가입자]", "value", monthlyStatsBuilder.toString(), "inline", false)
                ),
                "footer", Map.of("text", "업데이트: " + LocalDateTime.now())
        )));

        try {
            // 🎯 JSON 변환
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("JSON parsing error", e);
        }
    }

}