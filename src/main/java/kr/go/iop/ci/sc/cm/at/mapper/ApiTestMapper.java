/*
 *  Copyright (c) 2025 Intelligent On-nara BPS Platform.
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.at.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestDetailListDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestPrdctDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestProdDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.PrdctApiCmncRsltDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.StdApiArtclDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.StdApiDVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestPrdctSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.CmncMngSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.PrdctApiCmncRsltSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.StdApiSVO;



/**
* SaaS 등록 테스트 관리를 위한 mapper 클래스
* @name_ko SaaS 등록 테스트 관리 mapper.
* @author hyy
*/
@Mapper
public interface ApiTestMapper {

	/**
	 * API 테스트 목록 조회
	 * 
	 * @return
	 */
	public List<ApiTestDVO> selectProdApiTestList(ApiTestSVO req);
	
	/**
	 * 
	 * API 테스트 목록 수 조회
	 * 
	 * @param req
	 * @return
	 */
	public int selectApiTestTot(ApiTestSVO req);
	
	
	/**
	 * 
	 * 등록 테스트 상품 정보
	 * 
	 * @param req
	 * @return
	 */
	public ApiTestPrdctDVO selectProdApiTestInfo(ApiTestPrdctSVO req);
	
	/**
	 * 
	 * API 테스트 - 상세 API 목록
	 * 
	 * @param req
	 * @return
	 */
	public List<ApiTestDetailListDVO> selectApiTestDetailList(ApiTestPrdctSVO req);
	
	
	/**
	 * 
	 * 상세 테스트 API 파라미터 목록
	 * 
	 * @param req
	 * @return
	 */
	public List<StdApiArtclDVO> selectApiArtclList(ApiTestPrdctSVO req);
	
	
	/**
	 * 
	 * API 통신 결과 조회
	 * 
	 * @param req
	 * @return
	 */
	public PrdctApiCmncRsltDVO selectApiCommRslt(PrdctApiCmncRsltDVO req);
	
	
	/**
	 * 
	 * API 통신결과 조회 
	 * 
	 * @param req
	 * @return
	 */
	public PrdctApiCmncRsltDVO selectApiCommRsltInfo(PrdctApiCmncRsltSVO req);
	
	/**
	 * 
	 * API 통신결과 삭제
	 * 
	 * @param req
	 */
	public int deleteApiCommRslt(PrdctApiCmncRsltSVO req);
	
	
	/**
	 * 
	 * API 통신결과 추가
	 * 
	 * @param req
	 */
	public int insertProdApiTest(PrdctApiCmncRsltDVO req);
	
	/**
	 * 
	 * API 선택 팝업 목록
	 * 
	 * @param req
	 * @return
	 */
	public List<StdApiDVO> selectStdApiList(StdApiSVO req);
	
	/**
	 * 
	 * API 선택 팝업 목록 수
	 * 
	 * @param req
	 * @return
	 */
	public int selectStdApiListTot(StdApiSVO req);
	
	/**
	 * 
	 * 카탈로그 등록 여부 조회
	 * @param gdsId
	 * @return
	 */
	public ApiTestProdDVO selectProdInfo(ApiTestPrdctSVO req);
	
	/**
	 *  
	 *  카탈로그 사용으로 변경
	 * @param gdsId
	 * 
	 */
	public int updateCatalog(ApiTestPrdctSVO req);
	
	/**
	 * SaaS 상품 테스트 수정 
	 * @param req
	 */
	public int updateProdApiTest(PrdctApiCmncRsltDVO req);
	
	
	/**
	 *  카탈로그 버전 수정 
	 * @param req
	 */
	public int updateCatalogVersion(ApiTestPrdctSVO req);
	
	
	/**
	 * 
	 * 이전버전 api 테스트 삭제
	 * @param req
	 * 
	 */
	public int deleteApiTestByOldVersion(ApiTestPrdctSVO req);
	
	
	/**
	 * API 통신 성공여부 변경
	 * @param req
	 */
	public int updateApiCommScsYn(CmncMngSVO req);
	
	
	/**
	 *  API 최신 버전 조회
	 * @return
	 */
	public int getLastestApiVersion();
	
	/**
	 * 표준 API 상세정보 조회 
	 * @param req
	 * @return
	 */
	public StdApiDVO selectStdApiInfo(StdApiSVO req);
	
}
