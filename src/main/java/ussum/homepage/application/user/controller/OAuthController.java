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
@RequestMapping("/auth")
@RestController
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/oauth")
    public void LoginPage(HttpServletResponse response) throws Exception{
        response.sendRedirect(oAuthService.getKakaoLogin());
    }

    @GetMapping("/callback")
    public ApiResponse<UserOAuthResponse> callback(@RequestParam("code") String code){
        return ApiResponse.onSuccess(oAuthService.signIn(code));
    }

}
