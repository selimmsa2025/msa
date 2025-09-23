package kr.go.iop.ci.sc.cmmn.utils;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.go.iop.ci.sc.cmmn.vo.UserVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;

/**
 * 로그인한 사용자 정보 조회
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.11.28
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.11.28    양정숙        최초 생성
 * </pre>
 */
@Component
public class CurrentUserUtils {

	private CurrentUserUtils() {
	}

	/**
	 * 현재 로그인한 사용자 정보 가져오기
	 * @return
	 */
	public static UserVO getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		
		if(principal.toString().equals("anonymousUser") || principal.toString().equals("anonymous")) {
			return null;
		}
		
		return (new ObjectMapper()).convertValue(principal, UserVO.class);
	}

	/**
	 * 권한 코드가 포함되어 있는지 확인
	 * @param vo
	 * @param authCd
	 * @return
	 */
	public static boolean checkUserAuth(UserVO vo, String authCd) {
		if(vo == null || vo.getUserAuthrtList() == null) {
			return false;
		}
		
		return Arrays.stream(vo.getUserAuthrtList()).anyMatch(authCd::equals);
	}
	
	/**
	 * 로그인 IP 가져오기
	 * @param req
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || ConstantInfo.UNKNOWN_STRING.equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || ConstantInfo.UNKNOWN_STRING.equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || ConstantInfo.UNKNOWN_STRING.equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		
		if (ip == null || ip.length() == 0 || ConstantInfo.UNKNOWN_STRING.equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		
		if (ip == null || ip.length() == 0 || ConstantInfo.UNKNOWN_STRING.equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}

		return ip;
	}

}
