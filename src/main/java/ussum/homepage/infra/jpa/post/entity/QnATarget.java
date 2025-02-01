package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_TARGET;

@RequiredArgsConstructor
@Getter
public enum QnATarget {

    ALL("ALL"),
    COLLEGE("COLLEGE"),
    DEPARTMENT("DEPARTMENT");

    private final String target;

    public static QnATarget fromString(String target) {
        return Arrays.stream(values())
                .filter(qnaTarget -> qnaTarget.target.equals(target))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TARGET));
    }
    public static QnATarget fromStringOrNull(String target) {
        return Optional.ofNullable(target)
                .map(QnATarget::fromString)
                .orElse(null);
    }

    public static String getTargetStringFromQnATarget(QnATarget qnaTarget) {
        return qnaTarget.target;
    }
}
