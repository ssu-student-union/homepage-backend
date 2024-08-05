package ussum.homepage.infra.jpa.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MAJORCODE;

@RequiredArgsConstructor
@Getter
public enum GroupCode {
    //관리자
    ADMIN("ADMIN"),

    //총학생회
    STUDENT_UNION("STUDENT_UNION"),

    //중앙기구
    CENTRAL_ORGANIZATION("CENTRAL_ORGANIZATION"),

    //선거관리위원회
    VOTE_COMMITTEE("VOTE_COMMITTEE"),

    //단과대학생회
    COLLEGE_UNION("COLLEGE_UNION"),

    //학과부학생회
    DEPARTMENT_UNION("DEPARTMENT_UNION"),

    //특별기구
    SPECIAL_ORGANIZATION("SPECIAL_ORGANIZATION"),

    //인권신고접수
    STUDENT_RIGHTS_REPORT_SUBMISSION("STUDENT_RIGHTS_REPORT_SUBMISSION"),

    //학생서비스팀
    STUDENT_SERVICE_TEAM("STUDENT_SERVICE_TEAM"),

    //학생자치기구
    STUDENT_GOVERNMENT_ORGANIZATION("STUDENT_GOVERNMENT_ORGANIZATION");

    private final String stringGroupCode;

    public static GroupCode getEnumGroupCodeFromStringGroupCode(String stringGroupCode) {
        return Arrays.stream(values())
                .filter(groupCode -> groupCode.stringGroupCode.equals(stringGroupCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MAJORCODE));
    }
}
