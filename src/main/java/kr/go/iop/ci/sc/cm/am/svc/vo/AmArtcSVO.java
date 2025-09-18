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
 * 표준API관리 조회용 정보 VO(파라미터).
 * 
 * @name_ko 표준API관리 조회 VO(파라미터)
 * @author lsc, ksj
 */
@Schema(description = "표준API 항목(파라미터)")
@Getter
@Setter
public class AmArtcSVO implements Serializable {

	private static final long serialVersionUID = 5038336480412341000L;

	@Schema(description = "API버전일련번호")
	private int apiVerSn;

	@Schema(description = "API아이디")
	private String apiId;

	@Schema(description = "API항목구분코드")
	private String apiArtclSeCd;

	@Schema(description = "API항목일련번호")
	private Long apiArtclSn;

	@Schema(description = "API항목속성명")
	private String apiArtclAtrbNm;

	@Schema(description = "API항목자료유형코드")
	private String apiArtclDataTypeCd; //

	@Schema(description = "API항목자료유형코드명")
	private String apiArtclDataTypeNm; //

	@Schema(description = "API항목필수여부")
	private String apiArtclEsntlYn;

	@Schema(description = "API항목내용")
	private String apiArtclCn;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

	@Schema(description = "파라미터 항목 상태관리 - 수정")
	private String rowStatus;

}
