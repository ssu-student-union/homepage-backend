package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ONGOING_STATUS;
import static ussum.homepage.global.error.status.ErrorStatus.WRONG_TRANSLATED_TO_KOREAN;

@RequiredArgsConstructor
@Getter
public enum OngoingStatus {
    IN_PROGRESS("진행중"),
    RECEIVED("접수완료"),
    ANSWERED("답변완료"),
    COMPLETED("종료됨");

    private final String stringOnGoingStatus;

    public static OngoingStatus getEnumOngoingStatusFromStringOngoingStatus(String stringOnGoingStatus) {
        return Arrays.stream(values())
                .filter(ongoingStatus -> ongoingStatus.stringOnGoingStatus.equals(stringOnGoingStatus))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ONGOING_STATUS));
    }

//    public static String toKorean(String stringOnGoingStatus) {
//        switch (stringOnGoingStatus) {
//            case "IN_PROGRESS":
//                return "진행중";
//            case "RECEIVED":
//                return "접수완료";
//            case "ANSWERED":
//                return "답변완료";
//            case "COMPLETED":
//                return "종료됨";
//            default:
//                throw new InvalidValueException(WRONG_TRANSLATED_TO_KOREAN);
//        }
//    }
}
