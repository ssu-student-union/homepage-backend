package ussum.homepage.infra.jpa.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ROLE;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");
    private final String stringRole;
    public static Role getEnumRoleFromStringRole(String stringRole) {
        return Arrays.stream(values())
                .filter(role -> role.stringRole.equals(stringRole))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ROLE));
    }
}
