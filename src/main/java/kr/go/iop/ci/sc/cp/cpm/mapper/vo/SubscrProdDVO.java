/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cp.cpm.mapper.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 *  구독 상품 결과 조회용 VO
 *  @name_ko 구독 상품 결과 조회용 VO
 *  @author lsh
 */
@Schema(description = "구독 상품 결과 조회 VO")
@Getter
@Setter
public class SubscrProdDVO implements Serializable {

	private static final long serialVersionUID = -7812805798794722390L;
	
	/* 기관별 구독 상품 목록 조회 s */
	@Schema(description = "기관코드")
	private String institutionCode;   
	
	@Schema(description = "기관명")
	private String institutionName;
	
	@Schema(description = "구독상품목록")
	private List<ProdDVO> products;
	
	/* 기관별 구독 상품 목록 조회 e */

	/* 상품 구독 정보 조회 s */

	@Schema(description = "기관사용자통합아이디")
	private String instUserIntgId;

	@Schema(description = "기관사용자구분코드")
	private String instUserSeCd;

	@Schema(description = "기관사용자구분명")
	private String instUserSeNm;

	@Schema(description = "제품구독일련번호")
	private String prdsbscSn;

	@Schema(description = "제품구독상태코드")
	private String prdsbscSttsCd;
	
	@Schema(description = "제품구독상태명")
	private String prdsbscSttsNm;

	@Schema(description = "제품구독시작일자")
	private String prdsbscBgngYmd;

	@Schema(description = "제품구독종료일자")
	private String prdsbscEndYmd;

	@Schema(description = "제품구독기관수")
	private String saasPrdctSubCnt;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

	/* 상품 구독 정보 조회 e */

}
