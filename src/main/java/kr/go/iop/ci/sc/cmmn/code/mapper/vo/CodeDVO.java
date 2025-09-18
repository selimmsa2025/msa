/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.code.mapper.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통코드관리 조회 결과용 정보 VO.
 * 
 * @name_ko 공통코드관리 화면표시 VO
 * @author lsc
 */
@Schema(description = "운영현황-상품, 운영현황-코드")
@Getter
@Setter
public class CodeDVO implements Serializable {

	private static final long serialVersionUID = 1483746930542786814L;

	@Schema(description = "그룹코드아이디")
	private String groupCdId;

	@Schema(description = "상세코드")
	private String dtlCd;

	@Schema(description = "상세코드한글명")
	private String dtlCdKornNm;

	@Schema(description = "상세코드영문명")
	private String dtlCdEngNm;

	@Schema(description = "상세코드한글약어명")
	private String dtlCdKornAbbrNm;

	@Schema(description = "상세코드영문약어명")
	private String dtlCdEngAbbrNm;

	@Schema(description = "정렬순서")
	private Integer sortSeq;

	@Schema(description = "사용여부")
	private String useYn;

	@Schema(description = "사용시작일자")
	private String useBgngYmd;

	@Schema(description = "사용종료일자")
	private String useEndYmd;

	@Schema(description = "코드참조내용")
	private String cdRfrncCn;

	@Schema(description = "상세코드한글설명")
	private String dtlCdKornExpln;

	@Schema(description = "상세코드영문설명")
	private String dtlCdEngExpln;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

}
