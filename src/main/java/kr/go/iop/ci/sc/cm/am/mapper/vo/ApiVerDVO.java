/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.am.mapper.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 표준 API 버전 조회 결과용 정보 VO.
 * @name_ko 표준 API 버전 화면표시 VO
 * @author lsc, ksj
 */
@Schema(description = "표준 API 버전 VO")
@Getter
@Setter
public class ApiVerDVO implements Serializable {
	
	private static final long serialVersionUID = 3546978208630986340L;

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
	private String lastChgDt
;
}
