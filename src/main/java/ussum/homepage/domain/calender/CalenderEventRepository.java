package ussum.homepage.domain.calender;

import java.time.YearMonth;
import java.util.List;

public interface CalenderEventRepository {
    List<CalendarEvent> findByStartDateBeforeAndEndDateAfter(YearMonth endOfMonth);
    CalendarEvent save(CalendarEvent calendarEvent);
}
