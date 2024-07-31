package ussum.homepage.infra.jpa.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MAJORCODE;

@RequiredArgsConstructor
@Getter
public enum MajorCode {
    // 경영대학
    BUSINESS_SCHOOL("경영대학"),
    BUSINESS("경영학부"),
    ENTREPRENEURSHIP("벤처중소기업학과"),
    ACCOUNTING("회계학과"),
    FINANCE("금융학부"),
    ENTREPRENEURIAL_MANAGEMENT("벤처경영학과"),
    INNOVATION_MANAGEMENT("혁신경영학과"),
    WELFARE_MANAGEMENT("복지경영학과"),
    ACCOUNTING_AND_TAX("회계세무학과"),

    // 경제통상대학
    ECONOMICS_SCHOOL("경제통상대학"),
    ECONOMICS("경제학과"),
    GLOBAL_COMMERCE("글로벌통상학과"),
    FINANCIAL_ECONOMICS("금융경제학과"),
    INTERNATIONAL_TRADE("국제무역학과"),
    INDUSTRIAL_TRADE("통상산업학과"),

    // 공과대학
    ENGINEERING_SCHOOL("공과대학"),
    CHEMICAL_ENGINEERING("화학공학과"),
    NEW_MATERIALS("신소재공학과"),
    ELECTRICAL_ENGINEERING("전기공학부"),
    MECHANICAL_ENGINEERING("기계공학부"),
    INDUSTRIAL_INFORMATION("산업정보시스템공학과"),
    ARCHITECTURE("건축학부"),

    // 법과대학
    LAW_SCHOOL("법과대학"),
    LAW("법학과"),
    INTERNATIONAL_LAW("국제법무학과"),

    // 사회과학대학
    SOCIAL_SCIENCE_SCHOOL("사회과학대학"),
    SOCIAL_WELFARE("사회복지학부"),
    PUBLIC_ADMINISTRATION("행정학부"),
    POLITICAL_SCIENCE("정치외교학과"),
    INFORMATION_SOCIAL_SCIENCE("정보사회학과"),
    MEDIA_COMMUNICATION("언론홍보학과"),
    LIFELONG_EDUCATION("평생교육학과"),

    // 인문대학
    HUMANITIES_SCHOOL("인문대학"),
    CHRISTIANITY("기독교학과"),
    KOREAN_LANGUAGE("국어국문학과"),
    ENGLISH_LANGUAGE("영어영문학과"),
    GERMAN_LANGUAGE("독어독문학과"),
    FRENCH_LANGUAGE("불어불문학과"),
    CHINESE_LANGUAGE("중어중문학과"),
    JAPANESE_LANGUAGE("일어일문학과"),
    PHILOSOPHY("철학과"),
    HISTORY("사학과"),
    CREATIVE_WRITING("문예창작전공"),
    FILM_ART("영화예술전공"),
    SPORTS_SCIENCE("스포츠학부"),

    // 자연과학대학
    NATURAL_SCIENCE_SCHOOL("자연과학대학"),
    MATHEMATICS("수학과"),
    PHYSICS("물리학과"),
    CHEMISTRY("화학과"),
    INFORMATION_STATISTICS("정보통계보험수리학과"),
    LIFE_SYSTEMS("의생명시스템학부"),

    // IT대학
    IT_SCHOOL("IT대학"),
    COMPUTER_SCIENCE("컴퓨터학부"),
    ELECTRONIC_INFORMATION("전자정보학부"),
    GLOBAL_MEDIA("글로벌미디어학부"),
    SOFTWARE("소프트웨어학부"),
    AI("AI융합학부"),
    MEDIA_ENGINEERING("미디어경영학과"),
    INFORMATION_SECURITY("정보보호학과"),

    // 융합특성화자유전공학부
    CONVERGENCE("융합특성화자유전공학부");

    private final String stringMajorCode;
    public static MajorCode getEnumMajorCodeFromStringMajorCode(String stringMajorCode) {
        return Arrays.stream(values())
                .filter(majorCode -> majorCode.stringMajorCode.equals(stringMajorCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MAJORCODE));
    }
}
