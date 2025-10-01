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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 *  구독 조회용 VO
 *  @name_ko 구독 조회 VO
 *  @author lsh
 */
@Schema(description = "구독 조회 VO")
@Getter
@Setter
public class SubscrSVO implements Serializable {

	private static final long serialVersionUID = -3808497077331333145L;

	@Schema(description = "기관사용자통합아이디")
	private String instUserIntgId;

	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;

	@Schema(description = "기관사용자구분코드")
	private String instUserSeCd;

	@Schema(description = "제품구독일련번호")
	private int prdsbscSn;
	
	@Schema(description = "제품구독인증일련번호")
	private int prdsbscCertSn;
	
	@Schema(description = "제품구독이력일련번호")
	private int prdsbscHstrySn;

	@Schema(description = "제품구독상태코드")
	private String prdsbscSttsCd;
	
	@Schema(description = "계정아이디")
	private String accountId;

	@Schema(description = "제품구독시작일자")
	private String prdsbscBgngYmd;

	@Schema(description = "제품구독종료일자")
	private String prdsbscEndYmd;
	
	// 외부 SaaS 연계 s 
	@Schema(description = "기관코드")
	private String institutionCode;

	@Schema(description = "기관명")
	private String institutionName;

	@Schema(description = "지능형ID")
	private String iopId;

	@Schema(description = "SAAS제품제조사코드")
	private String saasPrdctMkrCd;
	// 외부 SaaS 연계 e
	
}
