package ussum.homepage.global.error.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.dto.ErrorReasonDto;

public class UnauthorizedException extends GeneralException {
    private BaseErrorCode errorCode;

    public UnauthorizedException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}