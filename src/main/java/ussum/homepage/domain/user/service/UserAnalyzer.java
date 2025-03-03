package ussum.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.user.MonthlySignupStats;
import ussum.homepage.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnalyzer {
    private final UserRepository userRepository;

    public Long getTotalUserCount() {
        return userRepository.findTotalUserCount();
    }

    public Long  getNewUserCountBetween(LocalDateTime start, LocalDateTime end) {
        return userRepository.findNewUserCountBetween(start, end);
    }

    public List<MonthlySignupStats> getMonthlySignupStats(int year) {
        return userRepository.findMonthlySignupStats(year);
    }
}
