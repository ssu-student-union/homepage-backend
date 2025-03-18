package ussum.homepage.application.calendar.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.domain.calender.CalendarEvent;

@Builder(access = AccessLevel.PRIVATE)
public record CalendarEventDetailResponse (
        Long calenderId,
        String calendarCategory,
        String title,
        String startDate,
        String endDate,
        boolean isAuthor

) {
    public static CalendarEventDetailResponse of(CalendarEvent calendarEvent, boolean isAuthor) {
        return CalendarEventDetailResponse.builder()
                .calenderId(calendarEvent.getId())
                .calendarCategory(calendarEvent.getCalendarCategory())
                .title(calendarEvent.getTitle())
                .startDate(calendarEvent.getStartDate())
                .endDate(calendarEvent.getEndDate())
                .isAuthor(isAuthor)
                .build();
    }
}
