package ussum.homepage.infra.jpa.calendar_event.respository;

import java.time.LocalDate;
import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarEventEntity;

public interface CalendarEventJpaRepository extends JpaRepository<CalendarEventEntity,Long> {
    @Query("SELECT c FROM CalendarEventEntity c " +
            "WHERE (FUNCTION('DATE', c.startDate) <= :endOfMonth) " +
            "AND (FUNCTION('DATE', c.endDate) >= :startOfMonth)")
    List<CalendarEventEntity> findEventsOverlappingWithPeriod(
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth);
}
