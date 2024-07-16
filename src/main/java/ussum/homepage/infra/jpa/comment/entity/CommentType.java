package ussum.homepage.infra.jpa.comment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;
import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_COMMENT_TYPE;

@RequiredArgsConstructor
@Getter
public enum CommentType {
    GENERAL("general"),
    QNA("qna");
    private final String stringCommentType;
    public static CommentType getEnumCommentTypeFromStringCommentType(String stringCommentType) {
        return Arrays.stream(values())
                .filter(commentType -> commentType.stringCommentType.equals(stringCommentType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_COMMENT_TYPE));
    }

}
