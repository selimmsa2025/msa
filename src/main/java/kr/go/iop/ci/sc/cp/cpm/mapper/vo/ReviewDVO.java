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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 후기 결과 조회 VO
 * @name_ko 후기 결과 조회 VO
 * @author selim
 */
@Schema(description = "후기 결과 조회 VO")
@Getter
@Setter
public class ReviewDVO implements Serializable {
	private static final long serialVersionUID = -4963571177026675980L;
	
	@Schema(description = "saas제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "평가자아이디")
	private String evltrId;

	@Schema(description = "saas제품평가점수")
	private String saasPrdctEvlScr;

	@Schema(description = "saas제품평가내용")
	private String saasPrdctEvlCn;

	@Schema(description = "사용자명")
	private String userNm;
	
	@Schema(description = "saas제품평가건수")
	private String saasPrdctEvlCnt;
	
	@Schema(description = "saas제품평가점수평균")
	private String saasPrdctEvlScrAvg;
	
	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

}
