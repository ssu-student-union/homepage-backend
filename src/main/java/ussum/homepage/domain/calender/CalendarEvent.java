package ussum.homepage.domain.calender;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalendarEvent {
    private Long id;
    private String title;
    private Long createBy;
    private String category;
    private String startDate;
    private String endDate;

    public static CalendarEvent of(Long id, String title, Long createBy, String category, String startDate, String endDate) {
        return new CalendarEvent(id, title, createBy, category, startDate, endDate);
    }
}
