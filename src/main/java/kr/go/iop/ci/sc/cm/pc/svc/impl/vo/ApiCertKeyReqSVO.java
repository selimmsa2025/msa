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
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * API 인증 관리 조회 결과용 정보 SVO(파라미터).
 * 
 * @name_ko API 인증 관리 화면표시 SVO(파라미터)
 * @author lsc
 */
@Schema(description = "API 인증키 관리")
@Getter
@Setter
public class ApiCertKeyReqSVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 인증키관리내역 */
	@Schema(description = "상품관리아이디")
	private String saasPrdctId;

	@Schema(description = "상품명")
	private String saasPrdctNm;

	@Schema(description = "서버구분코드(B002): 개발, 운영")
	private String srvrSeCd;

	@Schema(description = "서버구분코드명")
	private String srvrSeCdNm;

	@Schema(description = "API통신성공여부")
	private String apiCommScsYn;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

	@Schema(description = "인증정보 발급여부")
	private String hasTestCert;

	@Schema(description = "인증정보 항목내역")
	private List<CertInfoSVO> paramList;

	/* 상품정보 */
	@Schema(description = "상품제공방식구분코드명(A001): 기관상품, 개별상품, 개별상품(설치형)")
	private String saasPrdctPvsnMthSeCd;

	@Schema(description = "상품제공방식구분코드명")
	private String saasPrdctPvsnMthSeCdNm;

	@Schema(description = "상품유형코드(A002): 패키지, 웹오피스")
	private String saasPrdctTypeCd;

	@Schema(description = "상품유형코드명")
	private String saasPrdctTypeCdNm;

	/* JWT */
	@Schema(description = "인증진행단계코드(A005): com_c에 없음")
	private String certPrgrsStpCd;

	@Schema(description = "인증진행단계코드명")
	private String aplySrctKey;

	@Schema(description = "신청키")
	private String certPrgrsStpCdNm;

	@Schema(description = "신청비밀키")
	private String aplyKey;

	@Schema(description = "accesstoken")
	private String jsonWebTokenKey;

	@Schema(description = "refreshtoken")
	private String certKey;

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

	/* 상품구독내역 */
	@Schema(description = "제품구독일련번호(FK)")
	private int prdsbscSn;
	
	@Schema(description = "기관사용자통합아이디(FK)")
	private String instUserIntgId;

	@Schema(description = "기관사용자구분코드(A007)") // 기관사용자구분코드 (A007)
	private String instUserSeCd;

	@Schema(description = "구독일련번호(PK)")
	private int subSn;

	@Schema(description = "제품구독상태코드(A004)") // 제품구독상태코드 (A004: 트라이얼/유료/취소 등)
	private String subSttsCd;

	@Schema(description = "구독시작일자(YYYY-MM-DD)")
	private String subBgngYmd;

	@Schema(description = "구독종료일자(YYYY-MM-DD)")
	private String subEndYmd;

	@Schema(description = "기관명")
	private String institutionName;

	@Schema(description = "상세 구분값")
	private String gubun;
	
	@Schema(description = "인증구분코드")
	private String certSeCd;

}
