package ussum.homepage.infra.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.EnumSet;
import java.util.Set;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_CONTENT_TYPE;

@Component
@RequiredArgsConstructor
public class ContentTypeValidator {
    private final Set<AllowedContentType> allowedTypes =
            EnumSet.allOf(AllowedContentType.class);

    public void validate(String contentType) {
        try {
            AllowedContentType.fromContentType(contentType);
        } catch (IllegalArgumentException e) {
            throw new InvalidValueException(INVALID_CONTENT_TYPE);
        }
    }
}
