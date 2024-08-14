package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ONGOING_STATUS;

@RequiredArgsConstructor
@Getter
public enum OngoingStatus {
    IN_PROGRESS("IN_PROGRESS"),
    RECEIVED("RECEIVED"),
    ANSWERED("ANSWERED"),
    COMPLETED("COMPLETED");

    private final String stringOnGoingStatus;

    public static OngoingStatus getEnumOngoingStatusFromStringOngoingStatus(String stringOnGoingStatus) {
        return Arrays.stream(values())
                .filter(ongoingStatus -> ongoingStatus.stringOnGoingStatus.equals(stringOnGoingStatus))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ONGOING_STATUS));
    }
}
