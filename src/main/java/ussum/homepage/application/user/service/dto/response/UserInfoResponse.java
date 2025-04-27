package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.user.User;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MAJORCODE;

@Builder
public record UserInfoResponse(
        String name,
        String studentId,
        String major,
        boolean isCouncil
) {
    public static UserInfoResponse of(StudentCsv studentCsv, Member member) {
        return UserInfoResponse.builder()
                .name(studentCsv.getStudentName())
                .studentId(String.valueOf(studentCsv.getStudentId()))
                .major(studentCsv.getMajor())
                .isCouncil(member.getIsAdmin())
                .build();
    }

    public static UserInfoResponse of(User user, Member member) {
        //재학생이 아닌경우 member에서 찾기
        String target = "null";
        // 자치기구 계정의 경우 MajorCode가 null이라 MemberCode로 내려가게 함
        try {
            target = MajorCode.getEnumMajorCodeFromStringMajorCode(member.getMajorCode()).getStringMajorCode();    
        } catch (Exception e) {
            if (e instanceof InvalidValueException && ((InvalidValueException) e).getBaseErrorCode() == INVALID_MAJORCODE) {
                target = MemberCode.getEnumMemberCodeFromStringMemberCode(member.getMemberCode()).getStringMemberCode();
            }
        }
        
        return UserInfoResponse.builder()
                .name(user.getName())
                .studentId(user.getStudentId() == null ? "null" : String.valueOf(user.getStudentId()))
                .major(target)
                .isCouncil(member.getIsAdmin())
                .build();
    }
}
