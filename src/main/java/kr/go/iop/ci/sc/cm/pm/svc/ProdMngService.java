/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pm.svc;

import java.util.List;

import kr.go.iop.ci.sc.cm.pm.mapper.vo.ProdMngDVO;
import kr.go.iop.ci.sc.cm.pm.svc.vo.ProdMngSVO;

/**
 * 상품관리 서비스 인터페이스.
 * @name_ko 상품관리 인터페이스
 * @author pjh
 */
public interface ProdMngService {
	
	/* 상품관리 조회 */
	public List<ProdMngDVO> selectProdList(ProdMngSVO vo);
	
	/* 상품관리 조회 건수 */
	public int selectProdCnt(ProdMngSVO vo);
	
	/* 상품관리 상세조회 */
	public ProdMngDVO selectProdInfo(ProdMngSVO vo);
	
	/* 상품관리 상품ID 조회 */
	public String selectProdId(ProdMngSVO vo);
	
	/* 상품관리 상품등록 */
	public int insertProd(ProdMngSVO vo);
	
	/* 상품관리 상품수정 */
	public int updateProd(ProdMngSVO vo);
	
	/* 상품관리 상품삭제 */
	public int deleteProd(ProdMngSVO vo);
	
	/* 상품 표준 API 관계 등록 위한 정보 조회  */
	public List<ProdMngDVO> selectStdApiInfo(ProdMngSVO vo);
	
	/* 상품표준API관계등록 */
	public int insertStdApi(ProdMngSVO vo);

	/* 상품 API 통신 항목 등록(초기셋팅) */
	public int insertProdApiTest(ProdMngSVO vo);
	
	/* 상품관리 서비스 조회 */
	public List<ProdMngDVO> selectServiceList(ProdMngSVO vo);
	
	/*상품관리 서비스 상세조회 */
	public ProdMngDVO selectServiceInfo(ProdMngSVO vo);
	
	/*상품관리 서비스등록 */
	public int insertService(ProdMngSVO vo);
	
	/*상품관리 서비스수정 */
	public int updateService(ProdMngSVO vo);
	
	/*상품관리 서비스삭제 */
	public int deleteService(ProdMngSVO vo);
	
	/* API 최종버전 조회 */
	public int selectMaxStndApiVer();
	
	/*상품 인증키 등록 */
	public int insertCertKey(ProdMngSVO vo);
	
	/* 상품명 중복검사 */
	public int selectPrdctNmDupeTest(ProdMngSVO vo);
	
	/* 상품 구독 기관 갯수 */
	public int selectPrdctPrdsbscTest(ProdMngSVO vo);
}
