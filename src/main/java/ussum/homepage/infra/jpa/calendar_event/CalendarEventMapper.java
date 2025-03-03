package ussum.homepage.infra.jpa.calendar_event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarEventEntity;
import ussum.homepage.infra.utils.DateUtils;

@Component
public class CalendarEventMapper {

    public CalendarEvent toDomain(CalendarEventEntity calendarEventEntity){
        String calendarCategory = CalendarCategory.fromEnumOrNull(calendarEventEntity.getCalendarCategory());
        String startDate = DateUtils.formatToCustomString2(calendarEventEntity.getStartDate());
        String endDate = DateUtils.formatToCustomString2(calendarEventEntity.getEndDate());

        return CalendarEvent.of(calendarEventEntity.getId(),
                calendarEventEntity.getTitle(),
                calendarEventEntity.getCreateBy(),
                calendarCategory,
                startDate,
                endDate);
    }

    public CalendarEventEntity toEntity(CalendarEvent calendarEvent){
        CalendarCategory calendarCategory = CalendarCategory.getEnumCategoryCodeFromStringCategoryCode(calendarEvent.getCalendarCategory());
        LocalDate startDate = LocalDate.parse(calendarEvent.getStartDate());
        LocalDate endDate = LocalDate.parse(calendarEvent.getEndDate());

        return CalendarEventEntity.of(calendarEvent.getId(),
                calendarEvent.getTitle(),
                startDate,
                endDate,
                calendarCategory,
                calendarEvent.getCreateBy(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
