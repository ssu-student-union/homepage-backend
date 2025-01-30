package ussum.homepage.infra.jpa.calendar_event.entity;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CATEGORY_CODE;
import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ONGOING_STATUS;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.springframework.util.StringUtils;
import ussum.homepage.global.error.exception.InvalidValueException;

@Getter
public enum Category {
    //캘린더 카테고리
    COLLEGE("학사"),
    STUDENT_UNION("총학생회"),
    HOLIDAY("공휴일/기념일");

    private final String categoryName;

    Category(String name) {
        this.categoryName = name;
    }

    public static Category getEnumCategoryCodeFromStringCategoryCode(String name) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.categoryName.equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_CATEGORY_CODE));
    }

    public static Optional<Category> fromString(String name) {
        if (!StringUtils.hasText(name)) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(category -> category.categoryName.equals(name))
                .findFirst();
    }

    public static Category fromStringOrNull(String name) {
        return fromString(name).orElse(null);
    }

    public static String fromEnumOrNull(String name) {
        return Optional.ofNullable(name)
                .orElseThrow(() -> new InvalidValueException(INVALID_ONGOING_STATUS));
    }

    public static String fromEnumOrNull(Category category) {
        return Optional.ofNullable(category)
                .map(Category::getCategoryName)
                .orElse(null);
    }
}
