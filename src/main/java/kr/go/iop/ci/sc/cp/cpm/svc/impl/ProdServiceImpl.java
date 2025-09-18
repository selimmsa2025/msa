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

/**
 * 카탈로그 상품 포탈 기능을 위한 구현 클래스
 * 
 * @name_ko 카탈로그 상품 포탈 구현 클래스
 * @author selim
 */
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

		String dbMsg = "";
		String saasMsg = "";
		boolean dbOk = false;
		boolean saasOk = false;

		// DB insert
		prodMapper.insertProdSubscrReq(subscrSVO);
		int inserted = prodMapper.insertProdSubscrHistoryReq(subscrSVO);
		dbOk = inserted > 0;
		dbMsg = dbOk ? "DB 저장 성공" : "DB 저장 실패";
		if (!dbOk) {
			throw new IllegalStateException(dbMsg);
		}

		Map<String, Object> saasResp;
		try {
			saasResp = iopToSaaSClient.createProdSubscrReq(subscrSVO);

			String status = saasResp.get("status") != null ? saasResp.get("status").toString() : "";
			String resultCd = saasResp.get("resultCd") != null ? saasResp.get("resultCd").toString() : "";
			String resultMsg = saasResp.get("resultMsg") != null ? saasResp.get("resultMsg").toString() : "SaaS 응답 메시지 없음";

			saasOk = "SUCCESS".equalsIgnoreCase(status) || "200".equals(resultCd);
			saasMsg = (saasOk ? "SaaS 호출 성공: " : "SaaS 호출 실패: ") + resultMsg;

			// 최종 성공
			if (!(dbOk && saasOk)) {
				throw new IllegalStateException("부분 실패(DB:" + dbOk + ", SaaS:" + saasOk + ")");
			}

			// 최종 응답
			Map<String, Object> result = new HashMap<>();
			result.put("resultCnt", inserted);
			result.put("resultData", saasResp.get("resultData")); 
			result.put("status", status);
			result.put("resultCd", resultCd);
			return result;

		} catch (FeignException e) {
			saasMsg = "SaaS 호출 실패: " + e.status();
			throw new IllegalStateException(saasMsg, e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertProdSubscrCancelReq(SubscrSVO subscrSVO) {
		
		String dbMsg = "";
		String saasMsg = "";
		boolean dbOk = false;
		boolean saasOk = false;

		// DB insert
		prodMapper.updateProdSubscrReq(subscrSVO);
		int inserted = prodMapper.insertProdSubscrHistoryReq(subscrSVO);
		dbOk = inserted > 0;
		dbMsg = dbOk ? "DB 저장 성공" : "DB 저장 실패";
		if (!dbOk) {
			throw new IllegalStateException(dbMsg);
		}

		Map<String, Object> saasResp;
		try {
			saasResp = iopToSaaSClient.createProdSubscrReq(subscrSVO);

			String status = saasResp.get("status") != null ? saasResp.get("status").toString() : "";
			String resultCd = saasResp.get("resultCd") != null ? saasResp.get("resultCd").toString() : "";
			String resultMsg = saasResp.get("resultMsg") != null ? saasResp.get("resultMsg").toString() : "SaaS 응답 메시지 없음";

			saasOk = "SUCCESS".equalsIgnoreCase(status) || "200".equals(resultCd);
			saasMsg = (saasOk ? "SaaS 호출 성공: " : "SaaS 호출 실패: ") + resultMsg;

			// 최종 성공
			if (!(dbOk && saasOk)) {
				throw new IllegalStateException("부분 실패(DB:" + dbOk + ", SaaS:" + saasOk + ")");
			}

			// 최종 응답
			Map<String, Object> result = new HashMap<>();
			result.put("resultCnt", inserted);
			result.put("resultData", saasResp.get("resultData")); 
			result.put("status", status);
			result.put("resultCd", resultCd);
			return result;

		} catch (FeignException e) {
			saasMsg = "SaaS 호출 실패: " + e.status();
			throw new IllegalStateException(saasMsg, e);
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
