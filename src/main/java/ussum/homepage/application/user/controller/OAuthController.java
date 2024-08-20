package ussum.homepage.application.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.user.service.OAuthService;
import ussum.homepage.application.user.service.dto.request.CouncilLoginRequest;
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
    public ResponseEntity<ApiResponse<?>> callback(@RequestParam("code") String code){
        return ApiResponse.success(oAuthService.signIn(code));
    }

//    @PostMapping("council-login")
//    public ResponseEntity<ApiResponse<?>> councilLogin(@RequestBody CouncilLoginRequest request){
//        return ApiResponse.success(oAuthService.councilLogin(request));
//    }

}
