package ussum.homepage.global.error.exception;

import ussum.homepage.global.error.code.BaseErrorCode;

public class InvalidValueException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public InvalidValueException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}