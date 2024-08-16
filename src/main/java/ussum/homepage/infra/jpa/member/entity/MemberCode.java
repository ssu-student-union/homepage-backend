package ussum.homepage.infra.jpa.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MEMBERCODE;

@RequiredArgsConstructor
@Getter
public enum MemberCode {
    // 총학생회
    STUDENT_COUNCIL("STUDENT_COUNCIL"),

    // 중앙운영위원회
    CENTRAL_OPERATION_COMMITTEE("CENTRAL_OPERATION_COMMITTEE"),

    // 동아리연합회
    CLUB_UNION("CLUB_UNION"),

    // 중앙선거관리위원회
    CENTRAL_ELECTION_COMMITTEE("CENTRAL_ELECTION_COMMITTEE"),

    // 경영대학선거관리위원회
    BUSINESS_SCHOOL_ELECTION_COMMITTEE("BUSINESS_SCHOOL_ELECTION_COMMITTEE"),

    // 경제통상대학선거관리위원회
    ECONOMICS_TRADE_SCHOOL_ELECTION_COMMITTEE("ECONOMICS_TRADE_SCHOOL_ELECTION_COMMITTEE"),

    // 공과대학선거관리위원회
    ENGINEERING_SCHOOL_ELECTION_COMMITTEE("ENGINEERING_SCHOOL_ELECTION_COMMITTEE"),

    // 법과대학선거관리위원회
    LAW_SCHOOL_ELECTION_COMMITTEE("LAW_SCHOOL_ELECTION_COMMITTEE"),

    // 사회과학대학선거관리위원회
    SOCIAL_SCIENCES_SCHOOL_ELECTION_COMMITTEE("SOCIAL_SCIENCES_SCHOOL_ELECTION_COMMITTEE"),

    // 인문대학선거관리위원회
    HUMANITIES_SCHOOL_ELECTION_COMMITTEE("HUMANITIES_SCHOOL_ELECTION_COMMITTEE"),

    // 자연과학대학선거관리위원회
    NATURAL_SCIENCES_SCHOOL_ELECTION_COMMITTEE("NATURAL_SCIENCES_SCHOOL_ELECTION_COMMITTEE"),

    // IT대학선거관리위원회
    IT_SCHOOL_ELECTION_COMMITTEE("IT_SCHOOL_ELECTION_COMMITTEE"),

    // 동아리연합회선거관리위원회
    CLUB_UNION_ELECTION_COMMITTEE("CLUB_UNION_ELECTION_COMMITTEE"),

    // 중앙감사위원회
    CENTRAL_AUDIT_COMMITTEE("CENTRAL_AUDIT_COMMITTEE"),

    // 경영대학감사위원회
    BUSINESS_SCHOOL_AUDIT_COMMITTEE("BUSINESS_SCHOOL_AUDIT_COMMITTEE"),

    // 경제통상대학감사위원회
    ECONOMICS_TRADE_SCHOOL_AUDIT_COMMITTEE("ECONOMICS_TRADE_SCHOOL_AUDIT_COMMITTEE"),

    // 공과대학감사위원회
    ENGINEERING_SCHOOL_AUDIT_COMMITTEE("ENGINEERING_SCHOOL_AUDIT_COMMITTEE"),

    // 법과대학감사위원회
    LAW_SCHOOL_AUDIT_COMMITTEE("LAW_SCHOOL_AUDIT_COMMITTEE"),

    // 사회과학대학감사위원회
    SOCIAL_SCIENCES_SCHOOL_AUDIT_COMMITTEE("SOCIAL_SCIENCES_SCHOOL_AUDIT_COMMITTEE"),

    // 인문대학감사위원회
    HUMANITIES_SCHOOL_AUDIT_COMMITTEE("HUMANITIES_SCHOOL_AUDIT_COMMITTEE"),

    // 자연과학대학감사위원회
    NATURAL_SCIENCES_SCHOOL_AUDIT_COMMITTEE("NATURAL_SCIENCES_SCHOOL_AUDIT_COMMITTEE"),

    // IT대학감사위원회
    IT_SCHOOL_AUDIT_COMMITTEE("IT_SCHOOL_AUDIT_COMMITTEE"),

    // 경영대학
    BUSINESS_SCHOOL("BUSINESS_SCHOOL"),

