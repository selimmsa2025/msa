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

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


/**
 * 등록 테스트 상품 정보 요청 VO
 * @name_ko 등록 테스트 상품 정보 요청 VO
 * @author hyy
 */
@Schema(description = "API 테스트 - API 선택 팝업 VO")
@Getter
@Setter
public class StdApiDVO implements Serializable  {

	private static final long serialVersionUID = 5188027837613114448L;

	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;

	@Schema(description = "API아이디")
	private String apiId;

	@Schema(description = "API명")
	private String apiNm;

	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd;

	@Schema(description = "SAAS제품유형코드명")
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

}
