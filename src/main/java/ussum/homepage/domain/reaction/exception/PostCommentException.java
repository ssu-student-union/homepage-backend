package ussum.homepage.domain.reaction.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class PostCommentException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public PostCommentException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
