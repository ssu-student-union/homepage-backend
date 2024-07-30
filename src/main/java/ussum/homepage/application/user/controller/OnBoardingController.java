package ussum.homepage.application.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.user.service.OnBoardingService;
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
    public ApiResponse<?> createUserOnBoarding(@UserId Long userId,
                                               @RequestBody OnBoardingRequest request){
        onBoardingService.getUserOnBoarding(userId, request);
        return ApiResponse.onSuccess(null);
    }
}
