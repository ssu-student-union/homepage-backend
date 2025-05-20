package ussum.homepage.application.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.user.service.UserService;
import ussum.homepage.application.user.service.dto.request.MyPageUpdateRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

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
    public ResponseEntity<ApiResponse<?>> getUserInfo(
        @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", ""); // Bearer 제거
        log.info("##### userId ##### : " + accessToken);
        return ApiResponse.success(userService.getUserInfo(accessToken));
    }

    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<?>> getMypage(@UserId Long userId) {
        return ApiResponse.success(userService.getMyPageInfo(userId));
    }

    @PatchMapping("/mypage")
    public ResponseEntity<ApiResponse<?>> updateMypage(@UserId Long userId,
        @RequestBody MyPageUpdateRequest myPageUpdateRequest) {
        return ApiResponse.success(userService.updateMyPageInfo(userId, myPageUpdateRequest));
    }

    @Operation(summary = "회원 탈퇴 api", description = """
        회원 탈퇴 api입니다. 개발을 수월하게 하기 위해 만든 api로 사용에 유의해야합니다.
        회원의 글, 댓글, 반응 모두 삭제하고 유저정보를 삭제합니다.
        """)
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteUser(@Parameter(hidden = true) @UserId Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.success(null);
    }
}