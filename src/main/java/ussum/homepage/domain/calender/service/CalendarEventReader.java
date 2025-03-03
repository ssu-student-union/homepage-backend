package ussum.homepage.domain.calender.service;

import java.time.YearMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;

@Service
@RequiredArgsConstructor
public class CalendarEventReader {
    private final CalenderEventRepository calenderEventRepository;

    public Page<CalendarEvent> getCalenderEventsByYearMonthWithoutCategory(YearMonth yearMonth, Pageable pageable) {
        return calenderEventRepository.findByStartDateAfterAndEndDateBefore(yearMonth, pageable);
    }

    public Page<CalendarEvent> getCalenderEventsByYearMonthWithCategory(YearMonth yearMonth, CalendarCategory calendarCategory, Pageable pageable) {
        return calenderEventRepository.findByCalendarEventsWithCategory(yearMonth, calendarCategory,pageable);
    }

    public CalendarEvent getCalendarEventByCalendarEventId(Long calendarEventId) {
        return calenderEventRepository.findById(calendarEventId);
    }
}
