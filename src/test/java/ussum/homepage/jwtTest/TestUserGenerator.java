package ussum.homepage.jwtTest;

import ussum.homepage.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Random;

public class TestUserGenerator {

    private static final Random random = new Random();

    public static List<User> generateRandomUsers(int count) {
        return IntStream.range(1, count+1)
                .mapToObj(i -> User.of(
                        (long) i,  // id
                        generateRandomName(),  // name
                        generateRandomStudentId(),  // studentId
                        UUID.randomUUID().toString(),  // kakaoId
                        "http://example.com/profile" + i + ".jpg",  // profileImage
                        "account" + i,  // accountId
                        "password" + i,  // password
                        LocalDateTime.now(),  // createdAt
                        LocalDateTime.now(),  // updatedAt
                        UUID.randomUUID().toString()  // refreshToken
                ))
                .collect(Collectors.toList());
    }

    private static String generateRandomName() {
        String[] firstNames = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }

    private static String generateRandomStudentId() {
        return String.format("%d%02d%04d",
                2020 + random.nextInt(5),  // Year (2020-2024)
                1 + random.nextInt(12),    // Month (01-12)
                1000 + random.nextInt(9000)  // Random 4-digit number
        );
    }
}
