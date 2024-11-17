package ussum.homepage.infra.jpa.post.entity;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_MEMBERCODE;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.util.StringUtils;
import ussum.homepage.global.error.exception.InvalidValueException;

@RequiredArgsConstructor
@Getter
public enum SuggestionTarget {

    STUDENT_UNION("STUDENT_UNION"),

    GOVERNMENT_ORGANIZATION("GOVERNMENT_ORGANIZATION");

    private final String target;

    public static SuggestionTarget fromString(String target) {
        return Arrays.stream(values())
                .filter(suggestionTarget -> suggestionTarget.target.equals(target))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_MEMBERCODE));
    }

    public static String getTargetStringFromSuggestionTarget(SuggestionTarget suggestionTarget) {
        return suggestionTarget.target;
    }
}
