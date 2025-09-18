/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pc.svc;

import java.util.List;

import kr.go.iop.ci.sc.cm.pc.mapper.vo.ApiCertKeyDVO;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.ApiCertKeyReqSVO;

/**
 * API인증관리 서비스 인터페이스.
 * 
 * @name_ko API인증관리 인터페이스
 * @author lsc
 */
public interface ProdCertService {

	/* API 인증키 관리 목록조회 (통신테스트 목록조회 및 구독 목록조회) */
	public List<ApiCertKeyDVO> selectApiCertKeyList(ApiCertKeyReqSVO vo);

	public int selectApiCertKeyListCnt(ApiCertKeyReqSVO vo);

	public List<ApiCertKeyDVO> selectSubCertKeyList(ApiCertKeyReqSVO vo);

	public int selectSubCertKeyListCnt(ApiCertKeyReqSVO vo);

	/* API 인증키 상세 및 API 구독 인증키 상세 */
	public ApiCertKeyDVO selectApiCertKeydetail(ApiCertKeyReqSVO vo);

	/* API 인증키 정보 수정 */
	public int updateApiCertInfo(ApiCertKeyReqSVO vo);

	/* API 인증키 정보 삭제 */
	public int deleteApiCertInfo(ApiCertKeyReqSVO vo);

	/* API 인증키 등록 */
	public int createJwtKey(ApiCertKeyReqSVO vo);

}
