package ussum.homepage.domain.calender.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;

@Service
@RequiredArgsConstructor
public class CalendarEventAppender {
    private final CalenderEventRepository calenderEventRepository;

    @Transactional
    public CalendarEvent create(CalendarEvent calendarEvent) {
        return calenderEventRepository.save(calendarEvent);
    }
}
