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
import lombok.Getter;
import lombok.Setter;

/**
 * API통신성공여부 변경 관련 SVO
 * @name_ko API통신성공여부 변경 관련 SVO
 * @author hyy
 */
@Schema(description = "API통신성공여부 변경 관련 SVO")
@Getter
@Setter
public class CmncMngSVO implements Serializable {

	private static final long serialVersionUID = -6358209652611508778L;
	
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "서버 구분 코드")
	private String srvrSeCd;
	
	@Schema(description = "API통신성공여부")
	private String apiCmncScsYn;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	public CmncMngSVO(String saasPrdctId, String srvrSeCd) {
		super();
		this.saasPrdctId = saasPrdctId;
		this.srvrSeCd = srvrSeCd;
	}
	
	

}
