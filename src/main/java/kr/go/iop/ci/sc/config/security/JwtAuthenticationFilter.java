package kr.go.iop.ci.sc.config.security;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ResponseVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasLength;

/**
 * Spring Security AuthenticationFilter 처리. 토큰 정보를 받아 인증 정보 생성
 *
 * @author MSA팀
 * @version 1.0
 * @since 2024/01/12
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *    수정일        수정자            수정내용
 *  ----------    --------    ---------------------------
 *  2024.01.12    양정숙        MSA팀 적용
 * </pre>
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	public JwtAuthenticationFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	/**
	 * 로그인 요청 뿐만 아니라 모든 요청시마다 호출된다.
	 * 토큰에 담긴 정보로 Authentication 정보를 설정한다.
	 * 이 처리를 하지 않으면 AnonymousAuthenticationToken 으로 처리된다.
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
		try {
			log.debug("JwtAuthenticationFilter");
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);

			if (!hasLength(token) || "undefined".equals(token)) {
				super.doFilter(request, response, filterChain);
				
			} else {
				Claims claims = tokenProvider.getClaimsFromToken(token);
				
				String username = claims.getSubject();
				Map<?, ?> userInfo = claims.get(ConstantInfo.TOKEN_USER_INFO, Map.class);

				log.debug("username[{}]", username);
				log.debug("userInfo[{}]", userInfo);

				if (username == null) {
					// refresh token 에는 subject, authorities 정보가 없다.
					SecurityContextHolder.getContext().setAuthentication(null);
				} else {
					List<SimpleGrantedAuthority> roleList = null;
					
					if(claims.get(ConstantInfo.TOKEN_CLAIM_NAME) != null && claims.get(ConstantInfo.TOKEN_CLAIM_NAME) != "") {
						roleList = Arrays.stream(claims.get(ConstantInfo.TOKEN_CLAIM_NAME, String.class).split(","))
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList());
					}

					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, null, roleList));
				}

				filterChain.doFilter(request, response);
			}
		} catch (ServletException | IOException | ApiBizException e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			log.error("AuthenticationFilter doFilter error", e);
		} catch (ClaimJwtException e) {
			ResponseUtils.setResponse((HttpServletResponse) response, new ResponseVO(HttpStatus.UNAUTHORIZED), "토큰이 유효하지 않습니다.");
			throw new ApiBizException();
		}
	}

}
