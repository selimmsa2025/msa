package kr.go.iop.ci.sc.cmmn.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.cmmn.vo.ResponseVO;

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
@RestControllerAdvice
public class ApiBizExceptionHandler {
	@ExceptionHandler(ApiBizException.class)
	public ApiResponseVO handlerBaseException(ApiBizException e) {
		ResponseVO vo = new ResponseVO(e.getStatus());
		vo.setResultMsg(e.getMessage());
		
		return ResponseUtils.build(vo);
	}
}
