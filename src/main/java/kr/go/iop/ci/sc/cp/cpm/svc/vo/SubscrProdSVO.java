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
 *  구독 상품 조회용 VO
 *  @name_ko 구독 상품 조회 VO
 *  @author lsh
 */
@Schema(description = "구독 상품 조회 VO")
@Getter
@Setter
public class SubscrProdSVO implements Serializable {

	private static final long serialVersionUID = 903712735544043080L;

	@Schema(description = "기관사용자통합아이디")
	private String instUserIntgId;

	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "기관사용자구분코드")
	private String instUserSeCd;

}
