package ussum.homepage.application.calendar.service;

import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequestDto;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventCreateResponse;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventResponse;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.service.CalendarEventAppender;
import ussum.homepage.domain.calender.service.CalenderEventReader;
import ussum.homepage.infra.jpa.calendar_event.CalendarEventMapper;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;
import ussum.homepage.infra.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalenderEventReader calenderReader;
    private final CalendarEventAppender calendarEventAppender;

    @Transactional
    public List<?> getCalenders(Long userId,String query, String category) {

        YearMonth yearMonth = DateUtils.parseYearMonthFromString(query);
        List<CalendarEventResponse> calendarResponseList = null;

        if (category == null) {
            calendarResponseList = calenderReader.getCalenderEventsByYearMonthWithoutCategory(yearMonth).stream()
                    .map(CalendarEventResponse::of)
                    .toList();
        }else{
             CalendarCategory calendarCategory = CalendarCategory.getEnumCategoryCodeFromStringCategoryCode(category);
             calendarResponseList = calenderReader.getCalenderEventsByYearMonthWithoutCategory(yearMonth).stream()
                    .filter(calendarEvent -> calendarEvent.getCalendarCategory().equals(category))
                    .map(CalendarEventResponse::of)
                    .toList();
        }


        return calendarResponseList;
    }

    @Transactional
    public CalendarEventCreateResponse createCalendarSchedule(Long userId, CalendarEventRequestDto requestDto) {
        CalendarEvent calendarEvent = requestDto.toDomain(userId);
        calendarEventAppender.create(calendarEvent);
        return CalendarEventCreateResponse.of(calendarEvent.getId(), calendarEvent.getCalendarCategory());
    }
}
