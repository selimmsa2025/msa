package kr.go.iop.ci.sc.cmmn.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cmmn.utils.CurrentUserUtils;
import kr.go.iop.ci.sc.cmmn.vo.UserVO;
import lombok.RequiredArgsConstructor;

/**
 * 공통서비스 controller
 * 전체 백엔드 서비스 영역에 공통 적용 클래스
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.11.22
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.11.22    양정숙        최초 생성
 * </pre>
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "공통 기능", description = "서비스 공통 기능 API")
public class CmmnController {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${server.port}")
	private String serverPort;
	
	@Value("${health.message}")
	private String healthMessage;
	
	/**
	 * health check
	 * @return
	 */
	@GetMapping("/actuator/health-info")
	@Operation(summary = "서비스 상태 확인", description = "서비스 상태 확인")
	public String getStatus() {
		return String.format("%s on"
				+ "\n local.server.port : %s"
				+ "\n health.message : %s" , applicationName, serverPort, healthMessage);
	}

	/**
	 * 로그인한 사용자 정보 조회
	 * @return
	 * @throws JsonProcessingException 
	 */
	@GetMapping("/v1/cmmn/user-info")
	@Operation(summary = "로그인한 사용자", description = "현재 로그인한 사용자 정보 확인")
	public String getCurrentUserInfo() throws JsonProcessingException {
		UserVO userInfo = CurrentUserUtils.getCurrentUser();
		return new ObjectMapper().writeValueAsString(userInfo);
	}
}