    // 경영학부
    BUSINESS_ADMINISTRATION_DEPARTMENT("BUSINESS_ADMINISTRATION_DEPARTMENT"),

    // 벤처중소기업학과
    ENTREPRENEURSHIP_DEPARTMENT("ENTREPRENEURSHIP_DEPARTMENT"),

    // 회계학과
    ACCOUNTING_DEPARTMENT("ACCOUNTING_DEPARTMENT"),

    // 금융학부
    FINANCE_DEPARTMENT("FINANCE_DEPARTMENT"),

    // 경제통상대학
    ECONOMICS_TRADE_SCHOOL("ECONOMICS_TRADE_SCHOOL"),

    // 경제학과
    ECONOMICS_DEPARTMENT("ECONOMICS_DEPARTMENT"),

    // 글로벌통상학과
    GLOBAL_TRADE_DEPARTMENT("GLOBAL_TRADE_DEPARTMENT"),

    // 금융경제학과
    FINANCIAL_ECONOMICS_DEPARTMENT("FINANCIAL_ECONOMICS_DEPARTMENT"),

    // 국제무역학과
    INTERNATIONAL_TRADE_DEPARTMENT("INTERNATIONAL_TRADE_DEPARTMENT"),

    // 공과대학
    ENGINEERING_SCHOOL("ENGINEERING_SCHOOL"),

    // 화학공학과
    CHEMICAL_ENGINEERING_DEPARTMENT("CHEMICAL_ENGINEERING_DEPARTMENT"),

    // 신소재공학과
    MATERIALS_ENGINEERING_DEPARTMENT("MATERIALS_ENGINEERING_DEPARTMENT"),

    // 전기공학부
    ELECTRICAL_ENGINEERING_DEPARTMENT("ELECTRICAL_ENGINEERING_DEPARTMENT"),

    // 기계공학부
    MECHANICAL_ENGINEERING_DEPARTMENT("MECHANICAL_ENGINEERING_DEPARTMENT"),

    // 산업정보시스템공학과
    INDUSTRIAL_SYSTEMS_ENGINEERING_DEPARTMENT("INDUSTRIAL_SYSTEMS_ENGINEERING_DEPARTMENT"),

    // 건축학부
    ARCHITECTURE_DEPARTMENT("ARCHITECTURE_DEPARTMENT"),

    // 법과대학
    LAW_SCHOOL("LAW_SCHOOL"),

    // 법학과
    LAW_DEPARTMENT("LAW_DEPARTMENT"),

    // 국제법무학과
    INTERNATIONAL_LAW_DEPARTMENT("INTERNATIONAL_LAW_DEPARTMENT"),

    // 사회과학대학
    SOCIAL_SCIENCES_SCHOOL("SOCIAL_SCIENCES_SCHOOL"),

    // 사회복지학부
    SOCIAL_WELFARE_DEPARTMENT("SOCIAL_WELFARE_DEPARTMENT"),

    // 행정학부
    PUBLIC_ADMINISTRATION_DEPARTMENT("PUBLIC_ADMINISTRATION_DEPARTMENT"),

    // 정치외교학과
    POLITICAL_SCIENCE_DEPARTMENT("POLITICAL_SCIENCE_DEPARTMENT"),

    // 정보사회학과
    INFORMATION_SOCIETY_DEPARTMENT("INFORMATION_SOCIETY_DEPARTMENT"),

    // 언론홍보학과
    MEDIA_COMMUNICATIONS_DEPARTMENT("MEDIA_COMMUNICATIONS_DEPARTMENT"),

    // 평생교육학과
    LIFELONG_EDUCATION_DEPARTMENT("LIFELONG_EDUCATION_DEPARTMENT"),

    // 인문대학
    HUMANITIES_SCHOOL("HUMANITIES_SCHOOL"),

    // 기독교학과
    CHRISTIANITY_DEPARTMENT("CHRISTIANITY_DEPARTMENT"),

    // 국어국문학과
    KOREAN_LANGUAGE_DEPARTMENT("KOREAN_LANGUAGE_DEPARTMENT"),

    // 영어영문학과
    ENGLISH_DEPARTMENT("ENGLISH_DEPARTMENT"),

