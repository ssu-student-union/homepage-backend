package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_BOARDCODE;

@RequiredArgsConstructor
@Getter
public enum BoardCode {
    AUDIT("감사기구게시판", 1),
    NOTICE("공지사항게시판", 2),
    LOST("분실물게시판", 3),
    PARTNER("제휴게시판", 4),
    PETITION("청원게시판", 5),
    DATA("자료집게시판", 6),
    SUGGESTION("건의게시판", 7),
    RIGHTS("인권신고게시판", 8),
    SERVICE_NOTICE("서비스공지사항", 9),
    QNA("질의응답게시판", 10),
    CALENDAR("캘린더", 11);

    private final String stringBoardCode;
    private final int boardId;

    public static BoardCode getEnumBoardCodeFromStringBoardCode(String stringBoardCode) {
        // 한글 코드로 찾기
        Optional<BoardCode> byStringCode = Arrays.stream(values())
                .filter(boardCode -> boardCode.stringBoardCode.equals(stringBoardCode))
                .findFirst();
        if (byStringCode.isPresent()) {
            return byStringCode.get();
        }

        // 찾지 못했다면 영어 코드로 찾기
        try {
            return BoardCode.valueOf(stringBoardCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidValueException(INVALID_BOARDCODE);
        }
    }

    public static BoardCode getEnumBoardCodeFromBoardId(Long boardId) {
        return Arrays.stream(values())
                .filter(boardCode -> boardCode.boardId == boardId)
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_BOARDCODE));
    }
}
