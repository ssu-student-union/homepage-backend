package ussum.homepage.domain.calender.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;

@Service
@RequiredArgsConstructor
public class CalendarEventReader {
    private final CalenderEventRepository calenderEventRepository;

    public List<CalendarEvent> getCalenderEventsByYearMonthWithoutCategory(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return calenderEventRepository.findByStartDateAfterAndEndDateBefore(yearMonth);
    }

    public CalendarEvent getCalendarEventByCalendarEventId(Long calendarEventId) {
        return calenderEventRepository.findById(calendarEventId);
    }
}
