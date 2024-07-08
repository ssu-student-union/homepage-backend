package ussum.homepage.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.stereotype.Component;
import ussum.homepage.domain.user.Member;
import ussum.homepage.domain.user.User;
import ussum.homepage.global.error.exception.UnauthorizedException;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static ussum.homepage.global.error.status.ErrorStatus.*;

@Component
public class JwtTokenProvider {
    private JwtProperties properties;

    private String SECRET;
    private int ACCESS_TOKEN_EXPIRE_TIME;
    private int REFRESH_TOKEN_EXPIRE_TIME;

    public JwtTokenProvider(JwtProperties properties){
        SECRET = properties.getSecret();
        ACCESS_TOKEN_EXPIRE_TIME = properties.getAccess_token_expire_time();
        REFRESH_TOKEN_EXPIRE_TIME = properties.getRefresh_token_expire_time();
    }

    // 토큰 발급, 파라미터를 Member로 해야하나?
    public JwtTokenInfo issueToken(User user){
        return JwtTokenInfo.of(createAccessToken(user), createRefreshToken(user));
    }

    public String createAccessToken(User user){
        // 유저 정보를 담기 위해 claim 사용
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, "access_token")
                .setClaims(claims)
                .setSubject(String.valueOf(user.getKakaoId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(User user){
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, "refresh_token")
                .setClaims(claims)
                .setSubject(String.valueOf(user.getKakaoId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        String encoded = Base64.getEncoder().encodeToString(SECRET.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }

    public Long getSubject(String token) {
        return Long.valueOf(getJwtParser().parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public void validateAccessToken(String accessToken) {
        try {
            getJwtParser().parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public void validateRefreshToken(String refreshToken) {
        try {
            getJwtParser().parseClaimsJws(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_REFRESH_TOKEN_VALUE);
        }
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }
}
