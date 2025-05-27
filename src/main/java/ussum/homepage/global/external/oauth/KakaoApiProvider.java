package ussum.homepage.global.external.oauth;

import io.netty.handler.codec.http.HttpHeaderValues;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ussum.homepage.application.user.service.dto.response.KakaoTokenResponseDto;
import ussum.homepage.application.user.service.dto.response.KakaoUserInfoResponseDto;
import ussum.homepage.application.user.service.dto.response.UserOAuthResponse;

@RequiredArgsConstructor
@Slf4j
@Component
public class KakaoApiProvider {
    @Value("${oauth2.kakao.client_id}")
    private String clientId;

    //TODO(상욱): 추후 기능/보안상 문제 없다면 환경 변수 삭제
    @Value("${oauth2.kakao.redirect_uri}")
    private String redirectUri;

    @Value("${oauth2.kakao.token_uri}")
    private String tokenUri;

    @Value("${oauth2.kakao.resource_uri}")
    private String resource_uri;

    @Value("${oauth2.kakao.authorize_uri}")
    private String authorize_uri;

    public String getKakaoLogin(String redirectUri){
        String authUrl = authorize_uri +
                "?client_id=" + clientId +
                "&redirect_uri="+ redirectUri +
                "&response_type=code";
        return authUrl;
    }

    public String getAccessToken(String code, String redirectUri) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build();

        System.out.println("uriComponents = " + uriComponents);

        KakaoTokenResponseDto responseDto = WebClient.create().post()
                .uri(uriComponents.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("파라미터가 옳지 않음")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("서버 에러")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .doOnError(error -> {
                    log.info("error content : {}", error.getMessage());
                })
                .block();

        log.info(" [Kakao Service] Access Token ------> {}", responseDto.getAccessToken());
        log.info(" [Kakao Service] Refresh Token ------> {}", responseDto.getRefreshToken());

        return responseDto.getAccessToken().toString();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        String urlString = resource_uri;

        KakaoUserInfoResponseDto userInfo = WebClient.create().get()
                .uri(urlString)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("파라미터가 옳지 않음")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("서버 오류")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .doOnError(error -> {
                    log.info("error content : {}", error.getMessage());
                })
                .block();
        return userInfo;
    }

}
