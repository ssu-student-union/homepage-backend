package ussum.homepage.domain.reaction.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class PostCommentReactionException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public PostCommentReactionException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
