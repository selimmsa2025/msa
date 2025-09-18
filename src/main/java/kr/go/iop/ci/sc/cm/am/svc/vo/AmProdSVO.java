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
 * 상품관리 조회용 정보 VO.
 * 
 * @name_ko 상품관리 조회 VO
 * @author pjh
 */
@Schema(description = "상품관리")
@Getter
@Setter
public class AmProdSVO implements Serializable {
	private static final long serialVersionUID = -7384031555947545531L;

	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId; 

	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd; 

	@Schema(description = "SAAS제품유형명")
	private String saasPrdctTypeNm; 

	@Schema(description = "SAAS제품명")
	private String saasPrdctNm;

	@Schema(description = "SAAS제품API버전일련번호")
	private Integer saasPrdctApiVerSn; 

	@Schema(description = "사용여부")
	private String userYn;

	@Schema(description = "카탈로그등록여부")
	private String gdsGdntcRegYn; 
	
	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

//	상품표준API, 통신결과내역 초기셋팅 start
	@Schema(description = "API아이디")
	private String apiId;

	@Schema(description = "API버전일련번호")
	private int apiVerSn;

	@Schema(description = "서버구분코드")
	private String srvrSeCd;

	@Schema(description = "통신결과코드")
	private String cmncRsltCd;
//	상품표준API, 통신결과내역 초기셋팅 end



}
