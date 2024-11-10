package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_BOARDCODE;

@RequiredArgsConstructor
@Getter
public enum BoardCode {
    AUDIT("감사기구게시판"),
    NOTICE("공지사항게시판"),
    LOST("분실물게시판"),
    PARTNER("제휴게시판"),
    PETITION("청원게시판"),
    DATA("자료집게시판"),
    SUGGESTION("건의게시판"),
    RIGHTS("인권신고게시판");

    private final String stringBoardCode;
    public static BoardCode getEnumBoardCodeFromStringBoardCode(String stringBoardCode) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.stringBoardCode.equals(stringBoardCode))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_BOARDCODE));
    }
}
