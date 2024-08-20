package ussum.homepage.application.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.user.service.OnBoardingService;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping("/onboarding")
@RestController
public class OnBoardingController {
    private final OnBoardingService onBoardingService;

    @PostMapping("/academy-information")
    public ResponseEntity<ApiResponse<?>> createUserOnBoarding(@UserId Long userId,
                                                              @RequestBody OnBoardingRequest request){
        onBoardingService.saveUserOnBoarding(userId, request);
        return ApiResponse.success(null);
    }

}
