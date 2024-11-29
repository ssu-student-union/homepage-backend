package ussum.homepage.infra.utils;

import java.util.Arrays;

public enum AllowedContentType {
    PNG("image/png"),
    JPEG("image/jpeg"),
    PDF("application/pdf");

    private final String contentType;

    AllowedContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static AllowedContentType fromContentType(String contentType) {
        return Arrays.stream(values())
                .filter(type -> type.getContentType().equals(contentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported Content-Type: " + contentType));
    }
}
