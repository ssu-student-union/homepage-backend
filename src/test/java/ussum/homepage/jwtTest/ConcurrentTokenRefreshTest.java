package ussum.homepage.jwtTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ussum.homepage.application.user.service.OAuthService;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.jwt.JwtTokenInfo;
import ussum.homepage.global.jwt.RefreshService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ConcurrentTokenRefreshTest {

    @MockBean
    private OAuthService oAuthService;

    @MockBean
    private UserReader userReader;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private UserModifier userModifier;

    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUsers = TestUserGenerator.generateRandomUsers(5);

        when(oAuthService.issueAccessTokenAndRefreshToken(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    return JwtTokenInfo.of(
                            "access_token_for_" + user.getId(),
                            "refresh_token_for_" + user.getId()
                    );
                });

        for (User user : testUsers) {
            when(userReader.getUserWithId(user.getId())).thenReturn(user);
            when(refreshService.getRefreshTokenInfo(user.getId()))
                    .thenReturn(JwtTokenInfo.of("access_token_for_" + user.getId(), "refresh_token_for_" + user.getId()));
        }

        when(userModifier.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testConcurrentTokenRefresh() throws InterruptedException, ExecutionException {
        int numberOfUsers = 5;
        ConcurrentHashMap<Long, String> generatedTokens = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        CountDownLatch startLatch = new CountDownLatch(1);

        for (int i = 0; i < numberOfUsers; i++) {
            final int index = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    startLatch.await();
                    User user = testUsers.get(index);
                    System.out.println("Starting token refresh for User " + user.getId());
                    JwtTokenInfo tokenInfo = oAuthService.issueAccessTokenAndRefreshToken(user);
                    String newRefreshToken = tokenInfo.getRefreshToken();

                    // 새로운 리프레시 토큰으로 사용자 정보 업데이트 및 저장
                    user.updateRefreshToken(newRefreshToken);
                    User savedUser = userModifier.save(user);

                    generatedTokens.put(user.getId(), newRefreshToken);
                    System.out.println("User " + user.getId() + " received and saved new refresh token: " + savedUser.getRefreshToken());
                } catch (Exception e) {
                    System.err.println("Error for user " + testUsers.get(index).getId() + ": " + e.getMessage());
                }
            });
            futures.add(future);
        }

        startLatch.countDown();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        // 검증
        Set<String> uniqueTokens = new HashSet<>(generatedTokens.values());
        assertEquals(numberOfUsers, uniqueTokens.size(), "Each user should have a unique refresh token");

        for (User user : testUsers) {
            User updatedUser = userReader.getUserWithId(user.getId());
            String storedToken = updatedUser.getRefreshToken();
            String generatedToken = generatedTokens.get(user.getId());

            assertNotNull(storedToken, "Stored token should not be null for user " + user.getId());
            assertEquals(generatedToken, storedToken,
                    "Stored token should match generated token for user " + user.getId());

            JwtTokenInfo cachedInfo = refreshService.getRefreshTokenInfo(user.getId());
            assertNotNull(cachedInfo, "Cached token info should not be null for user " + user.getId());
            assertEquals(storedToken, cachedInfo.getRefreshToken(),
                    "Cached token should match stored token for user " + user.getId());

            System.out.println("User " + user.getId() + " final token: " + storedToken);
        }
    }

    @Test
    void testConcurrentTokenRefresh2() throws InterruptedException, ExecutionException {
        int numberOfUsers = 5;
        ConcurrentHashMap<Long, String> generatedTokens = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        CountDownLatch startLatch = new CountDownLatch(1);

        for (int i = 0; i < numberOfUsers; i++) {
            final int index = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    startLatch.await();
                    User user = testUsers.get(index);
                    System.out.println("Starting token refresh for User " + user.getId());
                    JwtTokenInfo tokenInfo = oAuthService.issueAccessTokenAndRefreshTokenNotAsync(user);
                    String newRefreshToken = tokenInfo.getRefreshToken();

                    // 새로운 리프레시 토큰으로 사용자 정보 업데이트 및 저장
                    user.updateRefreshToken(newRefreshToken);
                    User savedUser = userModifier.save(user);

                    generatedTokens.put(user.getId(), newRefreshToken);
                    System.out.println("User " + user.getId() + " received and saved new refresh token: " + savedUser.getRefreshToken());
                } catch (Exception e) {
                    System.err.println("Error for user " + testUsers.get(index).getId() + ": " + e.getMessage());
                }
            });
            futures.add(future);
        }

        startLatch.countDown();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        // 검증
        Set<String> uniqueTokens = new HashSet<>(generatedTokens.values());
        assertEquals(numberOfUsers, uniqueTokens.size(), "Each user should have a unique refresh token");

        for (User user : testUsers) {
            User updatedUser = userReader.getUserWithId(user.getId());
            String storedToken = updatedUser.getRefreshToken();
            String generatedToken = generatedTokens.get(user.getId());

            assertNotNull(storedToken, "Stored token should not be null for user " + user.getId());
            assertEquals(generatedToken, storedToken,
                    "Stored token should match generated token for user " + user.getId());

            JwtTokenInfo cachedInfo = refreshService.getRefreshTokenInfo(user.getId());
            assertNotNull(cachedInfo, "Cached token info should not be null for user " + user.getId());
            assertEquals(storedToken, cachedInfo.getRefreshToken(),
                    "Cached token should match stored token for user " + user.getId());

            System.out.println("User " + user.getId() + " final token: " + storedToken);
        }
    }

    @Test
    void testConcurrentTokenRefreshNotAsync() throws InterruptedException, ExecutionException {
        int numberOfUsers = 5;
        ConcurrentHashMap<Long, JwtTokenInfo> generatedTokens = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        CountDownLatch startLatch = new CountDownLatch(1);

        for (int i = 0; i < numberOfUsers; i++) {
            final int index = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    startLatch.await(); // 모든 스레드가 준비될 때까지 대기
                    User user = testUsers.get(index);
                    System.out.println("Starting token refresh for User " + user.getId());
                    JwtTokenInfo tokenInfo = oAuthService.issueAccessTokenAndRefreshTokenNotAsync(user);
                    generatedTokens.put(user.getId(), tokenInfo);
                    System.out.println("User " + user.getId() + " received token: " +
                            (tokenInfo != null ? tokenInfo : "생성되지 않음"));
                } catch (Exception e) {
                    System.err.println("Error for user " + testUsers.get(index).getId() + ": " + e.getMessage());
                }
            });
            futures.add(future);
        }

        startLatch.countDown(); // 모든 스레드 동시에 시작
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        // 검증
        Set<String> uniqueAccessTokens = new HashSet<>();
        Set<String> uniqueRefreshTokens = new HashSet<>();

        for (JwtTokenInfo tokenInfo : generatedTokens.values()) {
            if (tokenInfo != null) {
                uniqueAccessTokens.add(tokenInfo.getAccessToken());
                uniqueRefreshTokens.add(tokenInfo.getRefreshToken());
            }
        }

        System.out.println("Number of unique access tokens: " + uniqueAccessTokens.size());
        System.out.println("Number of unique refresh tokens: " + uniqueRefreshTokens.size());

        // 동시성 문제가 있다면, 유니크한 토큰의 수가 사용자 수보다 적을 것입니다.
        assertTrue(uniqueAccessTokens.size() < numberOfUsers || uniqueRefreshTokens.size() < numberOfUsers,
                "Expected fewer unique tokens due to concurrency issues.");

        // 각 사용자의 최종 토큰 상태 확인
        for (User user : testUsers) {
            User updatedUser = userReader.getUserWithId(user.getId());
            JwtTokenInfo generatedToken = generatedTokens.get(user.getId());

            System.out.println("User " + user.getId() + " final token: " +
                    (updatedUser.getRefreshToken() != null ? updatedUser.getRefreshToken() : "생성되지 않음"));
            System.out.println("Generated token for User " + user.getId() + ": " +
                    (generatedToken != null ? generatedToken.getRefreshToken() : "생성되지 않음"));
        }
    }
}
