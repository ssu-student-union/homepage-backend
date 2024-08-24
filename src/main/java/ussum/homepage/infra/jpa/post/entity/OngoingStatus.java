package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.infra.jpa.member.entity.MemberCode;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ONGOING_STATUS;
import static ussum.homepage.global.error.status.ErrorStatus.WRONG_TRANSLATED_TO_KOREAN;

@RequiredArgsConstructor
@Getter
public enum OngoingStatus {
    IN_PROGRESS("진행중"),
    RECEIVED("접수완료"),
    ANSWERED("답변완료"),
    COMPLETED("종료됨"),
    EMERGENCY("긴급공지");

    private final String stringOnGoingStatus;

    public static OngoingStatus getEnumOngoingStatusFromStringOngoingStatus(String stringOnGoingStatus) {
        return Arrays.stream(values())
                .filter(ongoingStatus -> ongoingStatus.stringOnGoingStatus.equals(stringOnGoingStatus))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ONGOING_STATUS));
    }

    public static Optional<OngoingStatus> fromString(String stringOnGoingStatus) {
        if (!StringUtils.hasText(stringOnGoingStatus)) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(ongoingStatus -> ongoingStatus.stringOnGoingStatus.equals(stringOnGoingStatus))
                .findFirst();
    }

    public static OngoingStatus fromStringOrNull(String stringOnGoingStatus) {
        return fromString(stringOnGoingStatus).orElse(null);
    }

    public static String fromEnumOrNull(OngoingStatus ongoingStatus) {
        return Optional.ofNullable(ongoingStatus)
                .map(OngoingStatus::getStringOnGoingStatus)
                .orElse(null);
    }

}
