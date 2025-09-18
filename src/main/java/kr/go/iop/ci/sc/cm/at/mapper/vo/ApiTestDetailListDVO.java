/*
 *  Copyright (c) 2025 Intelligent On-nara BPS Platform.
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.at.mapper.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * API 테스트 - 상세 API 목록 VO
 * @name_ko API 테스트 - 상세 API 목록 VO
 * @author hyy
 */
@Schema(description = "API 테스트 - 상세 API 목록 VO")
@Getter
@Setter
public class ApiTestDetailListDVO implements Serializable {

	private static final long serialVersionUID = 7407905136328384801L;

	@Schema(description = "API 아이디")
	private String apiId;
	
	@Schema(description = "API 명")
	private String apiNm;
	
	@Schema(description = "통신 결과 코드")
	private String cmncRsltCd;
	
	@Schema(description = "통신 결과 코드명")
	private String cmncRsltNm;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	@Schema(description = "URI 주소")
	private String uriAddr;
	
//	@Schema(description = "최초생성일시")
//	private LocalDate frstCrtDt;
	
	@Schema(description = "HTTP통신구분코드")
	private String httpCmncSeCd;
	
	@Schema(description = "HTTP통신구분명")
	private String httpCmncSeNm;
	
	@Schema(description = "서버 구분 코드")
	private String srvrSeCd;
	
	@Schema(description = "헤더 내용")
	private Map<String, String> headerContents = new LinkedHashMap<>();
	
	@Schema(description = "Req body 내용")
	private Map<String, Object> reqBdyContents = new LinkedHashMap<>();
	
	@Schema(description = "통신일시")
	private LocalDateTime cmncDt;
	
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "통신결과내용")
	private String cmncRsltCn;

}
