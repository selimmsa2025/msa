/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.am.svc;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.go.iop.ci.sc.cm.am.mapper.vo.AmArtcDVO;
import kr.go.iop.ci.sc.cm.am.mapper.vo.AmDVO;
import kr.go.iop.ci.sc.cm.am.mapper.vo.AmProdDVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmArtcSVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmSVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.ApiVerSVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmProdSVO;

/**
 * 표준API관리 서비스 인터페이스.
 * 
 * @name_ko 표준API관리 인터페이스
 * @author lsc, ksj
 */
public interface ApiMngService {

	/* 표준API목록조회 */
	public List<AmDVO> selectStndApiList(AmSVO vo);

	/* 표준API목록건수조회 */
	public int selectStndApiCnt(AmSVO vo);

	/* 표준API단건조회 */
	public AmDVO selectStndApiInfo(AmSVO vo);

	/* 표준API항목조회 */
	public List<AmArtcDVO> selectStndApiArtclList(AmSVO vo);

	/* 표준API_ID 최대값 조회 */
	public String selectNextStndApiId();

	/* 표준API등록_API 기본정보 */
	public int insertStndApi(AmSVO vo);

	/* 표준API등록_API 항목정보 */
	public int insertStndApiArtcl(AmArtcSVO vo);

	/* 표준API수정_API 기본정보 + 항목정보 */
	int updateStndApi(AmSVO svo);

	/* 표준API삭제_API 기본정보 */
	public int deleteStndApi(AmSVO vo);

	/* 표준API삭제_API 항목정보 */
	public int deleteStndApiArtcl(AmSVO vo);

	/* API 버전 등록 */
	List<Integer> insertStndApiVer(ApiVerSVO svo);

	/* API 버전 목록 조회 */
	List<Integer> selectStndApiVerList();

	// 2025.08.19 API 추가에 따른 테스트 정보 추가를 위한 메서드
	/* 상품 리스트 조회 */
	public List<AmProdDVO> selectProdList(AmSVO vo);

	/* 상품표준API관계등록 */
	public int insertProdStndApiR(AmProdSVO vo);

	/* 상품 API 통신 항목 등록(초기셋팅) */
	public int insertProdApiTest(AmProdSVO vo);

	/* 상품표준API관계삭제 */
	public int deleteProdStndApiR(AmSVO vo);

	/* 상품 API 통신 항목 삭제 */
	public int deleteProdApiTest(AmSVO vo);

	/* 표준 API 목록 엑셀 다운로드 (현재페이지/전체) */
	void selectStndApiListExcelDownload(HttpServletRequest request, HttpServletResponse response, AmSVO svo,
			String scope);

	/* 표준 API 상세 엑셀 다운로드 */
	void selectStndApiInfoExcelDownload(HttpServletRequest request, HttpServletResponse response, AmSVO svo);

	public int updateCertKey(AmSVO vo);


}
