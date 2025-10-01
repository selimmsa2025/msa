/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cp.cpm.svc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.cp.cpm.mapper.ProdMapper;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.ProdDVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.ReviewDVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.SubscrProdDVO;
import kr.go.iop.ci.sc.cp.cpm.svc.ProdService;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.ProdSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.ReviewSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrProdSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrSVO;
import kr.go.iop.ci.sc.feignapi.IopToSaaSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 카탈로그 상품 포탈 기능을 위한 구현 클래스
 * 
 * @name_ko 카탈로그 상품 포탈 구현 클래스
 * @author selim
 */
@Slf4j
@Service("prodService")
@RequiredArgsConstructor
public class ProdServiceImpl implements ProdService {

	private final ProdMapper prodMapper;
	
	private final IopToSaaSClient iopToSaaSClient;

	/* psy s */
	@Override
	public ProdDVO selectProdInfo(ProdSVO prodSVO) {
		return prodMapper.selectProdInfo(prodSVO);
	}

	@Override
	public List<ProdDVO> selectProdServiceList(ProdSVO prodSVO) {
		return prodMapper.selectProdServiceList(prodSVO);
	}

	@Override
	public ProdDVO selectProdServiceInfo(ProdSVO prodSVO) {
		return prodMapper.selectProdServiceInfo(prodSVO);
	}

	@Override
	public List<ProdDVO> selectProdSubscrInstList(ProdSVO prodSVO) {
		return prodMapper.selectProdSubscrInstList(prodSVO);
	}

	/* psy e */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertProdSubscrReq(SubscrSVO subscrSVO) {

		boolean isDbSaved = false;
		boolean isSaasCallSuccess = false;
		
		//구독 요청일때는 구독시작일자 종료일자 null
		if ("A0040001".equals(subscrSVO.getPrdsbscSttsCd())) {
			subscrSVO.setPrdsbscBgngYmd("");
			subscrSVO.setPrdsbscEndYmd("");
		}

		// DB insert
		prodMapper.insertProdSubscrReq(subscrSVO);
		int inserted = prodMapper.insertProdSubscrHistoryReq(subscrSVO);
		isDbSaved = inserted > 0;
		if (!isDbSaved) {
			log.error("DB 저장 실패");
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//TODO IOP-ID 추가
		subscrSVO.setIopId("");

		Map<String, Object> saasResp;
		try {
			saasResp = iopToSaaSClient.createProdSubscrReq(subscrSVO);

			JsonNode resultData = new ObjectMapper().valueToTree(saasResp.get("resultData"));
			log.debug("resultData={}",resultData);
			if (!resultData.isNull()) {
				isSaasCallSuccess = true;
			}
			
			if (!isSaasCallSuccess) {
				log.error("SaaS 처리 실패: resultData 없음");
				throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 최종 응답
			Map<String, Object> result = new HashMap<>();
			result.put("resultCnt", inserted);
			result.put("resultData", saasResp.get("resultData")); 
			return result;

		} catch (FeignException e) {
			log.error("SaaS 호출 실패");
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertProdSubscrCancelReq(SubscrSVO subscrSVO) {
		
		boolean isDbSaved = false;
		boolean isSaasCallSuccess = false;

		// DB insert
		prodMapper.updateProdSubscrReq(subscrSVO);
		int inserted = prodMapper.insertProdSubscrHistoryReq(subscrSVO);
		isDbSaved = inserted > 0;
		if (!isDbSaved) {
			log.error("DB 저장 실패");
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//TODO IOP-ID 추가
		subscrSVO.setIopId("");

		Map<String, Object> saasResp;
		try {
			saasResp = iopToSaaSClient.createProdSubscrCancelReq(subscrSVO);

			JsonNode resultData = new ObjectMapper().valueToTree(saasResp.get("resultData"));
			if (!resultData.isNull()) {
				isSaasCallSuccess = true;
			}
			
			if (!isSaasCallSuccess) {
				log.error("SaaS 처리 실패: resultData 없음");
				throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 최종 응답
			Map<String, Object> result = new HashMap<>();
			result.put("resultCnt", inserted);
			result.put("resultData", saasResp.get("resultData")); 
			return result;

		} catch (FeignException e) {
			log.error("SaaS 호출 실패");
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public SubscrProdDVO selectProdSubscrInfo(SubscrProdSVO subscrProdSVO) {
		return prodMapper.selectProdSubscrInfo(subscrProdSVO);
	}

	@Override
	public List<ReviewDVO> selectProdReviewList(ReviewSVO reviewSVO) {
		return prodMapper.selectProdReviewList(reviewSVO);
	}

	@Override
	public ReviewDVO selectProdReviewInfo(ReviewSVO reviewSVO) {
		return prodMapper.selectProdReviewInfo(reviewSVO);
	}

	@Override
	public int insertProdReview(ReviewSVO reviewSVO) {
		if (prodMapper.existProdReviewInfo(reviewSVO) > 0) {
			throw new ApiBizException(HttpStatus.CONFLICT, "이미 작성된 리뷰가 있습니다.");
		} else {
			return prodMapper.insertProdReview(reviewSVO);
		}
	}

	@Override
	public int updateProdReview(ReviewSVO reviewSVO) {
		return prodMapper.updateProdReview(reviewSVO);
	}

	@Override
	public int deleteProdReview(ReviewSVO reviewSVO) {
		return prodMapper.deleteProdReview(reviewSVO);
	}

	@Override
	public List<ProdDVO> selectInstBasedSubscrProdList(ProdSVO prodsvo) {
		return prodMapper.selectInstBasedSubscrProdList(prodsvo);
	}

	@Override
	public List<ProdDVO> selectInstSubscrProdList(ProdSVO prodSVO) {
		return prodMapper.selectInstSubscrProdList(prodSVO);
	}

	@Override
	public List<ProdDVO> selectPopularProdList(ProdSVO prodSVO) {
		return prodMapper.selectPopularProdList(prodSVO);
	}

	@Override
	public List<ProdDVO> selectUseProdList(ProdSVO prodSVO) {
		return prodMapper.selectUseProdList(prodSVO);
	}

	@Override
	public List<ProdDVO> selectProdList(ProdSVO prodSVO) {
		return prodMapper.selectProdList(prodSVO);
	}

}
