/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.pm.web;

import java.util.HashMap;
import java.util.List;


import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cm.pm.mapper.vo.ProdMngDVO;
import kr.go.iop.ci.sc.cm.pm.svc.ProdMngService;
import kr.go.iop.ci.sc.cm.pm.svc.vo.ProdMngSVO;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.cmmn.utils.CurrentUserUtils;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.cmmn.vo.UserVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 상품관리 화면처리를 위한 Controller.
 * @name_ko 상품관리 컨트롤러
 * @author pjh
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "상품 관리", description = "상품관리를 이용하기 위한 API2")
public class ProdMngController {
	
	private final ProdMngService prodMngService;
	
	@PostMapping("/v1/pm/prod/list")
	@Operation(summary = "상품 관리 상품목록", description = "상품관리 정보를 반환")	
	public ApiResponseVO getProdList(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.info("######## getProdList");

		checkAuthInfo(vo);
		
	    List<ProdMngDVO> list = prodMngService.selectProdList(vo);
	    int cnt = prodMngService.selectProdCnt(vo);
	    
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_LIST, list);
	    rtnMap.put(ConstantInfo.RESULT_CNT, cnt);
	    
	    log.info("######## getProdList 2");
	    
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/info")
	@Operation(summary = "상품 관리 상품상세", description = "상품관리 상세 정보를 반환")	
	public ApiResponseVO getProdInfo(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("getProdInfo");
		 
		checkAuthInfo(vo);
	        
		ProdMngDVO ProdMngDVO = prodMngService.selectProdInfo(vo);

	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_VO, ProdMngDVO);
	    
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/create")
	@Operation(summary = "상품 관리 상품등록", description = "상품관리 상품등록")	
	public ApiResponseVO createProd(@Validated @RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("createProd");

		checkAuthInfo(vo);
		
		int checkValidation = prodMngService.selectPrdctNmDupeTest(vo);
		if(checkValidation > 0) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "해당 제조사에 같은 상품명이 이미 있습니다.");
		}
		
		int maxApiVer = prodMngService.selectMaxStndApiVer();
		int apiVer = vo.getSaasPrdctApiVerSn();
		if(apiVer != maxApiVer) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "상품 버전이 최신 버전과 일치하지 않습니다.");
			
		}

		int resultCnt = prodMngService.insertProd(vo);

	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
    
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/update")
	@Operation(summary = "상품 관리 상품수정", description = "상품관리 상품수정")	
	public ApiResponseVO updateProd(@Validated @RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("updateProd");

		checkAuthInfo(vo);
		
		int checkValidation = prodMngService.selectPrdctNmDupeTest(vo);
		if(checkValidation > 0) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "해당 제조사에 같은 상품명이 이미 있습니다.");
		}
   
	    int resultCnt = prodMngService.updateProd(vo);
	    
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/delete")
	@Operation(summary = "상품 관리 상품삭제", description = "상품관리 상품삭제")	
	public ApiResponseVO deleteProd(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("deleteProd");

		checkAuthInfo(vo);
	     
	    //TO-BE 상품 삭제 시 서비스 일괄삭제 할지 검토
	    vo.setSrvcId("");
	     
	    int resultCnt = prodMngService.deleteProd(vo);
	    
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/list-service")
	@Operation(summary = "상품 관리 서비스목록", description = "상품관리 서비스 정보를 반환")	
	public ApiResponseVO getServiceList(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("getServiceList");
		
		checkAuthInfo(vo);

		List<ProdMngDVO> list = prodMngService.selectServiceList(vo);
	     
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_LIST, list);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/info-service")
	@Operation(summary = "상품 관리 서비스상세", description = "상품관리 서비스 상세 정보를 반환")	
	public ApiResponseVO getServiceInfo(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("getServiceInfo");
		 
		checkAuthInfo(vo);
		
		ProdMngDVO ProdMngDVO = prodMngService.selectServiceInfo(vo);
	        
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_VO, ProdMngDVO);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/create-service")
	@Operation(summary = "상품 관리 서비스등록", description = "상품관리 서비스등록")	
	public ApiResponseVO createService(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("createService");

		checkAuthInfo(vo);
		
		ProdMngDVO checkValidation = prodMngService.selectProdInfo(vo);
		
		if (checkValidation != null) {
		    String saasPrdctTypeCd = checkValidation.getSaasPrdctTypeCd();
		    if (!"A0020002".equals(saasPrdctTypeCd)) {
		        throw new ApiBizException(HttpStatus.BAD_REQUEST, "패키지형 상품이 아닙니다.");
		    }
		} else {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "해당 상품이 없습니다.");
		}

		int resultCnt = prodMngService.insertService(vo);
	     
	    HashMap<String, Object> rtnMap = new HashMap<>();
	     
	    rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/update-service")
	@Operation(summary = "상품 관리 서비스수정", description = "상품관리 서비스수정")	
	public ApiResponseVO updateService(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("updateService");

		checkAuthInfo(vo);
	        
	    int resultCnt = prodMngService.updateService(vo);
	     
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/delete-service")
	@Operation(summary = "상품 관리 서비스삭제", description = "상품관리 서비스삭제")	
	public ApiResponseVO deleteService(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("deleteService");

		checkAuthInfo(vo);
	        
	    int resultCnt = prodMngService.deleteService(vo);
	     
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/info-ver")
	@Operation(summary = "API 최종버전 조회", description = "API 최종버전 조회")	
	public ApiResponseVO getMaxStndApiVer() throws ParseException{
		
		log.debug("selectMaxStndApiVer");
	        
	    int result = prodMngService.selectMaxStndApiVer();
	     
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT, result);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
	@PostMapping("/v1/pm/prod/dupe-test")
	@Operation(summary = "상품명 중복검사", description = "상품명 중복검사")	
	public ApiResponseVO getPrdctNmDupeTest(@RequestBody ProdMngSVO vo) throws ParseException{
		
		log.debug("selectPrdctNmDupeTest");
		
		checkAuthInfo(vo);
	        
	    int resultCnt = prodMngService.selectPrdctNmDupeTest(vo);
	    String result;
	    if(resultCnt>0) {
	    	result = ConstantInfo.RTN_STTS_FAIL;
	    }else {
	    	result = ConstantInfo.RTN_STTS_SUCCESS;
	    }
	    
	    HashMap<String, Object> rtnMap = new HashMap<>();
	    rtnMap.put(ConstantInfo.RESULT, result);
	        
	    return ResponseUtils.build(rtnMap);
		
	}
	
//	private ProdMngSVO checkAuthInfo(ProdMngSVO vo) {
//        
//		UserVO userVO = CurrentUserUtils.getCurrentUser2();
//
//		boolean checkAuth = CurrentUserUtils.checkUserAuth2(userVO, "C0010001");
//		
//		if(checkAuth) {
//        vo.setFrstCrtPrcrId(userVO.getAccountId());
//        vo.setLastChgPrcrId(userVO.getAccountId());
//		}else {
//			throw new ApiBizException(HttpStatus.UNAUTHORIZED, "시스템관리자 권한이 없습니다.");
//		}
//        
//        return vo;
//    }
	
	private ProdMngSVO checkAuthInfo(ProdMngSVO vo) {
        
        
        vo.setFrstCrtPrcrId("sysadmin");
        vo.setLastChgPrcrId("sysadmin");
        
        return vo;
    }

}
