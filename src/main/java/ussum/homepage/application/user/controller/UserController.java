package ussum.homepage.application.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.user.service.UserService;
import ussum.homepage.application.user.service.dto.request.TokenRequest;
import ussum.homepage.application.user.service.dto.response.UserInfoResponse;
import ussum.homepage.global.ApiResponse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
@Tag(name = "users", description = "유저 관련 api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "PASSU 정보 조회 API", description = """
            PASSU 정보 조회 API입니다. Authorization 헤더로 토큰 보내면 됩니다.
            """)
    @GetMapping("/user-info")
    public ApiResponse<UserInfoResponse> getUserInfo(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        String accessToken = authorizationHeader.replace("Bearer ", ""); // Bearer 제거
        log.info("##### userId ##### : " + accessToken);
        return ApiResponse.onSuccess(userService.getUserInfo(accessToken));
    }
}