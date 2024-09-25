package ussum.homepage.global.jwt;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final JwtTokenProvider provider;
    private final UserModifier userModifier;
    private final Cache<Long, JwtTokenInfo> tokenCache;
    private final UserReader userReader;

    public JwtTokenInfo getRefreshTokenInfo(Long userId) {
        return tokenCache.getIfPresent(userId);
    }

    @Transactional
    public void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        userModifier.save(user);
        cacheTokenInfo(user, refreshToken);
    }

    private void cacheTokenInfo(User user, String refreshToken) {
        JwtTokenInfo tokenInfo = JwtTokenInfo.of(provider.createAccessToken(user), refreshToken);
        tokenCache.put(user.getId(), tokenInfo);
    }

    @Transactional
    public boolean validateAndUpdateRefreshToken(Long userId, String oldRefreshToken, String newRefreshToken) {
        User user = userReader.getUserWithId(userId);

        if (user.getRefreshToken().equals(oldRefreshToken)) {
            user.setRefreshToken(newRefreshToken);
            userModifier.save(user);
            cacheTokenInfo(user, newRefreshToken);
            return true;
        }
        return false;
    }

    public void invalidateRefreshToken(Long userId) {
        User user = userReader.getUserWithId(userId);
        if (user != null) {
            user.setRefreshToken(null);
            userModifier.save(user);
            tokenCache.invalidate(userId);
        }
    }
}