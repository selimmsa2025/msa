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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * API 테스트 관련 상품 정보 VO
 * @name_ko API 테스트 관련 상품 정보 VO
 * @author hyy
 */
@Schema(description = "API 테스트 관련 상품 정보 VO")
@Getter
@Setter
public class ApiTestProdDVO {

	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "상품안내문등록여부")
	private String gdsGdntcRegYn;
	
	@Schema(description = "SAAS제품제공방식구분코드")
	private String saasPrdctPvsnMthSeCd;
	
	@Schema(description = "SAAS제품제공방식구분코드명")
	private String saasPrdctPvsnMthSeNm;
	
	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd;
	
	@Schema(description = "SAAS제품유형코드명")
	private String saasPrdctTypeNm;
	
	@Schema(description = "SAAS제품명")
	private String saasPrdctNm;
	
	@Schema(description = "SAAS제품제조사코드")
	private String saasPrdctMkrCd;
	
	@Schema(description = "SAAS제품제조사코드명")
	private String saasPrdctMkrNm;
	
	@Schema(description = "SAAS제품API버전일련번호")
	private Integer saasPrdctApiVerSn;
}
