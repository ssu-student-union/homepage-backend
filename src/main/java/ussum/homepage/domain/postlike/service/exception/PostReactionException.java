package ussum.homepage.domain.postlike.service.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class PostReactionException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public PostReactionException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
