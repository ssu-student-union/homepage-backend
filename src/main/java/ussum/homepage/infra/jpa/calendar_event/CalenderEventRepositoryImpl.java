package ussum.homepage.infra.jpa.calendar_event;

import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.calender.CalenderEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;

@Repository
@RequiredArgsConstructor
public class CalenderEventRepositoryImpl implements CalenderEventRepository {

    @Override
    public List<CalenderEvent> findByStartDateBeforeAndEndDateAfter(YearMonth endOfMonth) {
        return null;
    }
}
