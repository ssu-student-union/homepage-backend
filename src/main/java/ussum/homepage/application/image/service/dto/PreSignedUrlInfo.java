package ussum.homepage.application.image.service.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class PreSignedUrlInfo {
    private final String preSignedUrl;
    private final String fileUrl;
    private final String originalFileName;
    private final LocalDateTime expirationTime;  // URL 만료 시간 추가

    private PreSignedUrlInfo(String preSignedUrl, String fileUrl, String originalFileName, LocalDateTime expirationTime) {
        this.preSignedUrl = validateNotBlank(preSignedUrl, "PreSigned URL");
        this.fileUrl = validateNotBlank(fileUrl, "File URL");
        this.originalFileName = validateNotBlank(originalFileName, "Original File Name");
        this.expirationTime = Objects.requireNonNull(expirationTime, "Expiration Time must not be null");
    }

    private String validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 필수값입니다.");
        }
        return value;
    }

    public static PreSignedUrlInfo of(String preSignedUrl, String fileUrl, String originalFileName, LocalDateTime expirationTime) {
        return new PreSignedUrlInfo(preSignedUrl, fileUrl, originalFileName, expirationTime);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
