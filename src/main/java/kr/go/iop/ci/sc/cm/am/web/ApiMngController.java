/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.am.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.go.iop.ci.sc.cm.am.mapper.vo.AmDVO;
import kr.go.iop.ci.sc.cm.am.svc.ApiMngService;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmSVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.ApiVerSVO;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 표준API관리 화면처리를 위한 Controller.
 * 
 * @name_ko 표준API관리 컨트롤러
 * @author lsc, ksj
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "표준API 조회", description = "표준API 조회 API")
public class ApiMngController {

	private final ApiMngService apiMngService;

	@PostMapping("/v1/am/api/list")
	@Operation(summary = "표준API 목록 조회", description = "표준API 목록 조회")
	public ApiResponseVO getStndApiList(@RequestBody AmSVO svo) throws ParseException {
		log.debug("##### getStndApiList");

		HashMap<String, Object> rtnMap = new HashMap<>();

		int totalCnt = apiMngService.selectStndApiCnt(svo);
		rtnMap.put("totalCnt", totalCnt);
		List<AmDVO> list = apiMngService.selectStndApiList(svo);
		rtnMap.put(ConstantInfo.RESULT_LIST, list);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/am/api/info")
	@Operation(summary = "표준API 상세 조회", description = "표준API 상세 조회")
	public ApiResponseVO getStndApiInfo(@RequestBody AmSVO svo) throws ParseException {
		log.debug("##### getStndApiInfo");

		HashMap<String, Object> rtnMap = new HashMap<>();

		AmDVO api = apiMngService.selectStndApiInfo(svo);
		rtnMap.put(ConstantInfo.RESULT, api);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/am/api/create")
	@Operation(summary = "표준API 등록", description = "표준API 등록 요청")
	public ApiResponseVO createStndApi(@RequestBody AmSVO svo) throws ParseException {
		log.debug("##### createStndApi");

		HashMap<String, Object> rtnMap = new HashMap<>();
		try {
			int result = apiMngService.insertStndApi(svo);
			rtnMap.put(ConstantInfo.RESULT, result);
			rtnMap.put(ConstantInfo.NEXT_API_ID, svo.getApiId());
			return ResponseUtils.build(rtnMap);
		} catch (Exception e) {
			log.error("API 등록 중 예외 발생", e);
			rtnMap.put(ConstantInfo.RESULT, 0);
			rtnMap.put("message", e.getMessage());
			return ResponseUtils.build(rtnMap);
		}
	}

	@PostMapping("/v1/am/api/delete")
	@Operation(summary = "표준API 삭제", description = "표준API 삭제 요청")
	public ApiResponseVO deleteStndApi(@RequestBody List<AmSVO> list) throws ParseException {
		log.debug("##### deleteStndApi");
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		
		try {
			int count = 0;
			for (AmSVO svo : list) {
				count += apiMngService.deleteStndApi(svo);
			}
			if (count != list.size()) {
				throw new RuntimeException("삭제 대상 개수와 실제 삭제된 개수가 일치하지 않습니다.");
			}
			rtnMap.put(ConstantInfo.RESULT, 1);
		} catch (Exception e) {
			log.error("API 삭제 중 예외 발생", e);
			rtnMap.put(ConstantInfo.RESULT, 0);
		}
		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/am/api/update")
	@Operation(summary = "표준 API 수정", description = "버전은 수정 불가")
	public ApiResponseVO updateStndApi(@RequestBody AmSVO svo) throws ParseException {
		log.debug("##### updateStndApi");

		Map<String, Object> rtnMap = new HashMap<>();

		try {
			int resultCnt = apiMngService.updateStndApi(svo);
			rtnMap.put(ConstantInfo.RESULT_CNT, resultCnt);
			rtnMap.put(ConstantInfo.RESULT, 1);
			return ResponseUtils.build(rtnMap);
		} catch (Exception e) {
			log.error("API 수정 중 예외 발생", e);
			rtnMap.put(ConstantInfo.RESULT_CNT, 0);
			rtnMap.put("message", e.getMessage());
			return ResponseUtils.build(rtnMap);
		}
	}

	@PostMapping("/v1/am/api/create-ver")
	@Operation(summary = "버전 추가", description = "API 버전을 추가")
	public ApiResponseVO createStndApiVer(@RequestBody ApiVerSVO svo) throws ParseException {
		log.debug("##### createStndApiVer");
		
		
		Map<String, Object> rtnMap = new HashMap<>();

		List<Integer> updatedVersionList = apiMngService.insertStndApiVer(svo);
		rtnMap.put(ConstantInfo.RESULT, updatedVersionList);

		return ResponseUtils.build(rtnMap);
	}

	@PostMapping("/v1/am/api/list-ver")
	@Operation(summary = "API 버전 전체 목록 조회", description = "등록된 전체 API 버전 번호를 조회한다.")
	public ApiResponseVO getStndApiVerList() {
		log.info("##### getStndApiVerList");

		Map<String, Object> rtnMap = new HashMap<>();

		try {
			List<Integer> versions = apiMngService.selectStndApiVerList();
			rtnMap.put(ConstantInfo.RESULT_LIST, versions);
		} catch (Exception e) {
			log.error("API 버전 조회 실패", e);
			throw new RuntimeException("API 버전 조회 중 오류 발생");
		}
		return ResponseUtils.build(rtnMap);
	}
	
//	private AmSVO checkAuthInfo(AmSVO vo) {
//  
//	UserVO userVO = CurrentUserUtils.getCurrentUser2();
//
//	boolean checkAuth = CurrentUserUtils.checkUserAuth2(userVO, "C0010001");
//	
//	if(checkAuth) {
//  vo.setFrstCrtPrcrId(userVO.getAccountId());
//  vo.setLastChgPrcrId(userVO.getAccountId());
//	}else {
//		throw new ApiBizException(HttpStatus.UNAUTHORIZED, "시스템관리자 권한이 없습니다.");
//	}
//  
//  return vo;
//}

	@PostMapping("/v1/am/api/export-list.xlsx")
	@Operation(summary = "API 목록 엑셀 다운로드", description = "현재 목록 페이지 데이터 목록을 엑셀 다운로드한다")
	public void excelDownloadList(HttpServletRequest request, HttpServletResponse response, @RequestBody AmSVO vo) {
		apiMngService.selectStndApiListExcelDownload(request, response, vo, "current"); // 현재페이지만

	}

	@PostMapping("/v1/am/api/export-info.xlsx")
	@Operation(summary = "API 상세 엑셀 다운로드", description = "현재 목록 페이지 데이터 목록을 엑셀 다운로드한다")
	public void excelDownloadInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody AmSVO vo) {
		apiMngService.selectStndApiInfoExcelDownload(request, response, vo);

	}


}
