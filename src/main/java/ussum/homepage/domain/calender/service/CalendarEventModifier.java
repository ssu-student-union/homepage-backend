package ussum.homepage.domain.calender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.post.entity.Category;

@Service
@RequiredArgsConstructor
public class CalendarEventModifier {
    private final CalenderEventRepository calenderEventRepository;
    private final CalendarEventReader calendarEventReader;
    private final BoardReader boardReader;

    public CalendarEvent updateCalendarEvent(CalendarEvent calendarEvent) {
        return calenderEventRepository.save(calendarEvent);
    }

    public void deleteCalendarEvent(String boardCode, Long calendarEventId) {
        calenderEventRepository.delete(calendarEventReader.getCalendarEventByCalendarEventId(calendarEventId));
    }
}
