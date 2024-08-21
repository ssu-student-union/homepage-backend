package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CATEGORY_CODE;

@RequiredArgsConstructor
@Getter
public enum Category {
    //공지사항 카테고리
    EMERGENCY("긴급공지"),

    //분실물 카테고리(분실문 현황, 분실 신고)
    LOST_STATUS("분실문현황"),
    LOST_REPORT("분실신고"),

    //제휴안내 카테고리(의료, 문화, 뷰티, 건강, 음식, 교육, 주거)
    MEDICAL("의료"),
    CULTURE("문화"),
    BEAUTY("뷰티"),
    HEALTH("건강"),
    FOOD("음식"),
    EDUCATION("교육"),
    HOUSING("주거"),

    //청원 카테고리(진행중, 접수완료, 답변완료 ,종료됨)
    IN_PROGRESS("진행중"),
    RECEIVED("접수완료"),
    ANSWERED("답변완료"),
    COMPLETED("종료됨"),

    //감사기구 카테고리(감사계획, 감사결과, 기타)
    AUDIT_PLAN("감사계획"),
    AUDIT_RESULT("감사결과"),
    ETC("기타"),

    //공지사항 카테고리
    //중앙 : 총학생회, 중앙운영위원회, 중앙선거관리위원회, 동아리연합회
    //단과대 : IT대학, 인문대학, 융합특성화자유전공학부, 사회과학대학, 공과대학, 경영대학, 경제통상대학, 자연과학대학, 법과대학
    STUDENT_COUNCIL("총학생회"),
    CENTRAL_OPERATION_COMMITTEE("중앙운영위원회"),
    CENTRAL_ELECTION_COMMITTEE("중앙선거관리위원회"),
    CLUB_UNION("동아리연합회"),

    IT_SCHOOL("IT대학"),
    HUMANITIES_SCHOOL("인문대학"),
    CONVERGENCE_DEPARTMENT("융합특성화자유전공학부"),
    SOCIAL_SCIENCES_SCHOOL("사회과학대학"),
    ENGINEERING_SCHOOL("공과대학"),
    BUSINESS_SCHOOL("경영대학"),
    ECONOMICS_TRADE_SCHOOL("경제통상대학"),
    NATURAL_SCIENCES_SCHOOL("자연과학대학"),
    LAW_SCHOOL("법과대학");

    private final String stringCategoryCode;
    public static Category getEnumCategoryCodeFromStringCategoryCode(String stringCategoryCode) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.stringCategoryCode.equals(stringCategoryCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_CATEGORY_CODE));
    }
}
