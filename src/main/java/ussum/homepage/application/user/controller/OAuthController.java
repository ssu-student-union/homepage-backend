package ussum.homepage.application.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ussum.homepage.application.user.service.OAuthService;
import ussum.homepage.application.user.service.dto.response.UserOAuthResponse;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/page")
    public void LoginPage(HttpServletResponse response) throws Exception{
        String redirectUri= oAuthService.getKakaoLogin();
        response.sendRedirect(redirectUri);
    }

    @GetMapping("/callback")
    public ApiResponse<UserOAuthResponse> callback(@RequestParam("code") String code){
        String accessToken = oAuthService.getAccessToken(code);
        UserOAuthResponse response = oAuthService.getUserInfo(accessToken);
        return ApiResponse.onSuccess(response);
    }

}