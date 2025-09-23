package kr.go.iop.ci.sc.cmmn.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.cmmn.vo.ResponseVO;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Http Response 처리
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
 * 2023.11.13    양정숙        최초 생성
 * </pre>
 */
@Component
public class ResponseUtils {

	private ResponseUtils() {
	}

	/**
	 * 응답 메시지 처리
	 * @param response
	 * @param vo
	 * @throws IOException
	 */
	public static void setResponse(HttpServletResponse response, ResponseVO vo) throws IOException {
		setResponse(response, vo, null);
	}
	
	/**
	 * 응답 메시지 처리
	 * @param response
	 * @param vo
	 * @param data
	 * @throws IOException
	 */
	public static void setResponse(HttpServletResponse response, ResponseVO vo, Object data) throws IOException {
		response.setStatus(vo.getStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		
		if(vo.getResultData() == null) {
			vo.setResultData(data);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().print(objectMapper.writeValueAsString(vo));
	}
	
	/**
	 * API 반환값 처리
	 * @param data
	 * @return
	 */
	public static ApiResponseVO build(Object data) {
		ResponseVO responseVO = new ResponseVO(HttpStatus.OK);
		responseVO.setResultData(data);
		return build(responseVO);
	}
	
	/**
	 * API 반환값 처리
	 * @param data
	 * @return
	 */
	public static ApiResponseVO build(HttpStatus status, Object data) {
		ResponseVO responseVO = new ResponseVO(status);
		responseVO.setResultData(data);
		return build(responseVO);
	}
	
	/**
	 * API 반환값 처리
	 * @param vo
	 * @param data
	 * @return
	 */
	public static ApiResponseVO build(ResponseVO vo) {
		return new ApiResponseVO(vo);
	}
	
	
	public static ApiResponseVO build(HttpStatus status, Object data, String resultMsg) {
		ResponseVO responseVO = new ResponseVO(status);
		responseVO.setResultData(data);
		responseVO.setResultCd(String.valueOf(status.value()));
		responseVO.setResultMsg(resultMsg);
		return build(responseVO);
	}
}
