/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cp.cpm.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.ProdDVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.ReviewDVO;
import kr.go.iop.ci.sc.cp.cpm.svc.ProdService;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.ProdSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.ReviewSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrProdSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrSVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.SubscrProdDVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "카탈로그 상품 메인", description = "카탈로그 상품 메인을 이용하기 위한 API")
public class ProdController {

	private final ProdService prodService;

	/**
	 * 카탈로그 상품 목록 조회
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/inst/list-prod")
	@Operation(summary = "기관 구독 상품 목록 조회", description = "기관 구독 상품 목록 조회 API")
	public ApiResponseVO getInstSubscrProdList(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getInstSubscrProdList");
		
		List<ProdDVO> list = prodService.selectInstSubscrProdList(prodSVO);
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		
		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 인기 상품 목록 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/popular/list-prod")
	@Operation(summary = "인기 상품 목록 조회", description = "인기 상품 목록 조회 API")
	public ApiResponseVO getPopularProdList(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getPopularProdList");
		
		List<ProdDVO> list = prodService.selectPopularProdList(prodSVO);
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		
		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 사용 상품 목록 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/use/list-prod")
	@Operation(summary = "사용 상품 목록 조회", description = "사용 상품 목록 조회 API")
	public ApiResponseVO getUseProdList(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getUseProdList");
		
		List<ProdDVO> list = prodService.selectUseProdList(prodSVO);
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		
		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 목록 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/list")
	@Operation(summary = "상품 목록 조회", description = "상품 목록 조회 API")
	public ApiResponseVO getProdList(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getProdList");
		
		List<ProdDVO> list = prodService.selectProdList(prodSVO);
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		
		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 기관별 구독 상품 목록 조회 API
	 *
	 * @param prodSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */ 
	 @PostMapping("/v1/cpm/subscr/list-prod") 
	 @Operation(summary = "기관별 구독 상품 목록 조회", description = "기관별 구독 상품 목록 조회 API") 
	 public ApiResponseVO getInstBasedSubscrProdList(@RequestBody ProdSVO prodsvo) throws ParseException { 
		log.debug("getInstBasedSubscrProdList"); 
		
		List<ProdDVO> list = prodService.selectInstBasedSubscrProdList(prodsvo);
		
		Map<String, SubscrProdDVO> instMap = new LinkedHashMap<>();
		for (ProdDVO item : list) {
		    instMap.computeIfAbsent(item.getInstitutionCode(), k -> {
		        SubscrProdDVO dvo = new SubscrProdDVO();
		        dvo.setInstitutionCode(item.getInstitutionCode());
		        dvo.setInstitutionName(item.getInstitutionName());
		        dvo.setProducts(new ArrayList<>());
		        return dvo;
		    });

		    if (item.getSaasPrdctId() != null) {
		        instMap.get(item.getInstitutionCode()).getProducts().add(item);
		    }
		}
		
		List<SubscrProdDVO> groupedList = new ArrayList<>(instMap.values());
		
		HashMap<String, Object> rtnMap = new HashMap<>(); 
		rtnMap.put("resultList", groupedList); 
		
		return ResponseUtils.build(rtnMap); 
	 }

