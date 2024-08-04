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
    LOST_STATUS("lost_status"),
    LOST_REPORT("lost_report"),

    //제휴안내 카테고리(의료, 문화, 뷰티, 건강, 음식, 교육, 주거)
    MEDICAL("medical"),
    CULTURE("culture"),
    BEAUTY("beauty"),
    HEALTH("health"),
    FOOD("food"),
    EDUCATION("education"),
    HOUSING("housing");

    private final String stringCategoryCode;
    public static CategoryCode getEnumCategoryCodeFromStringCategoryCode(String stringCategoryCode) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.stringCategoryCode.equals(stringCategoryCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_CATEGORY_CODE));
    }
}
