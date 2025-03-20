package ussum.homepage.application.user.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.user.UserCountRepository;

@Service
public class UserMetricService {

    private final Counter userSignupTotal;   // 전체 가입자 수
    private final AtomicLong todayUsers;  // 오늘 가입자 수
    private final AtomicLong lastMonthUsers;  // 최근 1개월 가입자 수
    private final AtomicLong lastYearUsers;   // 최근 1년 가입자 수

    private final UserCountRepository userCountRepository;

    @Autowired
    public UserMetricService(MeterRegistry meterRegistry, UserCountRepository userRepository) {
        this.userCountRepository = userRepository;

        long initialUserCount = userCountRepository.count();

        this.userSignupTotal = Counter.builder("user_signup_total")
            .description("총 가입자 수")
            .register(meterRegistry);

        // 실행 시 총 가입자 수 초기화
        for (int i = 0; i < initialUserCount; i++) {
            userSignupTotal.increment();
        }

        this.todayUsers = new AtomicLong(0);
        this.lastMonthUsers = new AtomicLong(0);
        this.lastYearUsers = new AtomicLong(0);

        // Gauge 등록 (실시간 값 반영)
        Gauge.builder("user_signup_today", todayUsers, AtomicLong::get)
            .description("오늘 가입 한 사용자 수")
            .register(meterRegistry);

        Gauge.builder("user_signup_last_month", lastMonthUsers, AtomicLong::get)
            .description("최근 한 달 가입 한 사용자 수")
            .register(meterRegistry);

        Gauge.builder("user_signup_last_year", lastYearUsers, AtomicLong::get)
            .description("최근 1년 간 가입 한 사용자 수")
            .register(meterRegistry);

        updateSignupMetrics(); // 서버 시작 시 초기값 설정
    }

    // 회원가입 시 카운트 증가
    public void incrementUserSignup() {
        userSignupTotal.increment();
        todayUsers.incrementAndGet();
        lastMonthUsers.incrementAndGet();
        lastYearUsers.incrementAndGet();
    }

    // 5분마다 DB 기간별 가입자 수 갱신
    @Scheduled(fixedRate = 300000) // 5분마다 실행
    public void updateSignupMetrics() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusMonths(1);
        LocalDate oneYearAgo = today.minusYears(1);

        todayUsers.set(userCountRepository.countByDate(today.atStartOfDay()));
        lastMonthUsers.set(userCountRepository.countByDateAfter(oneMonthAgo.atStartOfDay()));
        lastYearUsers.set(userCountRepository.countByDateAfter(oneYearAgo.atStartOfDay()));
    }

}
