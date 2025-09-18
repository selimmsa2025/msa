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
 * 등록 테스트 상품 정보 요청 VO
 * @name_ko 등록 테스트 상품 정보 요청 VO
 * @author hyy
 */
@Schema(description = "등록 테스트 상품 정보 요청 VO")
@Getter
@Setter
public class ApiTestSVO implements Serializable {

	private static final long serialVersionUID = -1141270265943298415L;

	@Schema(description = "SAAS제품명")
	private String saasPrdctNm;
	
	@Schema(description = "API버전일련번호")
	private Integer apiVerSn;

	@Schema(description = "상품안내문등록여부")
	private String gdsGdntcRegYn;
	
	@Schema(description = "페이지 사용 여부")
	private String pageYn;
	
	@Schema(description = "현재 페이지")
	private int currentPage;
	
	@Schema(description = "페이지 글 수")
	private int pageSize;
	
	@Schema(description = "API통신성공여부")
	private String apiCmncScsYn;
	
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
