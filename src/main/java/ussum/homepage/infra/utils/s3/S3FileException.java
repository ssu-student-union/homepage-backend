package ussum.homepage.infra.utils.s3;

import lombok.Getter;
import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.exception.GeneralException;

@Getter
public class S3FileException extends GeneralException {

    public S3FileException(String message, BaseErrorCode baseErrorCode) {
        super(message, baseErrorCode);
    }

    public S3FileException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    // 파일이 존재하지 않을 때의 예외
    public static class FileNotFoundException extends S3FileException {
        public FileNotFoundException(BaseErrorCode baseErrorCode) {
            super(baseErrorCode);
        }
    }

    // 잘못된 S3 URL 형식일 때의 예외
    public static class InvalidS3UrlException extends S3FileException {
        public InvalidS3UrlException(BaseErrorCode baseErrorCode) {
            super(baseErrorCode);
        }
    }

    // 파일 업로드 실패 예외
    public static class UploadFailedException extends S3FileException {
        public UploadFailedException(String message, BaseErrorCode baseErrorCode) {
            super(message, baseErrorCode);
        }
    }
}
