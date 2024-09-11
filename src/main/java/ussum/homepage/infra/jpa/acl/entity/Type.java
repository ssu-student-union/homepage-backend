package ussum.homepage.infra.jpa.acl.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_TYPE;

@RequiredArgsConstructor
@Getter
public enum Type {
    ALLOW("ALLOW"),
    DENY("DENY");

    private final String stringType;

    public static Type getEnumTypeFromStringType(String stringType) {
        return Arrays.stream(values())
                .filter(type -> type.stringType.equals(stringType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TYPE));
    }

    public static String fromEnumOrNull(Type type) {
        return Optional.ofNullable(type)
                .map(Type::getStringType)
                .orElse(null);
    }
}
