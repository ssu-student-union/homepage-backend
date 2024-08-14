package ussum.homepage.domain.member.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class MemberNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public MemberNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
