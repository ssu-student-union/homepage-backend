package ussum.homepage.application.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.calendar.service.CalendarService;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarServcie;

    @GetMapping("/{q}")
    public ResponseEntity<ApiResponse<?>> getCalendarMondth(@UserId UserId userId,
                                                            @PathVariable(name = "q") String query) {
        return ApiResponse.success(calendarServcie.getCalenders(query));
    }

}

