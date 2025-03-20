package ussum.homepage.application.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.user.service.OnBoardingService;
import ussum.homepage.application.user.service.dto.request.OnBoardingEmailRequest;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;
import ussum.homepage.global.config.custom.RateLimit;

@RequiredArgsConstructor
@RequestMapping("/onboarding")
@RestController
@RateLimit(capacity = 10, refillTokens = 10, refillDuration = 1)  // 1분에 10개 요청 제한
@Tag(name = "on-boarding", description = "온보딩 api, 온보딩 실패 시 메일로 문의하는 api")
public class OnBoardingController {
    private final OnBoardingService onBoardingService;

    @Operation(summary = "카카오 로그인 후 온보딩 api", description = """
            카카오로그인 api 반환값 중 isFirst 필드의 값이 true일 때 사용하는 api입니다. 즉, 온보딩을 해야하는 유저가 사용하는 api입니다.
            기본적으로 카카오로그인 api 호출 후 반환된 액세스 토큰값을 필요로 합니다.
            이름, 학번, 대학, 학과로 이루어진 dto를 파라미터로 전달해주시면 됩니다.
            성공적으로 저장된다면 200ok가 반환되고 반환값은 없습니다.
            """)
    @PostMapping("/academy-information")
    public ResponseEntity<ApiResponse<?>> createUserOnBoarding(@Parameter(hidden = true) @UserId Long userId,
                                                               @RequestBody OnBoardingRequest request) {
        onBoardingService.saveUserOnBoarding(userId, request);
        return ApiResponse.success(null);
    }

    @Operation(summary = "온보딩 실패 시 메일로 문의하는 api", description = """
            온보딩 실패 시 메일로 문의하는 api 입니다.
            """)
    @PostMapping("/mail")
    public ResponseEntity<ApiResponse<?>> sendEmail(@RequestBody OnBoardingEmailRequest onBoardingEmailRequest) {
        onBoardingService.sendEmail(onBoardingEmailRequest);
        return ApiResponse.success(null);
    }

}
