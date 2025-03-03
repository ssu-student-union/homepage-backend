package ussum.homepage.infra.jpa.calendar_event.respository;

import java.time.LocalDate;
import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarEventEntity;

public interface CalendarEventJpaRepository extends JpaRepository<CalendarEventEntity,Long> {
    @Query("SELECT c FROM CalendarEventEntity c " +
            "WHERE (FUNCTION('DATE', c.startDate) <= :endOfMonth) " +
            "AND (FUNCTION('DATE', c.endDate) >= :startOfMonth)" +
            "ORDER BY c.startDate ASC , c.calendarCategory")
    Page<CalendarEventEntity> findEventsOverlappingWithPeriod(
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth,
            Pageable pageable);

    @Query("SELECT c FROM CalendarEventEntity c " +
            "WHERE (FUNCTION('DATE', c.startDate) <= :endOfMonth) " +
            "AND (FUNCTION('DATE', c.endDate) >= :startOfMonth) " +
            "AND c.calendarCategory = :category")  // 카테고리 필터 추가
    Page<CalendarEventEntity> findByCategoryAndPeriod(
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth,
            @Param("category") CalendarCategory category,
            Pageable pageable);
}
