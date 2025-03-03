package ussum.homepage.domain.calender;

import java.time.YearMonth;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;

public interface CalenderEventRepository {
    Page<CalendarEvent> findByStartDateAfterAndEndDateBefore(YearMonth yearMonth, Pageable pageable);
    Page<CalendarEvent> findByCalendarEventsWithCategory(YearMonth yearMonth, CalendarCategory calendarCategory, Pageable pageable);
    CalendarEvent findById(Long id);
    CalendarEvent save(CalendarEvent calendarEvent);
    void delete(CalendarEvent calendarEvent);
}
