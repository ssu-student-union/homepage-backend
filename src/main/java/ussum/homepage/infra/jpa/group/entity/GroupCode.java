package ussum.homepage.infra.jpa.group.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_GROUP_CODE;
import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MAJORCODE;

@RequiredArgsConstructor
@Getter
public enum GroupCode {
    //관리자
    ADMIN("관리자"),

    //총학생회
    STUDENT_UNION("총학생회"),

    //동아리연합회
    CLUB_UNION("동아리연합회"),

    //중앙기구
    CENTRAL_ORGANIZATION("중앙기구"),

    //선거관리위원회
    VOTE_COMMITTEE("선거관리위원회"),

    //감사위원회
    AUDIT_COMMITTEE("감사위원회"),

    //단과대학생회
    COLLEGE_UNION("단과대학생회"),

    //학과부학생회
    DEPARTMENT_UNION("학과부학생회"),

    //특별기구
    SPECIAL_ORGANIZATION("특별기구"),

    //인권신고접수
    STUDENT_RIGHTS_REPORT_SUBMISSION("인권신고접수"),

    //학생서비스팀
    STUDENT_SERVICE_TEAM("학생서비스팀"),

    //학생자치기구
    STUDENT_GOVERNMENT_ORGANIZATION("학생자치기구"),

    //경영대학
    BUSINESS_SCHOOL("경영대학"),

    //경제통상대학
    ECONOMICS_TRADE_SCHOOL("경제통상대학"),

    //공과대학
    ENGINEERING_SCHOOL("공과대학"),

    //법과대학
    LAW_SCHOOL("법과대학"),

    //사회과학대학
    SOCIAL_SCIENCES_SCHOOL("사회과학대학"),

    //인문대학
    HUMANITIES_SCHOOL("인문대학"),

    //자연과학대학
    NATURAL_SCIENCES_SCHOOL("자연과학대학"),

    //IT대학
    IT_SCHOOL("IT대학"),

    //융합특성화자유전공학부
    CONVERGENCE_DEPARTMENT("융합특성화자유전공학부");

    private final String stringGroupCode;

    public static GroupCode getEnumGroupCodeFromStringGroupCode(String stringGroupCode) {
        return Arrays.stream(values())
                .filter(groupCode -> groupCode.stringGroupCode.equals(stringGroupCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_GROUP_CODE));
    }

    public static Optional<GroupCode> fromString(String stringGroupCode) {
        if (!StringUtils.hasText(stringGroupCode)) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(groupCode -> groupCode.stringGroupCode.equals(stringGroupCode))
                .findFirst();
    }

    public static GroupCode fromStringOrNull(String stringGroupCode) {
        return fromString(stringGroupCode).orElse(null);
    }

    public static String fromEnumOrNull(GroupCode groupCode) {
        return Optional.ofNullable(groupCode)
                .map(GroupCode::getStringGroupCode)
                .orElse(null);
    }

    public static List<String> getCollegesByList() {
        return Stream.of(
                        GroupCode.BUSINESS_SCHOOL,          //경영대학
                        GroupCode.ECONOMICS_TRADE_SCHOOL,   //경제통상대학
                        GroupCode.ENGINEERING_SCHOOL,       //공과대학
                        GroupCode.LAW_SCHOOL,               //법과대학
                        GroupCode.SOCIAL_SCIENCES_SCHOOL,   //사회과학대학
                        GroupCode.HUMANITIES_SCHOOL,        //인문대학
                        GroupCode.NATURAL_SCIENCES_SCHOOL,  //자연과학대학
                        GroupCode.IT_SCHOOL,                //IT대학
                        GroupCode.CONVERGENCE_DEPARTMENT    //융합특성화자유전공학부
                )
                .map(GroupCode::getStringGroupCode)
                .toList();
    }
}
