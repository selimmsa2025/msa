package kr.go.iop.ci.sc.cmmn.vo;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
 * 2023.11.20    양정숙        최초 생성
 * </pre>
 */
public class ApiResponseVO extends ResponseEntity<ResponseVO> implements Serializable {
	private static final long serialVersionUID = 7538710751890386400L;

	public ApiResponseVO() {
		super(HttpStatus.OK);
	}
	
	public ApiResponseVO(HttpStatus status) {
		super(status);
	}
	
	public ApiResponseVO(ResponseVO vo) {
		super(vo, vo.getStatus());
	}
}
