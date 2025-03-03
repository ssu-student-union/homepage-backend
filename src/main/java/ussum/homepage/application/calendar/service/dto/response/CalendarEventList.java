package ussum.homepage.application.calendar.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import ussum.homepage.global.common.PageInfo;

@Builder(access = AccessLevel.PRIVATE)
public record CalendarEventList<T>(
        List<T> calendarEventResponseList,
        List<String> allowedAuthorities,
        List<String> deniedAuthorities,
        PageInfo pageInfo
) {
    public static <T> CalendarEventList<T> of(List<T> calendarEventResponseList, PageInfo pageInfo) {
        return new CalendarEventList<>(calendarEventResponseList, List.of(), List.of(),pageInfo);
    }

    public CalendarEventList<T> validAuthorities(List<String> allowedAuthorities, List<String> deniedAuthorities) {
        return new CalendarEventList<>(this.calendarEventResponseList, allowedAuthorities, deniedAuthorities,this.pageInfo);
    }
}