package ussum.homepage.infra.jpa.acl.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_ACTION;

@RequiredArgsConstructor
@Getter
public enum Action {
    WRITE("WRITE"),
    LIST("LIST"),
    EDIT("EDIT"),
    READ("READ"),
    EDIT_PROPERTIES("EDIT_PROPERTIES"),
    DELETE("DELETE"),
    DELETE_COMMENT("DELETE_COMMENT"),
    COMMENT("COMMENT"),
    REACTION("REACTION");

    private final String stringAction;

    public static Action getEnumActionFromStringAction(String stringAction) {
        return Arrays.stream(values())
                .filter(order -> order.stringAction.equals(stringAction))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ACTION));
    }

    public static String fromEnumOrNull(Action action) {
        return Optional.ofNullable(action)
                .map(Action::getStringAction)
                .orElse(null);
    }
}
