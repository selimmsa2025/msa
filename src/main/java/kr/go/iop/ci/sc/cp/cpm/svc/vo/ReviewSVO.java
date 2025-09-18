/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cp.cpm.svc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 후기 조회용 VO
 * @name_ko 후기 조회 VO
 * @author selim
 */
@Schema(description = "후기 조회 VO")
@Getter
@Setter
public class ReviewSVO implements Serializable {

	private static final long serialVersionUID = -7845537423400931459L;
	
	@Schema(description = "saas제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "평가자아이디")
	private String evltrId;
	
	@Schema(description = "계정ID")
	private String accountId;

	@Schema(description = "saas제품평가점수")
	private BigDecimal saasPrdctEvlScr;

	@Schema(description = "saas제품평가내용")
	private String saasPrdctEvlCn;
	
	@Schema(description = "정렬기준")
	private String sortCrtr;

}
