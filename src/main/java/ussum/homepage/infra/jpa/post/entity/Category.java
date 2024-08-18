package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CATEGORY_CODE;

@RequiredArgsConstructor
@Getter
public enum Category {
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
    COMPLETED("COMPLETED");

    private final String stringCategoryCode;
    public static Category getEnumCategoryCodeFromStringCategoryCode(String stringCategoryCode) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.stringCategoryCode.equals(stringCategoryCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_CATEGORY_CODE));
    }
}
