package ussum.homepage.application.calendar.service.dto.request;

import ussum.homepage.domain.calender.CalendarEvent;

public record CalendarEventUpdateRequest(
        String calendarCategory,
        String startDate,
        String endDate,
        String title
) {
    public CalendarEvent toDomain(CalendarEvent calendarEvent){
        return CalendarEvent.of(
                calendarEvent.getId(),
                title,
                calendarEvent.getCreateBy(),
                calendarCategory,
                startDate,
                endDate
        );
    }
}
