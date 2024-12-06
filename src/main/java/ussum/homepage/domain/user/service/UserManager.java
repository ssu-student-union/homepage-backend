package ussum.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ussum.homepage.global.error.exception.GeneralException;

import java.time.Period;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class UserManager {
    private final PasswordEncoder passwordEncoder;

    public void validatePassword(String rawpassword, String encodedpassword) {
        Optional.of(passwordEncoder.matches(rawpassword, encodedpassword))
                .filter(match -> match)
                .orElseThrow(() -> new GeneralException(PASSWORD_NOT_MATCH));
    }
}
