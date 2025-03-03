package ussum.homepage.domain.user;

import lombok.*;

/// TODO(inho): DDD 구조에 맞게 다시 리팩토링 해야함
@Getter
@AllArgsConstructor
public class MonthlySignupStats {
    private int year;
    private int month;
    private Long count;

}