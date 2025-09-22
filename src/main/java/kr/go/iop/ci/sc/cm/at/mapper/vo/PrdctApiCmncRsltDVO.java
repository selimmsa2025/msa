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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품 API 통신결과 내역 VO
 * @name_ko 상품 API 통신결과 내역 VO
 * @author hyy
 */
@Schema(description = "상품 API 통신결과 내역 VO")
@Getter
@Setter
public class PrdctApiCmncRsltDVO implements Serializable {

	private static final long serialVersionUID = -5076337252362216434L;
	
	private static final int MAX_BYTE_LENGTH = 4000;

	@Schema(description = "SAAS제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "서버구분코드")
	private String srvrSeCd;
	
	@Schema(description = "API 아이디")
	private String apiId;
	
	@Schema(description = "통신 결과 코드")
	private String cmncRsltCd;
	
	@Schema(description = "통신 결과 코드명")
	private String cmncRsltNm;
	
	@Schema(description = "통신일시")
	private LocalDateTime cmncDt;
	
	@Schema(description = "통신결과내용")
	private String cmncRsltCn;
	
	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;
	
	@Schema(description = "최초생성일시")
	private String frstCrtDt;
	
	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;
	
	@Schema(description = "최종변경일시")
	private String lastChgDt;
	
	@Schema(description = "API 추가, 삭제 구분")
	private String status;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	public void setCmncRsltCn(String cmncRsltCn) {	
		if (cmncRsltCn == null) {
	        this.cmncRsltCn = null;
	        return;
	    }

	    byte[] bytes = cmncRsltCn.getBytes(StandardCharsets.UTF_8);
	    if (bytes.length <= MAX_BYTE_LENGTH) {
	        this.cmncRsltCn = cmncRsltCn;
	        return;
	    }

	    // 4000바이트 이하가 될 때까지 뒤에서 한 글자씩 줄임
	    int endIndex = cmncRsltCn.length();
	    while (endIndex > 0) {
	        String temp = cmncRsltCn.substring(0, endIndex);
	        if (temp.getBytes(StandardCharsets.UTF_8).length <= MAX_BYTE_LENGTH) {
	            this.cmncRsltCn = temp;
	            return;
	        }
	        endIndex--;
	    }

	    // 안전장치: 1글자도 못들어가면 빈 문자열
	    this.cmncRsltCn = "";
	}
	
}
