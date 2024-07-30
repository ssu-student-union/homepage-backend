package ussum.homepage.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.dto.ErrorReasonDto;

@Getter
public class GeneralException extends RuntimeException {
    private final BaseErrorCode baseErrorCode;

    public GeneralException(String message, BaseErrorCode baseErrorCode){
        super(message);
        this.baseErrorCode = baseErrorCode;
    }

    public GeneralException(BaseErrorCode baseErrorCode){
        super(baseErrorCode.getReason().getMessage());
        this.baseErrorCode = baseErrorCode;
    }

    public ErrorReasonDto getErrorReason() {
        return this.baseErrorCode.getReason();
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return this.baseErrorCode.getReasonHttpStatus();
    }
}
