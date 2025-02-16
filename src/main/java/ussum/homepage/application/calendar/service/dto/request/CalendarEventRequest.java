package ussum.homepage.application.calendar.service.dto.request;

import lombok.Builder;
import ussum.homepage.domain.calender.CalendarEvent;

@Builder
public record CalendarEventRequest(
        String category,
        String startDate,
        String endDate,
        String title
) {
    public CalendarEvent toDomain(Long userId) {
        return CalendarEvent.of(
                null,
                title,
                userId,
                category,
                startDate,
                endDate
        );
    }
}
