/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pc.web;

import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cm.pc.mapper.vo.ApiCertKeyDVO;
import kr.go.iop.ci.sc.cm.pc.svc.ProdCertService;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.ApiCertKeyReqSVO;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API 인증 관리 화면처리를 위한 Controller.
 * 
 * @name_ko 표준API관리 컨트롤러
 * @author lsc
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "카탈로그 관리", description = "API 인증키")
public class ProdCertController {

	private final ProdCertService apiCertKeyService;

	@PostMapping("/v1/pc/prod/list-cert-key")
	@Operation(summary = "통신테스트 인증목록 조회", description = "통신테스트 인증키 목록 조회")
	public ApiResponseVO getCertKeyList(@RequestBody ApiCertKeyReqSVO vo) throws ParseException {

		log.debug("##### getCertKeyList");

		List<ApiCertKeyDVO> list = apiCertKeyService.selectApiCertKeyList(vo);
		int cnt = apiCertKeyService.selectApiCertKeyListCnt(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		rtnMap.put(ConstantInfo.RESULT_CNT, cnt);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/pc/prod/sub-cert-key")
	@Operation(summary = "구독상품 인증목록 조회", description = "구독상품 인증키 목록 조회")
	public ApiResponseVO getSubCertKeyList(@RequestBody ApiCertKeyReqSVO vo) throws ParseException {

		log.debug("##### getSubCertKeyList");

		List<ApiCertKeyDVO> list = apiCertKeyService.selectSubCertKeyList(vo);
		int cnt = apiCertKeyService.selectSubCertKeyListCnt(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		rtnMap.put(ConstantInfo.RESULT_CNT, cnt);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/pc/prod/info-cert-key")
	@Operation(summary = "API 인증키 상세 조회", description = "[공통]API 인증키 상세 조회 - 통신테스트, 구독상품")
	public ApiResponseVO selectApiCertKeydetail(@RequestBody ApiCertKeyReqSVO vo) throws ParseException {

		log.debug("##### selectApiCertKeydetail");

		ApiCertKeyDVO apiCertKeyDVO = apiCertKeyService.selectApiCertKeydetail(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_VO, apiCertKeyDVO);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/pc/prod/update-cert-key")
	@Operation(summary = "API 인증키 정보 수정", description = "[공통]API 인증키 정보 수정 - 통신테스트(사용), 구독상품(미사용)")
	public ApiResponseVO createApiCertInfo(@RequestBody ApiCertKeyReqSVO vo) throws ParseException {

		log.debug("##### createApiCertInfo");

		int resultCnt = apiCertKeyService.updateApiCertInfo(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/pc/prod/delete-cert-key")
	@Operation(summary = "API 인증키 정보 삭제", description = "[공통]API 인증키 정보 삭제 - 통신테스트(사용), 구독상품(미사용)")
	public ApiResponseVO deleteApiCertInfo(@RequestBody ApiCertKeyReqSVO vo) throws ParseException {

		log.debug("##### deleteApiCertInfo");

		int resultCnt = apiCertKeyService.deleteApiCertInfo(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/ac/prod/create-jwt-key")
	@Operation(summary = "API 인증키 등록", description = "API 인증키 등록 요청")
	public ApiResponseVO createJwtKey(@RequestBody ApiCertKeyReqSVO vo) throws ParseException {
		log.debug("##### createJwtKey");

//		vo.setGdsId(null);
		vo.setSrvrSeCd(null);
		vo.setAplyKey(null);
		vo.setAplySrctKey(null);
		vo.setJsonWebTokenKey(null);
		vo.setCertKey(null);
		vo.setCertPrgrsStpCd(null);
		vo.setApiCommScsYn(null);
		vo.setFrstCrtDt(null);
		vo.setFrstCrtPrcrId(null);
		vo.setLastChgDt(null);
		vo.setLastChgPrcrId(null);

		HashMap<String, Object> rtnMap = new HashMap<>();
		try {

			int result = apiCertKeyService.createJwtKey(vo);

			rtnMap.put(ConstantInfo.RESULT, result);
			return ResponseUtils.build(rtnMap);

		} catch (Exception e) {
			log.error("API 인증키 등록 중 예외 발생", e);
			rtnMap.put(ConstantInfo.RESULT, 0);
			rtnMap.put("message", e.getMessage());
			return ResponseUtils.build(rtnMap);
		}

	}

}
