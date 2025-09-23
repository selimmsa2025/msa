/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.am.svc.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 표준 API 버전 조회용 정보 VO.
 * 
 * @name_ko 표준 API 버전 조회 VO
 * @author lsc, ksj
 */
@Getter
@Setter
@Schema(description = "표준API 버전")
public class ApiVerSVO implements Serializable {

	private static final long serialVersionUID = 9084654901932807455L;

	@Schema(description = "API버전일련번호")
	private int apiVerSn; 

	@Schema(description = "API버전사용여부")
	private String useYn; 

	@Schema(description = "API버전변경사유")
	private String apiVerChgRsn; 

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId; 

	@Schema(description = "최초생성일시")
	private String frstCrtDt; 
	
	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

}
