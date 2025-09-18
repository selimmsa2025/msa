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
public class StdApiArtclDVO implements Serializable {

	private static final long serialVersionUID = 8349324464190085143L;

	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	@Schema(description = "API 아이디")
	private String apiId;
	
	@Schema(description = "API항목 구분코드")
	private String apiArtclSeCd;
	
	@Schema(description = "API항목 일련번호")
	private Integer apiArtclSn;
	
	@Schema(description = "API항목 속성명")
	private String apiArtclAtrbNm;
	
	@Schema(description = "API항목자료유형코드")
	private String apiArtclDataTypeCd;
	
	@Schema(description = "API항목 필수여부")
	private String apiArtclEsntlYn;
	
	@Schema(description = "최초생성일시")
	private String frstCrtDt;

}
