package kr.go.iop.ci.sc.config.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * 토큰 정보 가져오기
 */
@Component
public class TokenProvider {

	@Value("${auth.token-secret}")
    private String tokenSecret;
	 /**
    private final SecretKey secretKey;

    public TokenProvider(@Value("${auth.token-secret}") String tokenSecret) {
        // HMAC-SHA 키 생성 (jjwt 0.12.x 호환)
        this.secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes());
    }

   
     * JWT에서 Claims 추출
     *
     * @param token Bearer 포함 가능
     * @return Claims
    
    public Claims getClaimsFromToken(String token) {
        String pureToken = token.replaceFirst("^Bearer\\s+", "").trim();
        
        Claims cl = getClaimsFromToken("");
        return cl;
    }
     */
    public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(tokenSecret.getBytes()).parseClaimsJws(token.replaceAll("^Bearer", "").trim()).getBody();
	}
}
