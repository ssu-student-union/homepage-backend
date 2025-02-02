package ussum.homepage.domain.calender.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.calender.CalenderEvent;
import ussum.homepage.domain.calender.CalenderEventRepository;

@Service
@RequiredArgsConstructor
public class CalenderEventReader {
    private final CalenderEventRepository calenderEventRepository;

    public List<CalenderEvent> getCalenderEventsByYearMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return calenderEventRepository.findByStartDateBeforeAndEndDateAfter(yearMonth);
    }
}
