package ussum.homepage.infra.jpa.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.infra.jpa.group.entity.GroupCode;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MAJORCODE;

@RequiredArgsConstructor
@Getter
public enum MajorCode {
    // 경영대학
    // 경영학부
    BUSINESS_ADMINISTRATION_DEPARTMENT("경영학부"),

    // 벤처중소기업학과
    ENTREPRENEURSHIP_DEPARTMENT("벤처중소기업학과"),

    // 회계학과
    ACCOUNTING_DEPARTMENT("회계학과"),

    // 금융학부
    FINANCE_DEPARTMENT("금융학부"),

    // 경제통상대학
    // 경제학과
    ECONOMICS_DEPARTMENT("경제학과"),

    // 글로벌통상학과
    GLOBAL_TRADE_DEPARTMENT("글로벌통상학과"),

    // 금융경제학과
    FINANCIAL_ECONOMICS_DEPARTMENT("금융경제학과"),

    // 국제무역학과
    INTERNATIONAL_TRADE_DEPARTMENT("국제무역학과"),

    // 공과대학
    // 화학공학과
    CHEMICAL_ENGINEERING_DEPARTMENT("화학공학과"),

    // 신소재공학과
    MATERIALS_ENGINEERING_DEPARTMENT("신소재공학과"),

    // 전기공학부
    ELECTRICAL_ENGINEERING_DEPARTMENT("전기공학부"),

    // 기계공학부
    MECHANICAL_ENGINEERING_DEPARTMENT("기계공학부"),

    // 산업정보시스템공학과
    INDUSTRIAL_SYSTEMS_ENGINEERING_DEPARTMENT("산업정보시스템공학과"),

    // 건축학부
    ARCHITECTURE_DEPARTMENT("건축학부"),

    // 법과대학
    // 법학과
    LAW_DEPARTMENT("법학과"),

    // 국제법무학과
    INTERNATIONAL_LAW_DEPARTMENT("국제법무학과"),

    // 사회과학대학
    // 사회복지학부
    SOCIAL_WELFARE_DEPARTMENT("사회복지학부"),

    // 행정학부
    PUBLIC_ADMINISTRATION_DEPARTMENT("행정학부"),

    // 정치외교학과
    POLITICAL_SCIENCE_DEPARTMENT("정치외교학과"),

    // 정보사회학과
    INFORMATION_SOCIETY_DEPARTMENT("정보사회학과"),

    // 언론홍보학과
    MEDIA_COMMUNICATIONS_DEPARTMENT("언론홍보학과"),

    // 평생교육학과
    LIFELONG_EDUCATION_DEPARTMENT("평생교육학과"),

    // 인문대학
    // 기독교학과
    CHRISTIANITY_DEPARTMENT("기독교학과"),

    // 국어국문학과
    KOREAN_LANGUAGE_DEPARTMENT("국어국문학과"),

    // 영어영문학과
    ENGLISH_DEPARTMENT("영어영문학과"),

    // 독어독문학과
    GERMAN_LANGUAGE_DEPARTMENT("독어독문학과"),

    // 불어불문학과
    FRENCH_LANGUAGE_DEPARTMENT("불어불문학과"),

    // 중어중문학과
    CHINESE_LANGUAGE_DEPARTMENT("중어중문학과"),

    // 일어일문학과
    JAPANESE_LANGUAGE_DEPARTMENT("일어일문학과"),

    // 철학과
    PHILOSOPHY_DEPARTMENT("철학과"),

    // 사학과
    HISTORY_DEPARTMENT("사학과"),

    // 문예창작전공
    CREATIVE_WRITING_MAJOR("문예창작전공"),

    // 영화예술전공
    FILM_ARTS_MAJOR("영화예술전공"),

    // 스포츠학부
    SPORTS_SCIENCE_DEPARTMENT("스포츠학부"),

    // 자연과학대학
    // 수학과
    MATHEMATICS_DEPARTMENT("수학과"),

    // 물리학과
    PHYSICS_DEPARTMENT("물리학과"),

    // 화학과
    CHEMISTRY_DEPARTMENT("화학과"),

    // 정보통계보험수리학과
    STATISTICS_ACTUARIAL_SCIENCE_DEPARTMENT("정보통계보험수리학과"),

    // 의생명시스템학부
    BIOMEDICAL_SCIENCES_DEPARTMENT("의생명시스템학부"),

    // IT대학
    // 컴퓨터학부
    COMPUTER_SCIENCE_DEPARTMENT("컴퓨터학부"),

    // 전자정보공학부
    ELECTRONICS_INFORMATION_ENGINEERING_DEPARTMENT("전자정보공학부"),

    // 글로벌미디어학부
    GLOBAL_MEDIA_DEPARTMENT("글로벌미디어학부"),

    // 소프트웨어학부
    SOFTWARE_ENGINEERING_DEPARTMENT("소프트웨어학부"),

    // AI융합학부
    AI_CONVERGENCE_DEPARTMENT("AI융합학부"),

    // 미디어경영학과
    MEDIA_MANAGEMENT_DEPARTMENT("미디어경영학과"),

    // 융합특성화자유전공학부
    CONVERGENCE_DEPARTMENT("융합특성화자유전공학부");

    private final String stringMajorCode;
    public static MajorCode getEnumMajorCodeFromStringMajorCode(String stringMajorCode) {
        return Arrays.stream(values())
                .filter(majorCode -> majorCode.stringMajorCode.equals(stringMajorCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MAJORCODE));
    }

    public static String fromEnumOrNull(MajorCode majorCode) {
        return Optional.ofNullable(majorCode)
                .map(MajorCode::getStringMajorCode)
                .orElse(null);
    }
}
