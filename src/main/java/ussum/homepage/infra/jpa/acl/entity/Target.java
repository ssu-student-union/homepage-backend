package ussum.homepage.infra.jpa.acl.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.group.entity.GroupCode;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Target {
    USER("USER"),
    EVERYONE("EVERYONE"),
    ANONYMOUS("ANONYMOUS");

    private final String stringTarget;

    public static Target getEnumTargetFromStringTarget(String stringTarget) {
        return Arrays.stream(values())
                .filter(target -> target.stringTarget.equals(stringTarget))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorStatus.INVALID_TARGET));
    }

    public static String fromEnumOrNull(Target target) {
        return Optional.ofNullable(target)
                .map(Target::getStringTarget)
                .orElse(null);
    }
}
