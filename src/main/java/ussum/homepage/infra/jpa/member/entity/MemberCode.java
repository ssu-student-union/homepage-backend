package ussum.homepage.infra.jpa.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.infra.jpa.group.entity.GroupCode;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MEMBERCODE;

@RequiredArgsConstructor
@Getter
public enum MemberCode {
    // 총학생회
    STUDENT_UNION("총학생회"),

    // 중앙운영위원회
    CENTRAL_OPERATION_COMMITTEE("중앙운영위원회"),

    // 동아리연합회
    CLUB_UNION("동아리연합회"),

    // 중앙선거관리위원회
    CENTRAL_ELECTION_COMMITTEE("중앙선거관리위원회"),

    // 경영대학선거관리위원회
    BUSINESS_SCHOOL_ELECTION_COMMITTEE("경영대학선거관리위원회"),

    // 경제통상대학선거관리위원회
    ECONOMICS_TRADE_SCHOOL_ELECTION_COMMITTEE("경제통상대학선거관리위원회"),

    // 공과대학선거관리위원회
    ENGINEERING_SCHOOL_ELECTION_COMMITTEE("공과대학선거관리위원회"),

    // 법과대학선거관리위원회
    LAW_SCHOOL_ELECTION_COMMITTEE("법과대학선거관리위원회"),

    // 사회과학대학선거관리위원회
    SOCIAL_SCIENCES_SCHOOL_ELECTION_COMMITTEE("사회과학대학선거관리위원회"),

    // 인문대학선거관리위원회
    HUMANITIES_SCHOOL_ELECTION_COMMITTEE("인문대학선거관리위원회"),

    // 자연과학대학선거관리위원회
    NATURAL_SCIENCES_SCHOOL_ELECTION_COMMITTEE("자연과학대학선거관리위원회"),

    // IT대학선거관리위원회
    IT_SCHOOL_ELECTION_COMMITTEE("IT대학선거관리위원회"),

    // 동아리연합회선거관리위원회
    CLUB_UNION_ELECTION_COMMITTEE("동아리연합회선거관리위원회"),

    // 중앙감사위원회
    CENTRAL_AUDIT_COMMITTEE("중앙감사위원회"),

    // 경영대학감사위원회
    BUSINESS_SCHOOL_AUDIT_COMMITTEE("경영대학감사위원회"),

    // 경제통상대학감사위원회
    ECONOMICS_TRADE_SCHOOL_AUDIT_COMMITTEE("경제통상대학감사위원회"),

    // 공과대학감사위원회
    ENGINEERING_SCHOOL_AUDIT_COMMITTEE("공과대학감사위원회"),

    // 법과대학감사위원회
    LAW_SCHOOL_AUDIT_COMMITTEE("법과대학감사위원회"),

    // 사회과학대학감사위원회
    SOCIAL_SCIENCES_SCHOOL_AUDIT_COMMITTEE("사회과학대학감사위원회"),

    // 인문대학감사위원회
    HUMANITIES_SCHOOL_AUDIT_COMMITTEE("인문대학감사위원회"),

    // 자연과학대학감사위원회
    NATURAL_SCIENCES_SCHOOL_AUDIT_COMMITTEE("자연과학대학감사위원회"),

    // IT대학감사위원회
    IT_SCHOOL_AUDIT_COMMITTEE("IT대학감사위원회"),

    // 경영대학
    BUSINESS_SCHOOL("경영대학"),

    // 경제통상대학
    ECONOMICS_TRADE_SCHOOL("경제통상대학"),

    // 공과대학
    ENGINEERING_SCHOOL("공과대학"),

    // 법과대학
    LAW_SCHOOL("법과대학"),

    // 사회과학대학
    SOCIAL_SCIENCES_SCHOOL("사회과학대학"),

    // 인문대학
    HUMANITIES_SCHOOL("인문대학"),

    // 자연과학대학
    NATURAL_SCIENCES_SCHOOL("자연과학대학"),

    // IT대학
    IT_SCHOOL("IT대학"),

    // 융합특성화자유전공학부
    CONVERGENCE_DEPARTMENT("자유전공학부"),

    // 학생인권위원회
    STUDENT_HUMAN_RIGHTS_COMMITTEE("학생인권위원회"),

    // 차세대반도체학과
    NEXT_GENERATION_SEMICONDUCTOR_DEPARTMENT("차세대반도체학과"),

    // 베어드학부대학
    BAIRD_SCHOOL("베어드학부대학"),

    // 국제대학
    INTERNATIONAL_SCHOOL("국제대학"),

    // 교지편집위원회
    SCHOOL_MAGAZINE_EDITORIAL_COMMITTEE("교지편집위원회"),

    // 학생복지위원회
    STUDENT_WELFARE_COMMITTEE("학생복지위원회"),

    // IT지원위원회
    IT_SUPPORT_COMMITTEE("IT지원위원회"),

    // 학생서비스팀
    STUDENT_SERVICES_TEAM("학생서비스팀");

    private final String stringMemberCode;
    public static MemberCode getEnumMemberCodeFromStringMemberCode(String stringMemberCode) {
        return Arrays.stream(values())
                .filter(memberCode -> memberCode.stringMemberCode.equals(stringMemberCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MEMBERCODE));
    }

    public static Optional<MemberCode> fromString(String stringMemberCode) {
        if (!StringUtils.hasText(stringMemberCode)) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(memberCode -> memberCode.stringMemberCode.equals(stringMemberCode))
                .findFirst();
    }

    public static MemberCode fromStringOrNull(String stringMemberCode) {
        return fromString(stringMemberCode).orElse(null);
    }

    public static String fromEnumOrNull(MemberCode memberCode) {
        return Optional.ofNullable(memberCode)
                .map(MemberCode::getStringMemberCode)
                .orElse(null);
    }
}
