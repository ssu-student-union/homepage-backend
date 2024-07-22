package ussum.homepage.domain.post.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class PostException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public PostException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
