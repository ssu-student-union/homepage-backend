package ussum.homepage.application.calendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.calendar.service.CalendarService;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequestDto;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;
import lombok.extern.slf4j.Slf4j;
import ussum.homepage.global.error.status.ErrorStatus;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(description = """
            일정 조회하는 API입니다. form-data 년도월, 그리고 queryParam 형식으로 calendarCategory(필터링)
            그리고 글쓰기 권한이 allowedAuthorities이나 deniedAuthorities에 담김.""")
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getCalendarMonth(@UserId Long userId,
                                                           @RequestParam(value = "date") String query,
                                                           @RequestParam(value = "calendarCategory",required = false) String calendarCategory) {
        return ApiResponse.success(calendarService.getCalenders(query, calendarCategory));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> createCalendarEvent(@UserId Long userId,
                                                              @RequestBody CalendarEventRequestDto calendarEventRequestDto) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();

//        String body = request.getReader().lines().collect(Collectors.joining());
//        CalendarEventRequest calendarEventRequest = objectMapper.readValue(body, CalendarEventRequest.class);
//
        if (calendarEventRequestDto != null) {
            return ApiResponse.success(calendarEventRequestDto);
        }

        return ApiResponse.success(calendarService.createCalendarSchedule(userId, calendarEventRequestDto));
    }
}
