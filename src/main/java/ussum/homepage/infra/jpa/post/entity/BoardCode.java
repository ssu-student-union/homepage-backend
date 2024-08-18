package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_BOARDCODE;

@RequiredArgsConstructor
@Getter
public enum BoardCode {
    AUDIT("AUDIT"),
    NOTICE("NOTICE"),
    LOST("LOST"),
    PARTNER("PARTNER"),
    PETITION("PETITION"),
    DATA("DATA");

    private final String stringBoardCode;
    public static BoardCode getEnumBoardCodeFromStringBoardCode(String stringBoardCode) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.stringBoardCode.equals(stringBoardCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_BOARDCODE));
    }
}
