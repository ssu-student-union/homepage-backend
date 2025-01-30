package ussum.homepage.infra.jpa.calendar_event.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarEventEntity;

public interface CalendarEventJpaRepository extends JpaRepository<CalendarEventEntity,Long> {
}
