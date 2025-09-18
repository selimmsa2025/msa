package kr.go.iop.ci.sc.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 토큰 정보 가져오기
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.11.13
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.11.27    양정숙        최초 생성
 * </pre>
 */
@Component
public class TokenProvider {

    @Value("${auth.token-secret}")
    private String tokenSecret;

	/**
	 * AuthenticationFilter.doFilter 메소드에서
	 * UsernamePasswordAuthenticationToken 정보를 세팅할 때 호출된다.
	 *
	 * @param token
	 * @return
	 */
	public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(tokenSecret.getBytes()).parseClaimsJws(token.replaceAll("^Bearer", "").trim()).getBody();
	}
}
