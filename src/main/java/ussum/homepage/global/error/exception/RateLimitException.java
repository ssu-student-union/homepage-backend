package ussum.homepage.global.error.exception;

import ussum.homepage.global.error.code.BaseErrorCode;

public class RateLimitException extends GeneralException {

    private BaseErrorCode baseErrorCode;

    public RateLimitException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
