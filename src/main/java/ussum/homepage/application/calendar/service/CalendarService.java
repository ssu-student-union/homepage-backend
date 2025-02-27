package ussum.homepage.application.calendar.service;

import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequestDto;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventCreateResponse;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventList;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventResponse;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.service.CalendarEventAppender;
import ussum.homepage.domain.calender.service.CalenderEventReader;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.calendar_event.CalendarEventMapper;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;
import ussum.homepage.infra.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalenderEventReader calenderReader;
    private final CalendarEventAppender calendarEventAppender;
    private final BoardReader boardReader;
    @Transactional
    public CalendarEventList<?> getCalenders(Long userId, String query, String category, String boardCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);

        if (!board.getName().equals(boardCode)) {
            throw new InvalidValueException(ErrorStatus.INVALID_BOARDCODE);
        }

        YearMonth yearMonth = DateUtils.parseYearMonthFromString(query);
        List<CalendarEventResponse> calendarEventResponseList = null;

        if (category == null) {
            calendarEventResponseList = calenderReader.getCalenderEventsByYearMonthWithoutCategory(yearMonth).stream()
                    .map(CalendarEventResponse::of)
                    .toList();
        }else{
             CalendarCategory calendarCategory = CalendarCategory.getEnumCategoryCodeFromStringCategoryCode(category);
            calendarEventResponseList = calenderReader.getCalenderEventsByYearMonthWithoutCategory(yearMonth).stream()
                    .filter(calendarEvent -> calendarEvent.getCalendarCategory().equals(category))
                    .map(CalendarEventResponse::of)
                    .toList();
        }

        return CalendarEventList.of(calendarEventResponseList);
    }

    @Transactional
    public CalendarEventCreateResponse createCalendarSchedule(Long userId, CalendarEventRequestDto requestDto) {
        CalendarEvent calendarEvent = requestDto.toDomain(userId);
        calendarEventAppender.create(calendarEvent);
        return CalendarEventCreateResponse.of(calendarEvent.getId(), calendarEvent.getCalendarCategory());
    }
}
