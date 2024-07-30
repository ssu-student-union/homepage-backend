package ussum.homepage.global.error.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.dto.ErrorReasonDto;
import ussum.homepage.global.error.status.ErrorStatus;

public class UnauthorizedException extends GeneralException {
    private BaseErrorCode errorCode;
    public UnauthorizedException() {
        super(ErrorStatus.UNAUTHORIZED);
    }
    public UnauthorizedException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}