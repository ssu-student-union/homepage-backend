package ussum.homepage.global.error.exception;

import lombok.Getter;
import ussum.homepage.global.error.code.BaseErrorCode;

@Getter
public class BusinessException extends GeneralException {
    private BaseErrorCode errorCode;

    public BusinessException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}