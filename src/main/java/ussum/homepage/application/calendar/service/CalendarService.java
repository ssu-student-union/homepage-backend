package ussum.homepage.application.calendar.service;

import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.calendar.service.dto.CalenderEventResponse;
import ussum.homepage.domain.calender.service.CalenderEventReader;
import ussum.homepage.infra.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalenderEventReader calenderReader;

    public List<?> getCalenders(String query) {
        YearMonth yearMonth = DateUtils.parseYearMonthFromString(query);
        List<? extends CalenderEventResponse> calenderResponseList = calenderReader.getCalenderEventsByYearMonth(yearMonth).stream()
                .map(CalenderEventResponse::of)
                .toList();

        return calenderResponseList;
    }
}
