package ussum.homepage.infra.jpa.group.entity;

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

    //감사위원회
    AUDIT_COMMITTEE("AUDIT_COMMITTEE"),

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
    STUDENT_GOVERNMENT_ORGANIZATION("STUDENT_GOVERNMENT_ORGANIZATION"),

    //경영대학
    BUSINESS_SCHOOL("BUSINESS_SCHOOL"),

    //경제통상대학
    ECONOMICS_TRADE_SCHOOL("ECONOMICS_TRADE_SCHOOL"),

    //공과대학
    ENGINEERING_SCHOOL("ENGINEERING_SCHOOL"),

    //법과대학
    LAW_SCHOOL("LAW_SCHOOL"),

    //사회과학대학
    SOCIAL_SCIENCES_SCHOOL("SOCIAL_SCIENCES_SCHOOL"),

    //인문대학
    HUMANITIES_SCHOOL("HUMANITIES_SCHOOL"),

    //자연과학대학
    NATURAL_SCIENCES_SCHOOL("NATURAL_SCIENCES_SCHOOL"),

    //IT대학
    IT_SCHOOL("IT_SCHOOL"),

    //융합특성화자유전공학부
    CONVERGENCE_DEPARTMENT("INTERDISCIPLINARY_STUDIES_DEPARTMENT"),

    //동아리연합회
    CLUB_UNION("CLUB_UNION");

    private final String stringGroupCode;

    public static GroupCode getEnumGroupCodeFromStringGroupCode(String stringGroupCode) {
        return Arrays.stream(values())
                .filter(groupCode -> groupCode.stringGroupCode.equals(stringGroupCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MAJORCODE));
    }
}
