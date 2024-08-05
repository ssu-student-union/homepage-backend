package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_STATUS;

@RequiredArgsConstructor
@Getter
public enum Status {
    NEW("새로운"),
    TEST("긴급"),
    GENERAL("일반");
    private final String stringStatus;

    public static Status getEnumStatusFromStringStatus(String stringStatus) {
        return Arrays.stream(values())
                .filter(status -> status.stringStatus.equals(stringStatus))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_STATUS));
    }
}

