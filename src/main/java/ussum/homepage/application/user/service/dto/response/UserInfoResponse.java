package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.member.entity.MemberCode;

@Builder
public record UserInfoResponse(
        String name,
        String studentId,
        String major,
        boolean isCouncil
) {
    public static UserInfoResponse of(StudentCsv studentCsv, Member member){
        return UserInfoResponse.builder()
                .name(studentCsv.getStudentName())
                .studentId(String.valueOf(studentCsv.getStudentId()))
                .major(studentCsv.getMajor())
                .isCouncil(member.getIsAdmin())
                .build();
    }

    public static UserInfoResponse of(User user, Member member){
        return UserInfoResponse.builder()
                .name(user.getName())
                .studentId("null")
                .major(MemberCode.getEnumMemberCodeFromStringMemberCode(member.getMemberCode()).getStringMemberCode())
                .isCouncil(member.getIsAdmin())
                .build();
    }
}
