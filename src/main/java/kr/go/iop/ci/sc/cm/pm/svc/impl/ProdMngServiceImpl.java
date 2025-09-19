/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pm.svc.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.go.iop.ci.sc.cm.pm.mapper.ProdMngMapper;
import kr.go.iop.ci.sc.cm.pm.mapper.vo.ProdMngDVO;
import kr.go.iop.ci.sc.cm.pm.svc.ProdMngService;
import kr.go.iop.ci.sc.cm.pm.svc.vo.ProdMngSVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.RequiredArgsConstructor;
/**
 * 상품관리 서비스 구현 클래스.
 * @name_ko 상품관리 서비스
 * @author pjh
 */
@Service("prodMngService")
@RequiredArgsConstructor
public class ProdMngServiceImpl implements ProdMngService {
	private final ProdMngMapper prodMngMapper;
	
	/* 상품관리 조회 */
	@Override
	public List<ProdMngDVO> selectProdList(ProdMngSVO vo){
		return prodMngMapper.selectProdList(vo);
	}
	
	/* 상품관리 조회 건수 */
	@Override
	public int selectProdCnt(ProdMngSVO vo){
		return prodMngMapper.selectProdCnt(vo);
	}
	
	/* 상품관리 상세조회 */
	@Override
	public ProdMngDVO selectProdInfo(ProdMngSVO vo){
		return prodMngMapper.selectProdInfo(vo);
	}
	
	/* 상품관리 상품ID 조회 */
	@Override
	public String selectProdId(ProdMngSVO vo){
		return prodMngMapper.selectProdId(vo);
	}
	
	/* 상품관리 상품등록 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertProd(ProdMngSVO vo){
		String saasPrdctId = selectProdId(vo);
        vo.setSaasPrdctId(saasPrdctId);

        int resultCnt = prodMngMapper.insertProd(vo);
        if (resultCnt <= 0) throw new RuntimeException("상품 등록 실패");
        vo.setApiVerSn(vo.getSaasPrdctApiVerSn());
        vo.setSrvrSeCd(ConstantInfo.TEST_OPS);

        List<ProdMngDVO> apiList = selectStdApiInfo(vo);
        for (ProdMngDVO apiVo : apiList) {
            ProdMngSVO insertVo = new ProdMngSVO();
            insertVo.setSaasPrdctId(vo.getSaasPrdctId());
            insertVo.setApiVerSn(vo.getApiVerSn());
            insertVo.setApiId(apiVo.getApiId());
            insertVo.setSrvrSeCd(vo.getSrvrSeCd());
            insertVo.setCmncRsltCd(ConstantInfo.TEST_WAIT);
            insertVo.setFrstCrtPrcrId(vo.getFrstCrtPrcrId());
            insertVo.setLastChgPrcrId(vo.getLastChgPrcrId());

            insertStdApi(insertVo);
            insertProdApiTest(insertVo);
        }
        
        
        insertCertKey(vo);
        
        return resultCnt;
    }

	/* 상품관리 상품수정 */
	@Override
	public int updateProd(ProdMngSVO vo){
		return prodMngMapper.updateProd(vo);
	}
	
	/* 상품관리 상품삭제 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteProd(ProdMngSVO vo){
		prodMngMapper.deleteService(vo);
		
		int resultCnt = prodMngMapper.deleteProd(vo);
		return resultCnt;
	}
	
	/* 상품관리 서비스 조회 */
	@Override
	public List<ProdMngDVO> selectStdApiInfo(ProdMngSVO vo) {
		return prodMngMapper.selectStdApiInfo(vo);
	}
	
	/*  상품표준API관계등록 */
	@Override
	public int insertStdApi(ProdMngSVO vo){
		return prodMngMapper.insertStdApi(vo);
	}

	/* 상품 API 통신 항목 등록(초기셋팅) */
	@Override
	public int insertProdApiTest(ProdMngSVO vo){
		return prodMngMapper.insertProdApiTest(vo);
	}

	/* 상품관리 서비스 조회 */
	@Override
	public List<ProdMngDVO> selectServiceList(ProdMngSVO vo) {
		return prodMngMapper.selectServiceList(vo);
	}
	
	/* 상품관리 서비스 상세조회 */
	@Override
	public ProdMngDVO selectServiceInfo(ProdMngSVO vo) {
		return prodMngMapper.selectServiceInfo(vo);
	}

	/* 상품관리 서비스등록 */
	@Override
	public int insertService(ProdMngSVO vo) {
		return prodMngMapper.insertService(vo);
	}

	/* 상품관리 서비스수정 */
	@Override
	public int updateService(ProdMngSVO vo) {
		return prodMngMapper.updateService(vo);
	}

	/* 상품관리 서비스삭제 */
	@Override
	public int deleteService(ProdMngSVO vo) {
		return prodMngMapper.deleteService(vo);
	}
	
	/* API 최종버전 조회 */
	@Override
	public int selectMaxStndApiVer() {
		return prodMngMapper.selectMaxStndApiVer();
	}
	
	/*상품 인증키 등록 */
	@Override
	public int insertCertKey(ProdMngSVO vo) {
		return prodMngMapper.insertCertKey(vo);
	}
	
	/* 상품명 중복검사 */
	@Override
	public int selectPrdctNmDupeTest(ProdMngSVO vo) {
		return prodMngMapper.selectPrdctNmDupeTest(vo);
	}

}
