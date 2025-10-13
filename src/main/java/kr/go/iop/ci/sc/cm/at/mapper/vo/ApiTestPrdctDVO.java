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
 * 등록 테스트 상품 정보 VO
 * @name_ko 등록 테스트 상품 정보 VO
 * @author hyy
 */
@Schema(description = "등록 테스트 상품 정보 VO")
@Getter
@Setter
public class ApiTestPrdctDVO implements Serializable {

	private static final long serialVersionUID = -1979205271305458349L;

	@Schema(description = "SAAS제품제공방식구분코드")
	private String saasPrdctPvsnMthSeCd;
	
	@Schema(description = "SAAS제품제공방식구분코드명")
	private String saasPrdctPvsnMthSeNm;
	
	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "SAAS제품명")
	private String saasPrdctNm;
	
	@Schema(description = "SAAS제품제조사코드")
	private String saasPrdctMkrCd;
	
	@Schema(description = "SAAS제품제조사코드명")
	private String saasPrdctMkrNm;
	
	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd;
	
	@Schema(description = "SAAS제품유형코드명")
	private String saasPrdctTypeNm;
	
	@Schema(description = "서버구분코드")
	private String srvrSeCd;
	
	@Schema(description = "서버구분코드명")
	private String srvrSeNm;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	@Schema(description = "상품최신버전")
	private Integer prdctLatestVer;
	
}
