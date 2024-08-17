package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CATEGORY_CODE;

@RequiredArgsConstructor
@Getter
public enum CategoryCode {
    //분실물 카테고리(분실문 현황, 분실 신고)
    LOST_STATUS("LOST_STATUS"),
    LOST_REPORT("LOST_REPORT"),

    //제휴안내 카테고리(의료, 문화, 뷰티, 건강, 음식, 교육, 주거)
    MEDICAL("MEDICAL"),
    CULTURE("CULTURE"),
    BEAUTY("BEAUTY"),
    HEALTH("HEALTH"),
    FOOD("FOOD"),
    EDUCATION("EDUCATION"),
    HOUSING("HOUSING"),

    //청원 카테고리(진행중, 접수완료, 답변완료 ,종료됨)
    IN_PROGRESS("IN_PROGRESS"),
    RECEIVED("RECEIVED"),
    ANSWERED("ANSWERED"),
    COMPLETED("COMPLETED"),

    //공지사항 카테고리
    //중앙 : 총학생회, 중앙운영위원회, 중앙선거관리위원회, 동아리연합회
    //단과대 : IT대학, 인문대학, 융합특성화자유전공학부, 사회과학대학, 공과대학, 경영대학, 경제통상대학, 자연과학대학, 법과대학
    STUDENT_COUNCIL("STUDENT_COUNCIL"),
    CENTRAL_OPERATION_COMMITTEE("CENTRAL_OPERATION_COMMITTEE"),
    CENTRAL_ELECTION_COMMITTEE("CENTRAL_ELECTION_COMMITTEE"),
    CLUB_UNION("CLUB_UNION"),

    IT_SCHOOL("IT_SCHOOL"),
    HUMANITIES_SCHOOL("HUMANITIES_SCHOOL"),
    CONVERGENCE_SCHOOL("CONVERGENCE_SCHOOL"),
    SOCIAL_SCIENCES_SCHOOL("SOCIAL_SCIENCES_SCHOOL"),
    ENGINEERING_SCHOOL("ENGINEERING_SCHOOL"),
    BUSINESS_SCHOOL("BUSINESS_SCHOOL"),
    ECONOMICS_TRADE_SCHOOL("ECONOMICS_TRADE_SCHOOL"),
    NATURAL_SCIENCES_SCHOOL("NATURAL_SCIENCES_SCHOOL"),
    LAW_SCHOOL("LAW_SCHOOL"),

    //감사기구 카테고리(감사계획, 감사결과, 기타)
    AUDIT_PLAN("AUDIT_PLAN"),
    AUDIT_RESULT("AUDIT_RESULT"),
    OTHERS("OTHERS");


    private final String stringCategoryCode;
    public static CategoryCode getEnumCategoryCodeFromStringCategoryCode(String stringCategoryCode) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.stringCategoryCode.equals(stringCategoryCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_CATEGORY_CODE));
    }
}
