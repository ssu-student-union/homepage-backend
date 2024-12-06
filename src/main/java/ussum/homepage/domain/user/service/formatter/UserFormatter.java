package ussum.homepage.domain.user.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.user.service.dto.response.UserResponse;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;

@Service
@RequiredArgsConstructor
public class UserFormatter implements ussum.homepage.domain.user.service.UserFormatter {
    private final UserReader userReader;
    @Override
    public UserResponse format(Long userId) {
        final User user = userReader.getUserWithId(userId);
        return UserResponse.of(user, true); // format을 사용한다는 것 자체가 처음 로그인해서 onBoarding을 한다는거.
    }
}