	/**
	 * 상품 상세 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/info")
	@Operation(summary = "상품 상세 조회", description = "상품 상세 조회 API")
	public ApiResponseVO getProdInfo(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getProdInfo");
		
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		
		ProdDVO prodDVO = prodService.selectProdInfo(prodSVO);
		rtnMap.put(ConstantInfo.RESULT_VO, prodDVO);
		
//		ProdDVO prdsbscInfo = prodService.selectPrdsbscInfo(prodSVO);
//		rtnMap.put(ConstantInfo.RESULT_CD, prdsbscInfo);
		
		
		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 구독 요청 API
	 * 
	 * @param subscrSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/create-subscr")
	@Operation(summary = "상품 구독 요청 API", description = "상품 구독 요청 API")
	public ApiResponseVO createProdSubscrReq(@RequestBody SubscrSVO subscrSVO) throws ParseException {
		log.debug("createProdSubscrReq");
		
		//TO-DO 구독요청시 관리자 권한 체크

		try {
			Map<String, Object> result = prodService.insertProdSubscrReq(subscrSVO);
			return ResponseUtils.build(HttpStatus.OK, result, "정상 처리되었습니다.");
		} catch (IllegalStateException e) {
			Map<String, Object> result = new HashMap<>();
			log.error("SaaS 요청/DB 작업수행중 오류", e);
			return ResponseUtils.build(HttpStatus.BAD_REQUEST, result, "구독 요청 실패");
		} catch (Exception e) {
			log.error("구독 요청 처리 중 내부 오류", e);
			return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, null, "구독 요청 실패");
		}
	}

	/**
	 * 상품 구독 취소 요청 API
	 * @param subscrSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/create-subscr-cancel")
	@Operation(summary = "상품 구독 취소 요청 API", description = "상품 구독 취소 요청 API")
	public ApiResponseVO createProdSubscrCancelReq(@RequestBody SubscrSVO subscrSVO) throws ParseException {
		log.debug("createProdSubscrCancelReq");

		//TO-DO 구독요청시 관리자 권한 체크
		
		try {
			Map<String, Object> result = prodService.insertProdSubscrCancelReq(subscrSVO);
			return ResponseUtils.build(HttpStatus.OK, result, "정상 처리되었습니다.");
		} catch (IllegalStateException e) {
			Map<String, Object> result = new HashMap<>();
			log.error("SaaS 요청/DB 작업수행중 오류", e);
			return ResponseUtils.build(HttpStatus.BAD_REQUEST, result, "구독 요청 실패");
		} catch (Exception e) {
			log.error("구독 요청 처리 중 내부 오류", e);
			return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, null, "구독 요청 실패");
		}
	}

	/**
	 * 상품 서비스 목록 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/list-service")
	@Operation(summary = "상품 서비스 목록 조회", description = "상품 서비스 목록 조회 API")
	public ApiResponseVO getProdServiceList(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getProdServiceList");
		
		List<ProdDVO> resultList = prodService.selectProdServiceList(prodSVO);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, resultList);

		return ResponseUtils.build(rtnMap);
	}
	/**
	 * 상품 서비스 목록 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	
	@PostMapping("/v1/cpm/prod/info-service")
	@Operation(summary = "상품 서비스 상세 조회", description = "상품 서비스 상세 조회 API")
	public ApiResponseVO getProdServiceInfo(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getProdServiceList");
		
		ProdDVO prodDVO = prodService.selectProdServiceInfo(prodSVO);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_VO, prodDVO);
		
		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 구독 기관 목록 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/list-Subscr-inst")
	@Operation(summary = "상품 구독 기관 목록 조회", description = "상품 구독 기관 목록 조회 API")
	public ApiResponseVO getProdSubscrInstList(@RequestBody ProdSVO prodSVO) throws ParseException {
		log.debug("getProdSubscrInstList");
		
		List<ProdDVO> resultList = prodService.selectProdSubscrInstList(prodSVO);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, resultList);
		

		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 구독 정보 조회 API
	 * 
	 * @param prodSVO <- SVO 별도 관리 시 변경 해야함
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/info-subscr")
	@Operation(summary = "상품 구독 정보 조회", description = "상품 구독 정보 조회 API")
	public ApiResponseVO getProdSubscrInfo(@RequestBody SubscrProdSVO subscrProdSVO) throws ParseException {
		log.debug("getProdSubscrInfo");

		SubscrProdDVO subscrProdDVO = prodService.selectProdSubscrInfo(subscrProdSVO);

		HashMap<String, Object> rtnMap = new HashMap<>();

		rtnMap.put(ConstantInfo.RESULT_VO, subscrProdDVO);

		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 리뷰 목록 조회 API
	 * 
	 * @param reviewSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/list-review")
	@Operation(summary = "상품 리뷰 목록 조회 API", description = "상품 리뷰 목록 조회 API")
	public ApiResponseVO getProdReviewList(@RequestBody ReviewSVO reviewSVO) throws ParseException {
		log.debug("getProdReviewList");

		List<ReviewDVO> list = prodService.selectProdReviewList(reviewSVO);

		ReviewDVO reviewDVO = prodService.selectProdReviewInfo(reviewSVO);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);

		rtnMap.put(ConstantInfo.RESULT_VO, reviewDVO);

		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 리뷰 등록 요청 API
	 * 
	 * @param reviewSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/create-review")
	@Operation(summary = "상품 리뷰 등록 요청 API", description = "상품 리뷰 등록 요청 API")
	public ApiResponseVO createProdReview(@RequestBody ReviewSVO reviewSVO) throws ParseException {
		log.debug("createProdReview");
		HashMap<String, Object> rtnMap = new HashMap<>();
		try {
			int resultCnt = prodService.insertProdReview(reviewSVO);
			rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
			return ResponseUtils.build(rtnMap);
		} catch (ApiBizException e) {
			rtnMap.put("resultMsg", e.getMessage());
			return ResponseUtils.build(rtnMap);
		}
	}

	/**
	 * 상품 리뷰 수정 요청 API
	 * 
	 * @param reviewSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/update-review")
	@Operation(summary = "상품 리뷰 수정 요청 API", description = "상품 리뷰 수정 요청 API")
	public ApiResponseVO updateProdReview(@RequestBody ReviewSVO reviewSVO) throws ParseException {
		log.debug("updateProdReview");

		int resultCnt = prodService.updateProdReview(reviewSVO);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);

		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 상품 리뷰 삭제 요청 API
	 * 
	 * @param reviewSVO
	 * @return ApiResponseVO
	 * @throws ParseException
	 */
	@PostMapping("/v1/cpm/prod/delete-review")
	@Operation(summary = "상품 리뷰 삭제 요청 API", description = "상품 리뷰 삭제 요청 API")
	public ApiResponseVO deleteProdReview(@RequestBody ReviewSVO reviewSVO) throws ParseException {
		log.debug("deleteProdReview");
		int resultCnt = prodService.deleteProdReview(reviewSVO);

		HashMap<String, Object> rtnMap = new HashMap<>();

		rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);

		return ResponseUtils.build(rtnMap);
	}

}
