/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pc.svc.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.go.iop.ci.sc.cm.pc.mapper.ProdCertMapper;
import kr.go.iop.ci.sc.cm.pc.mapper.vo.ApiCertKeyDVO;
import kr.go.iop.ci.sc.cm.pc.mapper.vo.CertInfoDVO;
import kr.go.iop.ci.sc.cm.pc.svc.ProdCertService;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.ApiCertKeyReqSVO;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.CertInfoSVO;
import lombok.RequiredArgsConstructor;

/**
 * API인증관리 서비스 구현 클래스.
 * 
 * @name_ko API인증관리 서비스
 * @author lsc
 */
@Service("catalogPrtlSrchService")
@RequiredArgsConstructor
public class ProdCertServiceImpl implements ProdCertService {

	private final ProdCertMapper apiCertKeyMapper;

	/* API 인증키 목록 조회 */
	@Override
	public List<ApiCertKeyDVO> selectApiCertKeyList(ApiCertKeyReqSVO vo) {
		return apiCertKeyMapper.selectApiCertKeyList(vo);
	}

	/* API 인증키 목록 조회 건수 */
	@Override
	public int selectApiCertKeyListCnt(ApiCertKeyReqSVO vo) {
		return apiCertKeyMapper.selectApiCertKeyListCnt(vo);
	}

	/* 구독 인증키 목록 조회 */
	@Override
	public List<ApiCertKeyDVO> selectSubCertKeyList(ApiCertKeyReqSVO vo) {
		return apiCertKeyMapper.selectSubCertKeyList(vo);
	}

	/* 구독 인증키 목록 조회 건수 */
	@Override
	public int selectSubCertKeyListCnt(ApiCertKeyReqSVO vo) {
		return apiCertKeyMapper.selectSubCertKeyListCnt(vo);
	}

	/* API 인증키 상세 및 API 구독 인증키 상세 */
	@Override
	public ApiCertKeyDVO selectApiCertKeydetail(ApiCertKeyReqSVO vo) {

		ApiCertKeyDVO dvo = new ApiCertKeyDVO();
		if (vo.getGubun().equals("sub")) { // front - PcSubListBoard.jsx
			dvo = apiCertKeyMapper.selectSubCertKeyDetail(vo);
			List<CertInfoDVO> paramList = apiCertKeyMapper.selectSubCertInfoList(vo);
			dvo.setParamList(paramList);
			return dvo;
		} else {
			dvo = apiCertKeyMapper.selectApiCertKeyDetail(vo);
			List<CertInfoDVO> paramList = apiCertKeyMapper.selectApiCertInfoList(vo);
			dvo.setParamList(paramList);
			return dvo;
		}

	}

	/* API 인증키 정보 수정 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateApiCertInfo(ApiCertKeyReqSVO vo) {
		deleteApiCertInfo(vo);
		List<CertInfoSVO> paramList = vo.getParamList();

		// certSeCd 별로 그룹핑해서 중복 체크
		Map<String, Set<String>> duplicateCheckMap = new HashMap<>();

		for (CertInfoSVO svo : paramList) {
			String certSeCd = svo.getCertSeCd();
			String authkeyNm = svo.getAuthkeyNm();

			duplicateCheckMap.putIfAbsent(certSeCd, new HashSet<>());
			// 같은 certSeCd(인증구분코드 : 테스트인증, 구독요청인증) 그룹 내에서 중복 검사
			if (!duplicateCheckMap.get(certSeCd).add(authkeyNm)) {
				return -2; // 동일 그룹 내 중복 → 에러
			}
		}

		try {
			for (CertInfoSVO svo : paramList) {
				apiCertKeyMapper.insertApiCertInfo(svo);
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/* API 인증키 정보 삭제 */
	@Override
	public int deleteApiCertInfo(ApiCertKeyReqSVO vo) {
		return apiCertKeyMapper.deleteApiCertInfo(vo);
	}

	/* 구독 인증키 정보 수정 - 미사용 */
	/* 구독 인증키 정보 삭제 - 미사용 */

	/* 상품관리 상품등록 */
	@Override
	public int createJwtKey(ApiCertKeyReqSVO vo) {
		return apiCertKeyMapper.createJwtKey(vo);
	}

}
