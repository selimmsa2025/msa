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
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 테스트 진행 관련 VO
 * @name_ko 테스트 진행 관련 VO
 * @author hyy
 */
@Getter
@Setter
@Schema(description = "테스트 진행 관련 VO")
public class TestExecutionSVO implements Serializable {

	private static final long serialVersionUID = -6676186254163801429L;

	@Schema(description = "헤더 내용")
	private Map<String, String> headerContents = new LinkedHashMap<>();
	
	@Schema(description = "Req body 내용")
	private Map<String, Object> reqBdyContents = new LinkedHashMap<>();
	
	@NotBlank(message = "서버구분코드는 필수입니다.")
	@Schema(description = "서버 구분 코드")
	private String srvrSeCd;
	
	@NotBlank(message = "SAAS제품아이디는 필수입니다.")
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@NotBlank(message = "API아이디는 필수입니다.")
	@Schema(description = "API 아이디")
	private String apiId;
	
	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;
	
	@Positive(message = "API버전일련번호가 유효하지 않습니다.")
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
}
