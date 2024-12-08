package ussum.homepage.global.external.discord.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EventMessage {
    // Error
    SIGN_UP_FAIL("회원가입을 실패한 사용자가 있습니다. ");

    // Success
    // TO DO : 성공 메시지 추가

    private final String message;
}
