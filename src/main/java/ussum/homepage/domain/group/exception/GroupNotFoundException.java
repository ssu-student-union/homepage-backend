package ussum.homepage.domain.group.exception;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

public class GroupNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public GroupNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
