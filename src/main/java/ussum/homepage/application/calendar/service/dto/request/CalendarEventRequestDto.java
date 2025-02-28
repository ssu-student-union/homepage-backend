package ussum.homepage.application.calendar.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ussum.homepage.domain.calender.CalendarEvent;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalendarEventRequestDto {
    private String title;
    private String startDate;
    private String endDate;
    private String calendarCategory;

    public CalendarEvent toDomain(Long userId){
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
