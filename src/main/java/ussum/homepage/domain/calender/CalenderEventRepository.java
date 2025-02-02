package ussum.homepage.domain.calender;

import java.time.YearMonth;
import java.util.List;

public interface CalenderEventRepository {
    List<CalenderEvent> findByStartDateBeforeAndEndDateAfter(YearMonth endOfMonth);
}
