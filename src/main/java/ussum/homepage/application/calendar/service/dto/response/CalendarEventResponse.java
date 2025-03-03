package ussum.homepage.application.calendar.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.domain.calender.CalendarEvent;

@Builder(access = AccessLevel.PRIVATE)
public record CalendarEventResponse(
        Long calenderId,
        String calendarCategory,
        String title,
        String startDate,
        String endDate

) {
    public static CalendarEventResponse of(CalendarEvent calendarEvent) {
        return CalendarEventResponse.builder()
                .calenderId(calendarEvent.getId())
                .calendarCategory(calendarEvent.getCalendarCategory())
                .title(calendarEvent.getTitle())
                .startDate(calendarEvent.getStartDate())
                .endDate(calendarEvent.getEndDate())
                .build();
    }
}
