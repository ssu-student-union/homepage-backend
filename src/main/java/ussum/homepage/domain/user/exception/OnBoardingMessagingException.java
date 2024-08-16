package ussum.homepage.domain.user.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class OnBoardingMessagingException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public OnBoardingMessagingException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
