package ussum.homepage.application.calendar.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.domain.calender.CalendarEvent;

@Builder(access = AccessLevel.PRIVATE)
public record CalendarEventResponse(
        Long calenderId,
        String category,
        String title,
        String startTime,
        String endTime

) {
    public static CalendarEventResponse of(CalendarEvent calendarEvent) {
        return CalendarEventResponse.builder()
                .calenderId(calendarEvent.getId())
                .category(calendarEvent.getCategory())
                .title(calendarEvent.getTitle())
                .startTime(calendarEvent.getStartDate())
                .endTime(calendarEvent.getEndDate())
                .build();
    }
}
