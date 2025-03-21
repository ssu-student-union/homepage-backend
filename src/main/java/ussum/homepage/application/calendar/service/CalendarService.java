package ussum.homepage.application.calendar.service;

import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventUpdateRequest;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventCreateResponse;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventDetailResponse;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventList;
import ussum.homepage.application.calendar.service.dto.response.CalendarEventResponse;
import ussum.homepage.domain.calender.CalendarEvent;
import ussum.homepage.domain.calender.service.CalendarEventAppender;
import ussum.homepage.domain.calender.service.CalendarEventModifier;
import ussum.homepage.domain.calender.service.CalendarEventReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.calendar_event.entity.CalendarCategory;
import ussum.homepage.infra.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final MemberReader memberReader;
    private final UserReader userReader;
    private final CalendarEventReader calenderReader;
    private final CalendarEventAppender calendarEventAppender;
    private final CalendarEventModifier calendarEventModifier;
    private final BoardReader boardReader;
    private final CalendarEventReader calendarEventReader;

    @Transactional
    public CalendarEventList<?> getCalenders(Long userId, String query, String category, String boardCode, int page, int take) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Pageable pageable = PageInfo.of(page,take);

        Page<CalendarEvent> calendarEventList = null;

        if (!board.getName().equals(boardCode)) {
            throw new InvalidValueException(ErrorStatus.INVALID_BOARDCODE);
        }

        YearMonth yearMonth = DateUtils.parseYearMonthFromString(query);
        List<CalendarEventResponse> calendarEventResponseList = null;

        if (category == null) {
            calendarEventList = calendarEventReader.getCalenderEventsByYearMonthWithoutCategory(yearMonth,pageable);
        }else{
            CalendarCategory calendarCategory = CalendarCategory.getEnumCategoryCodeFromStringCategoryCode(category);
            calendarEventList = calendarEventReader.getCalenderEventsByYearMonthWithCategory(yearMonth,calendarCategory,pageable);
        }

        calendarEventResponseList = calendarEventList.stream()
                .map(CalendarEventResponse::of)
                .toList();

        PageInfo pageInfo = PageInfo.of(calendarEventList);
        return CalendarEventList.of(calendarEventResponseList, pageInfo);
    }

    @Transactional
    public CalendarEventDetailResponse getCalendarEvent(Long userId, Long eventId) {
        CalendarEvent calendarEvent = calendarEventReader.getCalendarEventByCalendarEventId(eventId);
        boolean isAuthor = calendarEvent.getCreateBy().equals(userId);

        return CalendarEventDetailResponse.of(calendarEvent, isAuthor);
    }

    @Transactional
    public CalendarEventCreateResponse createCalendarSchedule(Long userId, CalendarEventRequest requestDto) {
        User user = userReader.getUserWithId(userId);
        List<Member> member = memberReader.getMembersWithUserId(userId);
        if (member.stream().noneMatch(m -> m.getGroupId()==2 || m.getGroupId()==3)){
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        CalendarEvent calendarEvent = requestDto.toDomain(userId);
        calendarEvent = calendarEventAppender.create(calendarEvent);
        return CalendarEventCreateResponse.of(calendarEvent.getId(), calendarEvent.getCalendarCategory());
    }

    @Transactional
    public Long updateCalendarEvent(Long userId, Long calendarEventId, CalendarEventUpdateRequest requestDto) {
        CalendarEvent calendarEvent = calendarEventReader.getCalendarEventByCalendarEventId(calendarEventId);
        CalendarEvent newCalendarEvent = calendarEventModifier.updateCalendarEvent(requestDto.toDomain(calendarEvent));
        return newCalendarEvent.getId();
    }
    @Transactional
    public void deleteCalendarEvent(String boardCode,Long calendarEventId) {
        calendarEventModifier.deleteCalendarEvent(boardCode, calendarEventId);
    }
}
