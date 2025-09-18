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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 API 통신결과 요청 VO
 * @name_ko 상품 API 통신결과 요청 VO
 * @author hyy
 */
@Schema(description = "상품 API 통신결과 요청 VO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrdctApiCmncRsltSVO implements Serializable {

	private static final long serialVersionUID = -5991963611666574462L;

	@NotBlank(message = "SAAS제품아이디는 필수입니다.")
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@NotBlank(message = "서버구분코드는 필수입니다.")
	@Schema(description = "서버구분코드")
	private String srvrSeCd;
	
	@NotBlank(message = "API아이디는 필수입니다.")
	@Schema(description = "API아이디")
	private String apiId;
	
	@Positive(message = "API버전일련번호가 유효하지 않습니다.")
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
}
