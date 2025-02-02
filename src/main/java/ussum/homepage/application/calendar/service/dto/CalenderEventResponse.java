package ussum.homepage.application.calendar.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.domain.calender.CalenderEvent;

@Builder(access = AccessLevel.PRIVATE)
public record CalenderEventResponse(
        Long calenderId,
        String category,
        String title,
        String startTime,
        String endTime

) {
    public static CalenderEventResponse of(CalenderEvent calenderEvent) {
        return CalenderEventResponse.builder()
                .calenderId(calenderEvent.getId())
                .category(calenderEvent.getCategory())
                .title(calenderEvent.getTitle())
                .startTime(calenderEvent.getStartDate())
                .endTime(calenderEvent.getEndDate())
                .build();
    }
}
