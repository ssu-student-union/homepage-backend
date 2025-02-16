package ussum.homepage.application.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.calendar.service.CalendarService;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarServcie;

    @Operation(description = """
            일정 조회하는 API입니다. form-data 년도월, 그리고 queryParam 형식으로 calendarCategory(필터링)
            그리고 글쓰기 권한이 allowedAuthorities이나 deniedAuthorities에 담김.""")
    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getCalendarMonth(@UserId Long userId,
                                                           @RequestParam(value = "date") String query,
                                                           @RequestParam(value = "calendarCategory") String calendarCategory) {
        return ApiResponse.success(calendarServcie.getCalenders(query, calendarCategory));
    }

    @PostMapping("/{date}")
    public ResponseEntity<ApiResponse<?>> createCalendarEvent(@UserId Long userId,
                                                                 @RequestBody CalendarEventRequest calendarEventRequest){
        return ApiResponse.success(calendarServcie.createCalendarSchedule(userId, calendarEventRequest));
    }
}
