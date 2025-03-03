package ussum.homepage.application.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.calendar.service.CalendarService;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventRequest;
import ussum.homepage.application.calendar.service.dto.request.CalendarEventUpdateRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(description = """
            특정 달의 일정 조회하는 API입니다. boardCode에는 캘린더를 넣어서 보내면 됩니다. 그리고 form-data 년도월(YYYYMM),
            queryParam 형식으로 calendarCategory(필터링)에 카테고리를 담아 요청하시면 됩니다.
            그리고 일정 생성 권한이 allowedAuthorities이나 deniedAuthorities에 담김.""")
    @GetMapping("/{boardCode}")
    public ResponseEntity<ApiResponse<?>> getCalendarMonth(@Parameter(hidden = true) @UserId Long userId,
                                                           @PathVariable(name = "boardCode") String boardCode,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "take") int take,
                                                           @RequestParam(value = "date") String query,
                                                           @RequestParam(value = "calendarCategory",required = false) String calendarCategory) {
        return ApiResponse.success(calendarService.getCalenders(userId,query, calendarCategory,boardCode, page, take));
    }

    @Operation(description = """
            일정 단건 조회하는 API입니다. calendarEventId에 조회하고 싶은 일정의 Id를 요청으로 넣어서 보내면 됩니다.
            수정, 삭제에 경우 응답으로 내려가는 isAuthor 필드가 false 이면 막아야 합니다.""")
    @GetMapping("/{boardCode}/{calendarEventId}")
    public ResponseEntity<ApiResponse<?>> getCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                           @PathVariable(name = "boardCode") String boardCode,
                                                           @PathVariable(name = "calendarEventId") Long calendarEventId){
        return ApiResponse.success(calendarService.getCalendarEvent(userId,calendarEventId));
    }

    @Operation(description = """
             캘린더 일정 생성 API. body에 json 형식으로 CalendarEventRequest를 담아서 보내주시면 됩니다.
             카테고리, 시작일, 끝일, 제목까지 null값 없이 요청에 담아 보내주셔야 합니다. 총학생회 계정이나 아지위 계정이 아니라면 403번 에러가 내려갑니다.
             만약 일정을 성공적으로 생성했다면 생성된 게시물의 id와 title을 담은 객체가 응답됩니다.""")
    @PostMapping("/{boardCode}")
    public ResponseEntity<ApiResponse<?>> createCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @RequestBody CalendarEventRequest calendarEventRequest) {
        return ApiResponse.success(calendarService.createCalendarSchedule(userId, calendarEventRequest));
    }

    @Operation(description = """
            일정 수정하는 API입니다.
            """)
    @PatchMapping("/{boardCode}/{calendarEventId}")
    public ResponseEntity<ApiResponse<?>> editCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                            @PathVariable(name = "boardCode") String boardCode,
                                                            @PathVariable(name = "calendarEventId") Long calendarEventId,
                                                            @RequestBody CalendarEventUpdateRequest calendarEventUpdateRequest) {
        return ApiResponse.success(calendarService.updateCalendarEvent(userId, calendarEventId, calendarEventUpdateRequest));
    }

    @Operation(description = """
            일정 삭제하는 API입니다.
            """)
    @DeleteMapping("/{boardCode}/{calendarEventId}")
    public ResponseEntity<ApiResponse<?>> deleteCalendarEvent(@Parameter(hidden = true) @UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @PathVariable(name = "calendarEventId") Long calendarEventId){
        calendarService.deleteCalendarEvent(boardCode,calendarEventId);
        return ApiResponse.success(null);
    }
}
