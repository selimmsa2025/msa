/*
 *  Copyright (c) 2025 Intelligent On-nara BPS Platform.
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.at.svc.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


/**
 * API 테스트 - API 선택 팝업 VO
 * @name_ko API 테스트 - API 선택 팝업 VO
 * @author hyy
 */
@Schema(description = "API 테스트 - API 선택 팝업 VO")
@Getter
@Setter
public class StdApiSVO implements Serializable {

	private static final long serialVersionUID = 7748531683052427329L;

	@Schema(description = "API명")
	private String apiNm;
	
	@Schema(description = "API요청응답구분코드")
	private String apiDmndRspnsSeCd;
	
	@Schema(description = "HTTP통신구분코드")
	private String httpCmncSeCd;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;
	
	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd;
	
	@Schema(description = "API아이디")
	private String apiId;
	
	@Schema(description = "페이지 사용 여부")
	private String pageYn;
	
	@Schema(description = "현재 페이지")
	private int currentPage;
	
	@Schema(description = "페이지 글 수")
	private int pageSize;
	
	public int getCurrentPage() {
		if(this.currentPage == 0) {
			return 1;
		} else {
			return this.currentPage;
		}
	}
	
	public int getPageSize() {
		if(this.pageSize == 0) {
			return 10;
		} else {
			return this.pageSize;
		}
	}
}
