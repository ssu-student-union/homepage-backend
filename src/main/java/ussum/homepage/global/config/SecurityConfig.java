package ussum.homepage.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ussum.homepage.global.config.auth.ExceptionHandlerFilter;
import ussum.homepage.global.jwt.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    private final CorsConfig corsConfig;
//    private final JwtTokenProvider provider;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    private static final String[] getWhiteList = {
//            "/board/{boardCode}/posts",
//            "/board/{boardCode}/posts/{postId}",
//            "/board/posts/{postId}/comments",
//            "/board/{boardCode}/{groupCode}/{memberCode}/posts",
//            "/board/data/posts",
//            "/board/{boardCode}/posts/top-liked",
//            "/board/{boardCode}/posts/search",
//            "/board/data/posts/search"
//    };
//
//    private static final String[] whiteList = {
//            "/auth/**",
//            "/swagger-ui/**",
//            "/swagger-resources/**",
//            "/v3/api-docs/**",
//            "/admin/**",
//            "/onboarding/mail"
//    };
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .requestMatchers(HttpMethod.GET, getWhiteList)
//                .requestMatchers(whiteList);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(sessionManagementConfigurer ->
//                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(exceptionHandlingConfigurer ->
//                        exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
//                .exceptionHandling(exceptionHandlingConfigurer ->
//                        exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler))
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        authorizationManagerRequestMatcherRegistry.anyRequest().authenticated()) // 일단 추가
//                .addFilter(corsConfig.corsFilter())
//                .addFilterBefore(new JwtFilter(provider), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new ExceptionHandlerFilter(), JwtFilter.class)
//                .build();
//    }
private final CorsConfig corsConfig;
    private final JwtTokenProvider provider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private static final String[] getWhiteList = {
            "/board/{boardCode}/posts/top-liked"
    };

    private static final String[] bothWhiteList = {
            "/board/캘린더",
            "/board/{boardCode}/posts/{postId}", //단건 조회
            "/board/{boardCode}/posts", //전체 조회
            "/board/data/posts", //자료집 조회
            "/board/posts/{postId}/comments", //댓글 대댓글 조회
            "/board/{boardCode}/posts/search", //검색
            "/board/data/posts/search" //자료집 검색
    };

    private static final String[] whiteList = {
            "/health", // Health Check 전용
            "/auth/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/admin/**",
            "/onboarding/mail",
            "/actuator",
            "/actuator/prometheus",
            "/users/user-info", // passu 정보 조회
            "/users/discord", // 디스코드 봇 사용자 수 조회
    };
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //Security Filter Chain을 거치지 않음
        return web -> web.ignoring()
                .requestMatchers(HttpMethod.GET, getWhiteList)
                .requestMatchers(whiteList);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.GET, bothWhiteList).permitAll() // botWhiteList에 포함된 경로에 대해서만 토큰 없어도 혹은 있어도 접근 허용
                                .anyRequest().authenticated()) // 나머지 요청은 인증 필요
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtFilter(provider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtFilter.class)
                .build();
    }

}
