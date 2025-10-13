/*
 *  Copyright (c) 2025 Intelligent On-nara BPS Platform.
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.at.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestDetailListDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestPrdctDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestProdDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.PrdctApiCmncRsltDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.StdApiDVO;
import kr.go.iop.ci.sc.cm.at.svc.ApiTestService;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestPrdctSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestSaveRstSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.PrdctApiCmncRsltSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.StdApiSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.TestExecutionSVO;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SaaS 등록 테스트 관리를 위한 Controller.
 * @name_ko SaaS 등록 테스트 관리 컨트롤러
 * @author	hyy
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SaaS 등록 테스트 관리", description = "SaaS 등록 테스트 관리 API")
public class ApiTestController {

	private final ApiTestService apiTestService;

	
	@GetMapping("/v1/at/prod/list-api-test")
	@Operation(summary = "SaaS 상품 테스트 목록 조회", description = "SaaS 상품 테스트 목록 조회 API")
	public ApiResponseVO getProdApiTestList(@ModelAttribute ApiTestSVO req) {
		
		List<ApiTestDVO> list = apiTestService.selectProdApiTestList(req);
		int totalCnt = apiTestService.selectApiTestTot(req);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		rtnMap.put(ConstantInfo.RESULT_CNT, totalCnt);
		
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/update-catalog")
	@Operation(summary = "카탈로그 등록", description = "카탈로그 등록 API")
	public ApiResponseVO updateCatalog(@RequestBody ApiTestPrdctSVO req) {
		HashMap<String, Object> rtnMap = new HashMap<>();
		int resultCount = apiTestService.updateCatalog(req);
		
		rtnMap.put(ConstantInfo.RESULT_CNT, resultCount);
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/info-api-test")
	@Operation(summary = "SaaS 상품 테스트 상세 조회", description = "SaaS 상품 테스트 상세 조회 API")
	public ApiResponseVO getProdApiTestInfo(@RequestBody ApiTestPrdctSVO req) {
		HashMap<String, Object> rtnMap = new HashMap<>();
		ApiTestPrdctDVO info = apiTestService.selectProdApiTestInfo(req);
		rtnMap.put(ConstantInfo.RESULT_INFO, info);
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/create-api-test")
	@Operation(summary = "SaaS 상품 테스트 등록", description = "SaaS 상품 테스트 등록 API")
	public ApiResponseVO createProdApiTest(@RequestBody @Valid ApiTestSaveRstSVO req) {
		int upsertCount = apiTestService.insertProdApiTest(req);
		// 테스트 완료 처리
		int completedCount = apiTestService.updateTestStatus(req);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_CNT, (upsertCount+ completedCount));
		return ResponseUtils.build(rtnMap);
	}
	
//	@PostMapping("/v1/at/prod/update-api-test")
//	@Operation(summary = "SaaS 상품 테스트 수정", description = "SaaS 상품 테스트 수정 API")
//	public ApiResponseVO updateProdApiTest(@RequestBody ApiTestSaveRstSVO req) {
//		HashMap<String, Object> rtnMap = new HashMap<>();
//		apiTestService.updateProdApiTest(req);
//		return ResponseUtils.build(rtnMap);
//	}
	
	@PostMapping("/v1/at/prod/delete-api-test")
	@Operation(summary = "SaaS 상품 테스트 삭제", description = "SaaS 상품 테스트 삭제 API")
	public ApiResponseVO deleteProdApiTest(@RequestBody @Valid PrdctApiCmncRsltSVO req) {
		
		// 삭제
		int deletedCount = apiTestService.deleteApiCommRslt(req);
		
		// 통신완료여부 처리
		ApiTestSaveRstSVO testStatusSVO = new ApiTestSaveRstSVO();
		testStatusSVO.setSaasPrdctId(req.getSaasPrdctId());
		testStatusSVO.setSrvrSeCd(req.getSrvrSeCd());
		testStatusSVO.setApiVerSn(req.getApiVerSn());
		int completedCount = apiTestService.updateTestStatus(testStatusSVO);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_CNT, deletedCount + completedCount);
		return ResponseUtils.build(rtnMap);
	}
	
	
	@PostMapping("/v1/at/prod/list-api-test-artcl")
	@Operation(summary = "API 테스트 항목 목록 조회", description = "API 테스트 항목 목록 조회 API")
	public ApiResponseVO getProdApiTestList(@RequestBody ApiTestPrdctSVO req) {
		List<ApiTestDetailListDVO> list = apiTestService.selectApiListWithParam(req);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_LIST, list);
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/start-api-test")
	@Operation(summary = "API 테스트 시작", description = "API 테스트 시작 API")
	public ApiResponseVO startProdApiTest(@RequestBody @Valid TestExecutionSVO req) {
		// 입력값 검증
		apiTestService.validateParameter(req);
		
		Map<String, Object> apiResult = apiTestService.startApiTest(req);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT, apiResult);
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/select-api-test-rst")
	@Operation(summary = "API 테스트 결과 조회", description = "API 테스트 결과 조회 API")
	public ApiResponseVO getApiTestRst(@RequestBody PrdctApiCmncRsltSVO req) {
		PrdctApiCmncRsltDVO apiResult = apiTestService.selectApiCommRsltInfo(req);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put(ConstantInfo.RESULT_INFO, apiResult);
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/list-std-api")
	@Operation(summary = "API선택 목록", description = "API선택 목록 API")
	public ApiResponseVO getApiSelectList(@RequestBody StdApiSVO req) {
		HashMap<String, Object> rtnMap = new HashMap<>();
		List<StdApiDVO> selectStdApiList = apiTestService.selectStdApiList(req);
		
		int totalCnt = apiTestService.selectStdApiListTot(req);
		rtnMap.put(ConstantInfo.RESULT_LIST, selectStdApiList);
		rtnMap.put(ConstantInfo.RESULT_CNT, totalCnt);
		
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/info-prod")
	@Operation(summary = "카탈로그 정보 조회", description = "카탈로그 정보 조회 API")
	public ApiResponseVO getProdInfo(@RequestBody ApiTestPrdctSVO req) {
		HashMap<String, Object> rtnMap = new HashMap<>();
		
		ApiTestProdDVO selectProdInfo = apiTestService.selectProdInfo(req);
		rtnMap.put(ConstantInfo.RESULT_INFO, selectProdInfo);
		
		return ResponseUtils.build(rtnMap);
	}
	
	
	@PostMapping("/v1/at/prod/update-catalog-version")
	@Operation(summary = "카탈로그 상품 재등록", description = "카탈로그 상품 재등록 API")
	public ApiResponseVO updateCatalogVer(@RequestBody ApiTestPrdctSVO req) {
		HashMap<String, Object> rtnMap = new HashMap<>();
		int updatedCount = apiTestService.updateCatalogVersion(req);
		rtnMap.put(ConstantInfo.RESULT_CNT, updatedCount);
		return ResponseUtils.build(rtnMap);
	}
	
	@PostMapping("/v1/at/prod/test-artcl-info")
	@Operation(summary = "테스트대상 API항목내역 조회", description = "테스트대상 API항목내역 조회 API")
	public ApiResponseVO getTestArtclInfo(@RequestBody PrdctApiCmncRsltSVO req) {
		HashMap<String, Object> rtnMap = new HashMap<>();
		Map<String, Object> testArtclInfo = apiTestService.getTestArtclInfo(req);
		rtnMap.put(ConstantInfo.RESULT_INFO, testArtclInfo);
		return ResponseUtils.build(rtnMap);
	}
	
	
	// ======================= 테스트 용 ======================= 
	
	
	
	// 상품목록조회
	@GetMapping("/prod/list")
	public ApiResponseVO yyTestList1(
				@RequestParam(value = "category_id") String category_id,
				@RequestParam(value = "keyword") String keyword,
				@RequestParam(value = "page") int page
			) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii! test 1");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	// 웹오피스필수점검
	@PostMapping("/webopc/setting")
	public ApiResponseVO yyTestList2(
				@RequestBody TestVO vo
			) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii!setting");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
//		return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, rtnMap
//										, "오류 발생");
	}
	
	public record TestVO(String office_id, List<String> check_items, Map<String, Object> testObj, List<TestVO2> testList) {};
	public record TestVO2(String test1, Integer test2) {};
	
	// 후기 정보 조회
	@GetMapping("/review/rating")
	public ApiResponseVO yyTestList3(
				@RequestParam(value = "product_id") String product_id,
				@RequestParam(value = "sort") String sort
			) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii! rating 22");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	// 사용자정보조회
	@PostMapping("/user/info/update")
	public ApiResponseVO yyTestList4(@RequestBody String user_id) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii!user Info 1");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
//		return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, rtnMap
//										, "오류 발생");
	}
	
	// 구독정보조회
	@PostMapping("/subscription/info")
	public ApiResponseVO yyTestList5(@RequestBody String subscriberId) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii!subscription info ");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
//			return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, rtnMap
//											, "오류 발생");
	}
	
	@GetMapping("/test")
	public ApiResponseVO yyTestList6(
			) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii! testteste test 22");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	
	@GetMapping("/user/info/updated")
	public ApiResponseVO yyTestList8(@RequestParam(value = "userId") String userId) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test", "Hiiiiiiiiiiii! test 1, djfklsjke");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
//		return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, rtnMap
//		, "오류 발생");
	}
	
	
	@PostMapping("/v1/test/test")
	public ApiResponseVO yyTestList9(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	@PostMapping("/v1/test/test2")
	public ApiResponseVO yyTestList911(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	@PostMapping("/catalog/v1/saas/prod/create-subscr")
	public ApiResponseVO createSubscr(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	@PostMapping("/catalog/v1/saas/prod/cancel-subscr")
	public ApiResponseVO cancelSubscr(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	@PostMapping("/catalog/v1/saas/user/info-rt-modify")
	public ApiResponseVO infoRtModify(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	@PostMapping("/package/setting")
	public ApiResponseVO packageSesttingTest(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	@PostMapping("/v1/user/list")
	public ApiResponseVO userLsit(@RequestBody TestVO vo) {
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("test",  "테스트 완료 되었습니다.");
		
		return ResponseUtils.build(HttpStatus.OK, rtnMap, "성공");
	}
	
	// ======================= 테스트 용 ======================= 
	
}
