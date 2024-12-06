package ussum.homepage.infra.jpa.acl.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.status.ErrorStatus;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum TargetGroup {
    STUDENT_UNION("총학생회"),
    STUDENT_GOVERNMENT_ORGANIZATION("학생자치기구"),
    SPECIAL_ORGANIZATION("특별기구"),
    STUDENT_SERVICE_TEAM("학생서비스팀"),
    DEPARTMENT_UNION("학과부학생회"),
    COLLEGE_UNION("단과대학생회"),
    CENTRAL_ORGANIZATION("중앙기구"),
    AUDIT_COMMITTEE("감사위원회"),
    CLUB_UNION("동아리연합회"),
    STUDENT_RIGHTS_REPORT_SUBMISSION("인권신고접수");

    private final String stringTargetGroup;

    public static TargetGroup getEnumTargetFromStringTargetGroup(String stringTargetGroup) {
        return Arrays.stream(values())
                .filter(target -> target.stringTargetGroup.equals(stringTargetGroup))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorStatus.INVALID_TARGET));
    }

    public static String fromEnumOrNull(TargetGroup targetGroup) {
        return Optional.ofNullable(targetGroup)
                .map(TargetGroup::getStringTargetGroup)
                .orElse(null);
    }
}
