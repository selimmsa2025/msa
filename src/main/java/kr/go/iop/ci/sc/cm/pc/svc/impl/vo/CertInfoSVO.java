/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pc.svc.impl.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * API인증관리 상세 조회 결과용 정보 SVO(파라미터).
 * 
 * @name_ko API인증관리 상세 화면표시 SVO(파라미터)
 * @author lsc
 */
@Schema(description = "API 인증키 관리")
@Getter
@Setter
public class CertInfoSVO implements Serializable {
	private static final long serialVersionUID = 242697152499057132L;

	@Schema(description = "상품관리아이디")
	private String saasPrdctId;

	@Schema(description = "서버구분코드(B002): 개발, 운영")
	private String srvrSeCd;

	@Schema(description = "시험인증일련번호(A001)")
	private int cmncCertSn;

	@Schema(description = "인증키명")
	private String authkeyNm;

	@Schema(description = "인증키값")
	private String authkey;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

}
