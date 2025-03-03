package ussum.homepage.domain.calender;

import java.time.YearMonth;
import java.util.List;

public interface CalenderEventRepository {
    List<CalendarEvent> findByStartDateAfterAndEndDateBefore(YearMonth endOfMonth);
    CalendarEvent findById(Long id);
    CalendarEvent save(CalendarEvent calendarEvent);
    void delete(CalendarEvent calendarEvent);
}
