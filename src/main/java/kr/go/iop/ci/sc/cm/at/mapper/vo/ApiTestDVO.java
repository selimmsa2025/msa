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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * API 테스트 목록 조회 VO
 * @name_ko API 테스트 목록 조회 VO
 * @author hyy
 */
@Schema(description = "API 테스트 목록 조회 VO")
@Getter
@Setter
public class ApiTestDVO implements Serializable {

	private static final long serialVersionUID = -2792267680319974235L;

	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "SAAS제품명")
	private String saasPrdctNm;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	@Schema(description = "서버구분코드")
	private String srvrSeCd;
	
	@Schema(description = "서버구분코드명")
	private String srvrSeNm;
	
	@Schema(description = "카탈로그등록여부")
	private String gdsGdntcRegYn;
	
	@Schema(description = "테스트 일자")
	private LocalDateTime cmncDt;
	
	@Schema(description = "API 최신 버전")
	private Integer apiLatestVer;
	
	@Schema(description = "API통신성공여부")
	private String apiCmncScsYn;
	
	@Schema(description = "SAAS제품API버전일련번호")
	private Integer saasPrdctApiVerSn;

}
