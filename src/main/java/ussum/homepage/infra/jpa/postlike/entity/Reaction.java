package ussum.homepage.infra.jpa.postlike.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_REACTION;

@RequiredArgsConstructor
@Getter
public enum Reaction {
    LIKE("like"),
    UNLIKE("unlike");
    private final String stringReaction;
    public static Reaction getEnumReactionFromStringReaction(String stringReaction) {
        return Arrays.stream(values())
                .filter(reaction -> reaction.stringReaction.equals(stringReaction))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_REACTION));
    }
}
