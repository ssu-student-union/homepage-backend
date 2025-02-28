package ussum.homepage.application.calendar.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CalendarEventList<T>(
        List<T> calendarEventResponseList,
        List<String> allowedAuthorities,
        List<String> deniedAuthorities
) {
    public static <T> CalendarEventList<T> of(List<T> calendarEventResponseList) {
        return new CalendarEventList<>(calendarEventResponseList, List.of(), List.of());
    }
    public static CalendarEventList<CalendarEventResponse> fromList(List<CalendarEventResponse> events) {
        return new CalendarEventList<>(events, List.of(), List.of());
    }

    public CalendarEventList<T> validAuthorities(List<String> allowedAuthorities, List<String> deniedAuthorities) {
        return new CalendarEventList<>(this.calendarEventResponseList, allowedAuthorities, deniedAuthorities);
    }
}