    // 독어독문학과
    GERMAN_LANGUAGE_DEPARTMENT("GERMAN_LANGUAGE_DEPARTMENT"),

    // 불어불문학과
    FRENCH_LANGUAGE_DEPARTMENT("FRENCH_LANGUAGE_DEPARTMENT"),

    // 중어중문학과
    CHINESE_LANGUAGE_DEPARTMENT("CHINESE_LANGUAGE_DEPARTMENT"),

    // 일어일문학과
    JAPANESE_LANGUAGE_DEPARTMENT("JAPANESE_LANGUAGE_DEPARTMENT"),

    // 철학과
    PHILOSOPHY_DEPARTMENT("PHILOSOPHY_DEPARTMENT"),

    // 사학과
    HISTORY_DEPARTMENT("HISTORY_DEPARTMENT"),

    // 문예창작전공
    CREATIVE_WRITING_MAJOR("CREATIVE_WRITING_MAJOR"),

    // 영화예술전공
    FILM_ARTS_MAJOR("FILM_ARTS_MAJOR"),

    // 스포츠학부
    SPORTS_SCIENCE_DEPARTMENT("SPORTS_SCIENCE_DEPARTMENT"),

    // 자연과학대학
    NATURAL_SCIENCES_SCHOOL("NATURAL_SCIENCES_SCHOOL"),

    // 수학과
    MATHEMATICS_DEPARTMENT("MATHEMATICS_DEPARTMENT"),

    // 물리학과
    PHYSICS_DEPARTMENT("PHYSICS_DEPARTMENT"),

    // 화학과
    CHEMISTRY_DEPARTMENT("CHEMISTRY_DEPARTMENT"),

    // 정보통계보험수리학과
    STATISTICS_ACTUARIAL_SCIENCE_DEPARTMENT("STATISTICS_ACTUARIAL_SCIENCE_DEPARTMENT"),

    // 의생명시스템학부
    BIOMEDICAL_SCIENCES_DEPARTMENT("BIOMEDICAL_SCIENCES_DEPARTMENT"),

    // IT대학
    IT_SCHOOL("IT_SCHOOL"),

    // 컴퓨터학부
    COMPUTER_SCIENCE_DEPARTMENT("COMPUTER_SCIENCE_DEPARTMENT"),

    // 전자정보공학부
    ELECTRONICS_INFORMATION_ENGINEERING_DEPARTMENT("ELECTRONICS_INFORMATION_ENGINEERING_DEPARTMENT"),

    // 글로벌미디어학부
    GLOBAL_MEDIA_DEPARTMENT("GLOBAL_MEDIA_DEPARTMENT"),

    // 소프트웨어학부
    SOFTWARE_ENGINEERING_DEPARTMENT("SOFTWARE_ENGINEERING_DEPARTMENT"),

    // AI융합학부
    AI_CONVERGENCE_DEPARTMENT("AI_CONVERGENCE_DEPARTMENT"),

    // 미디어경영학과
    MEDIA_MANAGEMENT_DEPARTMENT("MEDIA_MANAGEMENT_DEPARTMENT"),

    // 융합특성화자유전공학부
    INTERDISCIPLINARY_STUDIES_DEPARTMENT("INTERDISCIPLINARY_STUDIES_DEPARTMENT"),

    // 학생인권위원회
    STUDENT_HUMAN_RIGHTS_COMMITTEE("STUDENT_HUMAN_RIGHTS_COMMITTEE"),

    // 교지편집위원회
    SCHOOL_MAGAZINE_EDITORIAL_COMMITTEE("SCHOOL_MAGAZINE_EDITORIAL_COMMITTEE"),

    // 학생복지위원회
    STUDENT_WELFARE_COMMITTEE("STUDENT_WELFARE_COMMITTEE"),

    // IT지원위원회
    IT_SUPPORT_COMMITTEE("IT_SUPPORT_COMMITTEE"),

    // 학생서비스팀
    STUDENT_SERVICES_TEAM("STUDENT_SERVICES_TEAM");

    private final String stringMemberCode;
    public static MemberCode getEnumMemberCodeFromStringMemberCode(String stringMemberCode) {
        return Arrays.stream(values())
                .filter(memberCode -> memberCode.stringMemberCode.equals(stringMemberCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MEMBERCODE));
    }

}