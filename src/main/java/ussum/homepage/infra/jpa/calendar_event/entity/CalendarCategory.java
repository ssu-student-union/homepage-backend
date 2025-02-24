package ussum.homepage.infra.jpa.calendar_event.entity;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CALENDAR_CATEGORY_CODE;
import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CATEGORY_CODE;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.springframework.util.StringUtils;
import ussum.homepage.global.error.exception.InvalidValueException;

@Getter
public enum CalendarCategory {
    //캘린더 카테고리
    COLLEGE("학사"),
    STUDENT_UNION("총학생회"),
    HOLIDAY("공휴일/기념일");

    private final String categoryName;

    CalendarCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public static CalendarCategory getEnumCategoryCodeFromStringCategoryCode(String category) {
        return Arrays.stream(values())
                .filter(value -> value.categoryName.equals(category))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_CALENDAR_CATEGORY_CODE));
    }

    public static Optional<CalendarCategory> fromString(String name) {
        if (!StringUtils.hasText(name)) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(calendarCategory -> calendarCategory.categoryName.equals(name))
                .findFirst();
    }

    public static CalendarCategory fromStringOrNull(String name) {
        return fromString(name).orElse(null);
    }

    public static String fromEnumOrNull(CalendarCategory calendarCategory) {
        return Optional.ofNullable(calendarCategory)
                .map(CalendarCategory::getCategoryName)
                .orElse(null);
    }
}
