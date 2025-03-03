package ussum.homepage.application.calendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.calendar.service.CalendarService;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventUpdateRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;
import lombok.extern.slf4j.Slf4j;
import ussum.homepage.global.error.status.ErrorStatus;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(description = """
            일정 조회하는 API입니다. form-data 년도월, 그리고 queryParam 형식으로 calendarCategory(필터링)
            그리고 글쓰기 권한이 allowedAuthorities이나 deniedAuthorities에 담김.""")
    @GetMapping("/{boardCode}")
    public ResponseEntity<ApiResponse<?>> getCalendarMonth(@Parameter(hidden = true) @UserId Long userId,
                                                           @PathVariable(name = "boardCode") String boardCode,
                                                           @RequestParam(value = "date") String query,
                                                           @RequestParam(value = "calendarCategory",required = false) String calendarCategory) {
        return ApiResponse.success(calendarService.getCalenders(userId,query, calendarCategory,boardCode));
    }

    @GetMapping("/{boardCode}/{calendarEventId}")
    public ResponseEntity<ApiResponse<?>> getCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                           @PathVariable(name = "boardCode") String boardCode,
                                                           @PathVariable(name = "calendarEventId") Long calendarEventId){
        return ApiResponse.success(calendarService.getCalendarEvent(userId,calendarEventId));
    }

    @PostMapping("/{boardCode}")
    public ResponseEntity<ApiResponse<?>> createCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @RequestBody CalendarEventRequest calendarEventRequest) {
        return ApiResponse.success(calendarService.createCalendarSchedule(userId, calendarEventRequest));
    }

    @PatchMapping("/{boardCode}/{calendarEventId}")
    public ResponseEntity<ApiResponse<?>> editCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                            @PathVariable(name = "boardCode") String boardCode,
                                                            @PathVariable(name = "calendarEventId") Long calendarEventId,
                                                            @RequestBody CalendarEventUpdateRequest calendarEventUpdateRequest) {
        return ApiResponse.success(calendarService.updateCalendarEvent(userId, calendarEventId, calendarEventUpdateRequest));
    }

    @DeleteMapping("/{boardCode}/{calendarEventId}")
    public ResponseEntity<ApiResponse<?>> deleteCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @PathVariable(name = "calendarEventId") Long calendarEventId){
        calendarService.deleteCalendarEvent(boardCode,calendarEventId);
        return ApiResponse.success(null);
    }
}
