/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pm.svc.vo;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
/**
 * 상품관리 조회용 정보 VO.
 * @name_ko 상품관리 조회 VO
 * @author pjh
 */
@Schema(description = "상품관리")
@Getter
@Setter
public class ProdMngSVO implements Serializable{
	private static final long serialVersionUID = -7384031555947545531L;
	
	@Schema(description = "SAAS제품아이디")
    private String saasPrdctId;
	
	@Schema(description = "SAAS제품제공방식구분코드")
	private String saasPrdctPvsnMthSeCd;
	
	@Schema(description = "SAAS제품유형코드")
	private String saasPrdctTypeCd;
	
	@Schema(description = "진행상태")
	private String progressStatus;
	
	@Schema(description = "SAAS제품명")
	private String saasPrdctNm;
	
	@Schema(description = "SAAS제품상세내용")
	private String saasPrdctDtlCn;
	
	@Schema(description = "SAAS제품요약내용")
	private String saasPrdctSmryCn;
	
	@Schema(description = "SAAS제품제조사코드")
	private String saasPrdctMkrCd;
	
	@Schema(description = "SAAS제품API버전일련번호")
	private Integer saasPrdctApiVerSn;
	
	@Schema(description = "SAAS제품서비스도메인주소")
	@URL(message = "올바른 URL 형식이어야 합니다.")
	@Pattern(
		regexp = "^(http|https)://[^%\\\\^$]*$",
		message = "URL은 http:// 또는 https:// 로 시작하고 올바른 도메인을 포함해야 합니다. % ^ $ 같은 특수문자는 사용할 수 없습니다."
	)
	private String saasPrdctSrvcDmnAddr;
	
	@Schema(description = "SAAS제품서비스상세URL주소")
	@URL(message = "올바른 URL 형식이어야 합니다.")
	@Pattern(
		regexp = "^(http|https)://[^%\\\\^$]*$",
		message = "URL은 http:// 또는 https:// 로 시작하고 올바른 도메인을 포함해야 합니다. % ^ $ 같은 특수문자는 사용할 수 없습니다."
	)
	private String saasPrdctSrvcDtlUrlAddr;
	
	@Schema(description = "SAAS제품FAQ주소")
	@URL(message = "올바른 URL 형식이어야 합니다.")
	@Pattern(
		regexp = "^$|(http|https)://[^%\\\\^$]*$",
		message = "URL은 http:// 또는 https:// 로 시작하고 올바른 도메인을 포함해야 합니다. % ^ $ 같은 특수문자는 사용할 수 없습니다."
	)
	private String saasPrdctFaqAddr;
	
	@Schema(description = "사용여부")
	private String useYn;
	
	@Schema(description = "상품안내문등록여부")
	private String gdsGdntcRegYn;
	
	@Schema(description = "제품이미지첨부파일그룹번호")
	private String prdctImgAtchFileGroupNo;
	
	@Schema(description = "화면이미지첨부파일그룹번호")
	private String scrnImgAtchFileGroupNo;
	
	@Schema(description = "설명이미지첨부파일그룹번호")
	private String explnImgAtchFileGroupNo;
	
	@Schema(description = "첨부파일그룹번호")
	private String atchFileGroupNo;

	@Schema(description = "첨부파일일련번호")
	private int atchFileSn;
	
	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;
	
	@Schema(description = "최초생성일시")
	private String frstCrtDt;
	
	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;
	
	@Schema(description = "최종변경일시")
	private String lastChgDt;
	
//	서비스 start
	@Schema(description = "서비스아이디")
	private String srvcId;
	
	@Schema(description = "서비스명")
	private String srvcNm;
	
	@Schema(description = "서비스내용")
	private String srvcCn;
	
	@Schema(description = "서비스요약내용")
	private String srvcSmryCn;
	
	@Schema(description = "서비스상세URL주소")
	@Pattern(
		regexp = "^$|(http|https)://[^%\\\\^$]*$",
		message = "URL은 http:// 또는 https:// 로 시작하고 올바른 도메인을 포함해야 합니다. % ^ $ 같은 특수문자는 사용할 수 없습니다."
	)
	private String srvcDtlUrlAddr;
//	서비스 end
	
//	상품표준API, 통신결과내역 초기셋팅 start
	@Schema(description = "API아이디")
	private String apiId;
	
	@Schema(description = "API버전일련번호")
	private int apiVerSn;

	@Schema(description = "서버구분코드")
	private String srvrSeCd;
	
	@Schema(description = "통신결과코드")
	private String cmncRsltCd;
//	상품표준API, 통신결과내역 초기셋팅 end

	@Schema(description = "페이지처리유무")
	private String pageYn;
	
	@Schema(description = "현재페이지")
	private int currentPage;
	
	@Schema(description = "목록조회갯수")
	private int pageSize;
	
	@Schema(description = "순서")
	private int rnum;
	
	@Schema(description = "정렬기준 컬럼")
	private String sortColumn;
	
	@Schema(description = "정렬 순서")
	private String sortOrder;
}
