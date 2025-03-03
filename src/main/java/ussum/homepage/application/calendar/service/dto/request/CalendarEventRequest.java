package ussum.homepage.application.calendar.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ussum.homepage.domain.calender.CalendarEvent;

@Builder
public record CalendarEventRequest(
        String calendarCategory,
        String startDate,
        String endDate,
        String title
){
    public CalendarEvent toDomain(Long userId) {
        return CalendarEvent.of(
                null,
                title,
                userId,
                calendarCategory,
                startDate,
                endDate
        );
    }
}
