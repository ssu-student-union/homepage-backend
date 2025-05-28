package ussum.homepage.application.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.user.service.OAuthService;
import ussum.homepage.application.user.service.dto.request.CouncilLoginRequest;
import ussum.homepage.global.ApiResponse;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/oauth")
    public void LoginPage(HttpServletResponse response, HttpServletRequest request) throws Exception{
        response.sendRedirect(oAuthService.getKakaoLogin(request));
    }

    @GetMapping("/callback")
    public ResponseEntity<ApiResponse<?>> callback(@RequestParam("code") String code, HttpServletRequest request){
        return ApiResponse.success(oAuthService.signIn(code, request.getHeader("origin")));
    }

    @Operation(summary = "학생자치기구 로그인 api", description = """
            학생자치기구 로그인을 위한 api입니다.
            id와 password를 Json형식으로 보내주시면 됩니다.
            db에 미리 저장된 학생자치기구 계정을 기준으로 id와 비밀번호가 일치하는지 판단하고, 일치하지 않을 시, 각각에 따른 예외가 발생합니다. 
            반환값으로 액세스토큰, 로그인한 계정의 그룹, 멤버 코드가 반환됩니다.
            """)
    @PostMapping("council-login")
    public ResponseEntity<ApiResponse<?>> councilLogin(@RequestBody CouncilLoginRequest request){
        return ApiResponse.success(oAuthService.councilLogin(request));
    }

}
