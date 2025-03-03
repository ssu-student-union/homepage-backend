package ussum.homepage.application.calendar.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarEventCreateResponse {
    private Long calendarEventId;
    private String title;

    public static CalendarEventCreateResponse of(Long id, String title){
        return CalendarEventCreateResponse.builder()
                .calendarEventId(id)
                .title(title)
                .build();
    }
}
