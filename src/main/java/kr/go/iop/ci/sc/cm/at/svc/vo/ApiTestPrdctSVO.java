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

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 등록 테스트 상품 정보 요청 VO
 * @name_ko 등록 테스트 상품 정보 요청 VO
 * @author hyy
 */
@Schema(description = "등록 테스트 상품 정보 요청 VO")
@Getter
@Setter
@NoArgsConstructor
public class ApiTestPrdctSVO implements Serializable {

	private static final long serialVersionUID = 998612014313152769L;

	@NotBlank(message = "SAAS제품아이디는 필수입니다.")
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "서버 구분 코드")
	private String srvrSeCd;
	
	@Schema(description = "항목 구분 타입")
	private String artclSeType;
	
	@Schema(description = "API 아이디")
	private String apiId;
	
	@Schema(description = "SAAS제품API버전일련번호")
	private Integer saasPrdctApiVerSn;
	
	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;

	public ApiTestPrdctSVO(String saasPrdctId, String srvrSeCd, Integer apiVerSn) {
		super();
		this.saasPrdctId = saasPrdctId;
		this.srvrSeCd = srvrSeCd;
		this.apiVerSn = apiVerSn;
	}

}
