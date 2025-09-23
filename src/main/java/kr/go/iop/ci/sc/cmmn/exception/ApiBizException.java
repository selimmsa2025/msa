package kr.go.iop.ci.sc.cmmn.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Exception 처리
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.11.20
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.11.20    양정숙        최초 생성
 * </pre>
 */
@Getter
@Setter
public class ApiBizException extends RuntimeException {
	private static final long serialVersionUID = 3970500934169211833L;
	@Schema(description = "API 처리 결과 코드를 클라이언트에게 리턴")
	private HttpStatus status;
	@Schema(description = "API 처리 결과 메시지를 클라이언트에게 리턴")
	private String message;

	public ApiBizException() {
		this.status = HttpStatus.BAD_REQUEST;
		this.message = "처리 중 오류가 발생하였습니다.";
	}
	
	public ApiBizException(HttpStatus status) {
		this.status = status;
		this.message = "처리 중 오류가 발생하였습니다.";
	}
	
	public ApiBizException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ApiBizException(HttpStatus status, String message, Object...args) {
		this.status = status;
		this.message = MessageFormat.format(message, args);
	}
}
