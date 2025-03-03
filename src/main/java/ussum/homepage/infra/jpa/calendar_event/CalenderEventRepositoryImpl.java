package ussum.homepage.infra.jpa.calendar_event;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarEventEntity;
import ussum.homepage.infra.jpa.calendar_event.respository.CalendarEventJpaRepository;

@Repository
@RequiredArgsConstructor
public class CalenderEventRepositoryImpl implements CalenderEventRepository {
    private final CalendarEventMapper calendarEventMapper;
    private final CalendarEventJpaRepository calendarEventJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CalendarEvent> findByStartDateAfterAndEndDateBefore(YearMonth endOfMonth, Pageable pageable) {
        LocalDate startOfMonth = endOfMonth.atDay(1);  // 해당 월의 첫날 (yyyy-MM-01)
        LocalDate endOfMonthDate = endOfMonth.atEndOfMonth();; // 해당 월의 마지막 날 (yyyy-MM-dd)
        Page<CalendarEventEntity> entityPage = calendarEventJpaRepository.findEventsOverlappingWithPeriod(startOfMonth, endOfMonthDate, pageable);

        return entityPage.map(calendarEventMapper :: toDomain);
    }

    @Override
    public Page<CalendarEvent> findByCalendarEventsWithCategory(YearMonth yearMonth, CalendarCategory calendarCategory,
                                                                Pageable pageable) {
        LocalDate startOfMonth = yearMonth.atDay(1);  // 해당 월의 첫날
        LocalDate endOfMonthDate = yearMonth.atEndOfMonth(); // 해당 월의 마지막 날

        Page<CalendarEventEntity> entityPage = calendarEventJpaRepository.findByCategoryAndPeriod(startOfMonth, endOfMonthDate, calendarCategory, pageable);

        // Page 객체를 변환하여 반환
        return entityPage.map(calendarEventMapper::toDomain);
    }

    @Override
    public CalendarEvent findById(Long id) {
        return calendarEventMapper.toDomain(calendarEventJpaRepository.findById(id).orElseThrow());
    }

    @Override
    public CalendarEvent save(CalendarEvent calendarEvent) {
        return calendarEventMapper.toDomain(
                calendarEventJpaRepository.save(calendarEventMapper.toEntity(calendarEvent)));
    }

    @Override
    public void delete(CalendarEvent calendarEvent) {
        calendarEventJpaRepository.delete(calendarEventMapper.toEntity(calendarEvent));
    }
}
