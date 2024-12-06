package ussum.homepage.domain.user.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class UserNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public UserNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
