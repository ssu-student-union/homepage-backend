package ussum.homepage.application.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.user.service.OnBoardingService;
import ussum.homepage.application.user.service.dto.request.OnBoardingEmailRequest;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.application.user.service.dto.request.UserRequest;
import ussum.homepage.application.user.service.dto.response.UserResponse;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping("/onboarding")
@RestController
public class OnBoardingController {
    private final OnBoardingService onBoardingService;

    @PostMapping("/academy-information")
    public ResponseEntity<ApiResponse<?>> createUserOnBoarding(@Parameter(hidden = true) @UserId Long userId,
                                                               @RequestBody OnBoardingRequest request) {
        onBoardingService.saveUserOnBoarding(userId, request);
        return ApiResponse.success(null);
    }

    @PostMapping("/mail")
    public ResponseEntity<ApiResponse<?>> sendEmail(@Parameter(hidden = true) @UserId Long userId,
                                                    @RequestBody OnBoardingEmailRequest onBoardingEmailRequest) {
        onBoardingService.sendEmail(onBoardingEmailRequest);
        return ApiResponse.success(null);
    }

}
