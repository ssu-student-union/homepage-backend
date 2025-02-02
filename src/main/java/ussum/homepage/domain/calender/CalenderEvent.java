package ussum.homepage.domain.calender;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalenderEvent {
    private Long id;
    private String title;
    private Long createBy;
    private String category;
    private String startDate;
    private String endDate;

    public static CalenderEvent of(Long id, String title, Long createBy, String category, String location, String startDate, String endDate) {
        return new CalenderEvent(id, title, createBy, category, startDate, endDate);
    }
}
