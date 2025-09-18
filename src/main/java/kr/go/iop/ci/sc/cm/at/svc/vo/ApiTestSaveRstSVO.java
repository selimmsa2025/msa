/*
 *  Copyright (c) 2025 Intelligent On-nara BPS Platform.
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.at.svc.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.iop.ci.sc.cm.at.mapper.vo.PrdctApiCmncRsltDVO;
import lombok.Getter;
import lombok.Setter;

/**
 * API 테스트 저장 관련 SVO
 * @name_ko API 테스트 저장 관련 SVO
 * @author hyy
 */
@Schema(description = "API 테스트 저장 관련 SVO")
@Getter
@Setter
public class ApiTestSaveRstSVO implements Serializable {

	private static final long serialVersionUID = -2996536183214870590L;
	
	@NotBlank(message = "SAAS제품아이디는 필수입니다.")
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@NotBlank(message = "서버구분코드는 필수입니다.")
	@Schema(description = "서버구분코드")
	private String srvrSeCd;
	
	@Positive(message = "API버전일련번호가 유효하지 않습니다.")
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;

	@Schema(description = "API 수정 목록")
	private List<PrdctApiCmncRsltDVO> updateList;

	@Schema(description = "API 추가 목록")
	private List<PrdctApiCmncRsltDVO> insertList;
}
