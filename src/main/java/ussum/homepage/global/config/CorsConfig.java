package ussum.homepage.global.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://sssupport.shop");
        config.addAllowedOrigin("https://ssu-student-union.github.io");
        config.addAllowedOrigin("https://homepage-frontend-dun.vercel.app");
        config.addAllowedOrigin("https://homepage-frontend-psi.vercel.app");
        config.addAllowedOrigin("https://stu.ssu.ac.kr");
        config.addAllowedOrigin("https:/dev-homepage-frontend.vercel.app");
        config.addAllowedOrigin("https://dev.sssupport.shop");
        config.addAllowedOrigin("https://backend.sssupport.shop");
        config.addAllowedOrigin("https://dev-backend.sssupport.shop");
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }

}
