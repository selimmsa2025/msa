/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.go.iop.ci.sc.cm.pc.mapper.vo.ApiCertKeyDVO;
import kr.go.iop.ci.sc.cm.pc.mapper.vo.CertInfoDVO;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.ApiCertKeyReqSVO;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.CertInfoSVO;

/**
 * API 인증 관리 매퍼 인터페이스.
 * 
 * @name_ko API 인증 관리 매퍼
 * @author lsc
 */
@Mapper
public interface ProdCertMapper {

	/* API 인증키 관리 목록조회 (통신테스트 목록조회 및 구독 목록조회) */
	public List<ApiCertKeyDVO> selectApiCertKeyList(ApiCertKeyReqSVO vo);

	public int selectApiCertKeyListCnt(ApiCertKeyReqSVO vo);

	public List<ApiCertKeyDVO> selectSubCertKeyList(ApiCertKeyReqSVO vo);

	public int selectSubCertKeyListCnt(ApiCertKeyReqSVO vo);

	/* API 인증키 상세 및 API 구독 인증키 상세 */
	public ApiCertKeyDVO selectApiCertKeyDetail(ApiCertKeyReqSVO vo);

	public List<CertInfoDVO> selectApiCertInfoList(ApiCertKeyReqSVO vo);

	public ApiCertKeyDVO selectSubCertKeyDetail(ApiCertKeyReqSVO vo);

	public List<CertInfoDVO> selectSubCertInfoList(ApiCertKeyReqSVO vo);

	/* API 인증키 정보 추가 및 구독 인증키 정보 추가 */
	public int insertApiCertInfo(CertInfoSVO vo);

	public int insertSubCertInfo(CertInfoSVO vo); /* 미사용 */

	/* API 인증키 정보 삭제 및 구독 인증키 정보 삭제 */
	public int deleteApiCertInfo(ApiCertKeyReqSVO vo);

	public int deleteSubCertInfo(ApiCertKeyReqSVO vo); /* 미사용 */

	/* API 인증키 등록 */
	public int createJwtKey(ApiCertKeyReqSVO vo);

}
