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
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 표준API관리 조회용 정보 VO.
 * @name_ko 표준API관리 조회 VO
 * @author lsc, ksj
 */
@Schema(description = "표준API 기본")
@Getter
@Setter
public class AmSVO implements Serializable {

	private static final long serialVersionUID = 7854411192473781015L;

	@Schema(description = "API버전일련번호")
	private int apiVerSn;

	@Schema(description = "API아이디")
	private String apiId;

	@Schema(description = "API명")
	private String apiNm;

	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd; 

	@Schema(description = "SAAS제품유형명")
	private String saasPrdctTypeNm; 

	@Schema(description = "API요청응답구분코드")
	private String apiDmndRspnsSeCd;

	@Schema(description = "API요청응답구분코드명")
	private String apiDmndRspnsSeNm;

	@Schema(description = "HTTP통신구분코드")
	private String httpCmncSeCd; 

	@Schema(description = "HTTP통신구분코드명")
	private String httpCmncSeNm; 

	@Schema(description = "URI주소")
	private String uriAddr;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

	@Schema(description = "표준API항목내역")
	private List<AmArtcSVO> paramList;
	
	@Schema(description = "카탈로그등록여부")
	private String gdsGdntcRegYn; 

	@Schema(description = "페이지번호")
	private int page;
	private int pageSize;
	private int offset;
	
	@Schema(description = "서버구분")
	private String SrvrSeCd; 
	
	@Schema(description = "saas상품아이디")
	private String SaasPrdctId; 

//	@Schema(description = "페이징여부")
//	private String pageYn = "Y"; 

}
