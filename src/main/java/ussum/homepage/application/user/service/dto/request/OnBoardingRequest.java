package ussum.homepage.application.user.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ussum.homepage.domain.member.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OnBoardingRequest {
    private String name;
    private String studentId;
    private String memberCode; // 대학
    private String majorCode; // 학부

//    public Member toDomain(Long userId, String memberCode, String majorCode){
//        return Member.of(
//                null,
//                false,
//                memberCode,
//                majorCode,
//                userId,
//                null
//        );
//    }
}
