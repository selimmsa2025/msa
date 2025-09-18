package kr.go.iop.ci.sc.cmmn.vo;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 응답메시지 VO
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
@Getter
@Setter
public class ResponseVO {
	@Schema(description = "HttpStatus")
	private HttpStatus status;
	@Schema(description = "결과코드")
	private String resultCd;
	@Schema(description = "결과메시지")
	private String resultMsg;
	@Schema(description = "결과값")
	private Object resultData;

	public ResponseVO(HttpStatus status) {
		this.status = status;
	}
	
	public ResponseVO(HttpStatus status, String resultCd) {
		this.status = status;
		this.resultCd = resultCd;
	}
	
	/**
	 * 역직렬화를 위한 기본 생성자
	 */
	@SuppressWarnings("unused")
	private ResponseVO() {
		
	}
}
