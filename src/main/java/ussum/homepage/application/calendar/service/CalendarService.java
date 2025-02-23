package ussum.homepage.application.calendar.service;

import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventCreateResponse;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventResponse;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.service.CalendarEventAppender;
import ussum.homepage.domain.calender.service.CalenderEventReader;
import ussum.homepage.infra.jpa.calendar_event.CalendarEventMapper;
import ussum.homepage.infra.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalenderEventReader calenderReader;
    private final CalendarEventAppender calendarEventAppender;

    @Transactional
    public List<?> getCalenders(String query, String category) {
        YearMonth yearMonth = DateUtils.parseYearMonthFromString(query);
        List<CalendarEventResponse> calendarResponseList = calenderReader.getCalenderEventsByYearMonth(yearMonth).stream()
                .filter(calenderEvent -> calenderEvent.getCalendarCategory().equals(category))
                .map(CalendarEventResponse::of)
                .toList();

        return calendarResponseList;
    }

    @Transactional
    public CalendarEventCreateResponse createCalendarSchedule(Long userId, CalendarEventRequest calendarEventRequest) {
        CalendarEvent calendarEvent = calendarEventRequest.toDomain(userId);
        calendarEventAppender.create(calendarEvent);
        return CalendarEventCreateResponse.of(calendarEvent.getId(), calendarEventRequest.title());
    }
}
