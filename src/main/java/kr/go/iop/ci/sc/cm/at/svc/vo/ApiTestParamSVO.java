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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * API 테스트 파라미터 VO 
 * @name_ko API 테스트 파라미터 VO 
 * @author hyy
 */
@Schema(description = "API 테스트 파라미터 VO")
@Getter
@Setter
@AllArgsConstructor
public class ApiTestParamSVO implements Serializable {

	private static final long serialVersionUID = -3288072604057769616L;

	@Schema(description = "API Test key")
	private String key;
	
	@Schema(description = "API Test value")
	private Object value;


}
