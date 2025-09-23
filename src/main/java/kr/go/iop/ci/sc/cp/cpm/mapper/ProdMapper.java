/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cp.cpm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.go.iop.ci.sc.cmmn.file.mapper.vo.CatalogFileDVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.ProdDVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.ReviewDVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.ProdSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.ReviewSVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrSVO;
import kr.go.iop.ci.sc.cp.cpm.mapper.vo.SubscrProdDVO;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrProdSVO;

/**
 * 카탈로그 상품 포탈 기능을 위한 mapper 클래스
 * 
 * @name_ko 카탈로그 상품 포탈 mapper.
 * @author selim
 */
@Mapper
public interface ProdMapper {

	/**
	 * 상품 상세 조회
	 * 
	 * @param prodSVO
	 * @return 상품 상세 조회
	 */
	public ProdDVO selectProdInfo(ProdSVO prodSVO);
	
	/**
	 * 
	 * 
	 * @param CatalogFileDVO
	 * @return 첨부파일 조회
	 */
	public List<CatalogFileDVO> selectFileInfo(ProdSVO prodSVO);
	

	/**
	 * 상품 구독 기관 목록 조회
	 * 
	 * @param prodSVO
	 * @return 상품 상세 조회
	 */
	public List<ProdDVO> selectProdSubscrInstList(ProdSVO prodSVO);
	
	
	/**
	 * 상품 서비스 조회
	 * 
	 * @param prodSVO
	 * @return 상품 서비스 조회
	 */
	public List<ProdDVO> selectProdServiceList(ProdSVO prodSVO);

	/**
	 * 상품 서비스 상세 조회
	 * 
	 * @param prodSVO
	 * @return 상품 서비스 상세 조회
	 */
	public ProdDVO selectProdServiceInfo(ProdSVO prodSVO);
	
	/**
	 * 상품 구독 요청 (구독이력)
	 * 
	 * @param subscrSVO
	 * @return 상품 구독 정보 조회
	 */
	public int insertProdSubscrHistoryReq(SubscrSVO subscrSVO);

	/**
	 * 상품 구독 요청 (구독내역)
	 * 
	 * @param subscrSVO
	 * @return 상품 구독 요청 개수
	 */
	public int insertProdSubscrReq(SubscrSVO subscrSVO);

	/**
	 * 상품 구독 취소 요청 (이력 추가)
	 * 
	 * @param subscrSVO
	 * @return 상품 구독 취소 요청 개수
	 */
	public int insertProdSubscrCancelReq(SubscrSVO subscrSVO);
	
	/**
	 * 상품 구독 취소 요청 (냬역 업데이트)
	 * 
	 * @param subscrSVO
	 * @return 
	 */
	public void updateProdSubscrReq(SubscrSVO subscrSVO);
	
	/**
	 * 상품 구독 정보 조회
	 * 
	 * @param subscrProdSVO
	 * @return 상품 구독 정보 조회
	 */
	public SubscrProdDVO selectProdSubscrInfo(SubscrProdSVO subscrProdSVO);
	
	/**
	 * 상품 리뷰 목록 조회
	 * 
	 * @param reviewSVO
	 * @return 상품 리뷰 목록
	 */
	public List<ReviewDVO> selectProdReviewList(ReviewSVO reviewSVO);

	/**
	 * 상품 리뷰 정보 조회 (개수, 평균)
	 * 
	 * @param reviewSVO
	 * @return 상품 리뷰 정보 조회 (개수, 평균)
	 */
	public ReviewDVO selectProdReviewInfo(ReviewSVO reviewSVO);

	/**
	 * 상품 리뷰 등록 요청
	 * 
	 * @param reviewSVO
	 * @return 상품 리뷰 등록 개수
	 */
	public int insertProdReview(ReviewSVO reviewSVO);

	/**
	 * 상품 리뷰 수정 요청
	 * 
	 * @param reviewSVO
	 * @return 상품 리뷰 수정 개수
	 */
	public int updateProdReview(ReviewSVO reviewSVO);

	/**
	 * 상품 리뷰 삭제 요청
	 * 
	 * @param reviewSVO
	 * @return 상품 리뷰 삭제 개수
	 */
	public int deleteProdReview(ReviewSVO reviewSVO);

	/**
	 * 리뷰 조회 (리뷰 존재 여부 확인)
	 * 
	 * @param reviewSVO
	 * @return 리뷰 개수
	 */
	public int existProdReviewInfo(ReviewSVO reviewSVO);

	/**
	 * 기관별 구독 상품 목록 조회
	 * 
	 * @param prodSVO
	 * @return 기관별 구독 상품 목록
	 */
	public List<ProdDVO> selectInstBasedSubscrProdList(ProdSVO prodSVO);

	/**
	 * 기관 구독 상품 목록 조회
	 * 
	 * @param SubscrProdSVO
	 * @return 기관 구독 상품 목록 조회
	 */
	public List<ProdDVO> selectInstSubscrProdList(ProdSVO prodSVO);

	/**
	 * 인기 상품 목록 조회
	 * 
	 * @param prodSVO <- 확인
	 * @return 인기 상품 목록 조회
	 */
	public List<ProdDVO> selectPopularProdList(ProdSVO prodSVO);

	/**
	 * 사용 상품 목록 조회
	 * 
	 * @param prodSVO <- 확인
	 * @return 사용 상품 목록 조회
	 */
	public List<ProdDVO> selectUseProdList(ProdSVO prodSVO);

	/**
	 * 상품 목록 조회
	 * 
	 * @param prodSVO <- 확인
	 * @return 상품 목록 조회
	 */
	public List<ProdDVO> selectProdList(ProdSVO prodSVO);



	

	



}
