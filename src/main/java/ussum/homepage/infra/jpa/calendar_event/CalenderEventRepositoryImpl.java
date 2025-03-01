package ussum.homepage.infra.jpa.calendar_event;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarEventEntity;
import ussum.homepage.infra.jpa.calendar_event.respository.CalendarEventJpaRepository;

@Repository
@RequiredArgsConstructor
public class CalenderEventRepositoryImpl implements CalenderEventRepository {
    private final CalendarEventMapper calendarEventMapper;
    private final CalendarEventJpaRepository calendarEventJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CalendarEvent> findByStartDateAfterAndEndDateBefore(YearMonth endOfMonth) {
        LocalDate startOfMonth = endOfMonth.atDay(1);  // 해당 월의 첫날 (yyyy-MM-01)
        LocalDate endOfMonthDate = endOfMonth.atEndOfMonth();; // 해당 월의 마지막 날 (yyyy-MM-dd)

        System.out.println(startOfMonth+","+endOfMonthDate);
        return calendarEventJpaRepository.findEventsOverlappingWithPeriod(startOfMonth, endOfMonthDate)
                .stream()
                .map(calendarEventMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public CalendarEvent save(CalendarEvent calendarEvent) {
        return calendarEventMapper.toDomain(
                calendarEventJpaRepository.save(calendarEventMapper.toEntity(calendarEvent)));
    }
}
