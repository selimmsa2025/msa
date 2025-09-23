/*
 *  Copyright (c) 2025 Intelligent On-nara BPS Platform.
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.at.svc.impl;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.go.iop.ci.sc.cm.at.mapper.ApiTestMapper;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestDetailListDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestPrdctDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.ApiTestProdDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.PrdctApiCmncRsltDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.StdApiArtclDVO;
import kr.go.iop.ci.sc.cm.at.mapper.vo.StdApiDVO;
import kr.go.iop.ci.sc.cm.at.svc.ApiTestService;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestPrdctSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.ApiTestSaveRstSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.CmncMngSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.PrdctApiCmncRsltSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.StdApiSVO;
import kr.go.iop.ci.sc.cm.at.svc.vo.TestExecutionSVO;
import kr.go.iop.ci.sc.cm.pc.mapper.ProdCertMapper;
import kr.go.iop.ci.sc.cm.pc.mapper.vo.CertInfoDVO;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.ApiCertKeyReqSVO;
import kr.go.iop.ci.sc.cmmn.bean.WebClientConfig;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.config.info.CommonCode;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


/**
 * SaaS 등록 테스트 관리를 위한 구현 클래스
 * @name_ko SaaS 등록 테스트 관리 구현 클래스
 * @author hyy
 */
@Slf4j
@Service("apiTestService")
@RequiredArgsConstructor
public class ApiTestServiceImpl implements ApiTestService {

	private final ApiTestMapper apiTestMapper;
	
	private final WebClientConfig webClientConfig;
	
	private final ProdCertMapper apiCertKeyMapper;
	
	@Value("${saas.dev.url}")
	private String saasDevUrl;
	
	@Value("${saas.prod.url}")
	private String saasProdUrl;

	/**
	 * 게시물 목록 조회
	 */
	@Override
	public List<ApiTestDVO> selectProdApiTestList(ApiTestSVO req) {
		return apiTestMapper.selectProdApiTestList(req);
	}
	
	/**
	 * 
	 * API 테스트 목록 수 조회
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public int selectApiTestTot(ApiTestSVO req) {
		return apiTestMapper.selectApiTestTot(req);
	}

	/**
	 * 
	 * 등록 테스트 상품 정보
	 *
	 */
	@Override
	public ApiTestPrdctDVO selectProdApiTestInfo(ApiTestPrdctSVO req) {
		return apiTestMapper.selectProdApiTestInfo(req);
	}

	/**
	 *
	 * API 테스트 - 상세 API 목록
	 *
	 */
	@Override
	public List<ApiTestDetailListDVO> selectApiTestDetailList(ApiTestPrdctSVO req) {
		return apiTestMapper.selectApiTestDetailList(req);
	}
	
	/**
	 * 
	 *  상품별 표준 API 목록, 파라미터 전체
	 * 
	 * @param apiList
	 * @return
	 */
	@Override
	public List<ApiTestDetailListDVO> selectApiListWithParam(ApiTestPrdctSVO req) {
		// API 테스트 목록
		List<ApiTestDetailListDVO> apiTestList = selectApiTestDetailList(req);
		// API의 파라미터 목록
		List<StdApiArtclDVO> apiParamList = selectApiArtclList(req);
		
		Map<String, Map<String, HeaderParamValue>> headerParamMap = new HashMap<>();
		Map<String, Map<String, ParamValue>> reqParamMap = new HashMap<>();
		
		// API 만 존재하고 파라미터 존재하지 않은 경우는 아예 headerParamMap, reqParamMap 가 생기지 않음
		// Header, request Body Parameter Map 생성
		for (StdApiArtclDVO apiParam : apiParamList) {
			
			// ApiId, ApiVerSn 복합키 Map 구조
			// 각 Header, Request Body 에 맞는 Map 설정
			switch (apiParam.getApiArtclSeCd()) {
				// Header Parameter
				case ConstantInfo.API_ARTCL_SE_CD_HEADER -> { 
					Map<String, HeaderParamValue> groupMap = headerParamMap.computeIfAbsent(apiParam.getApiId(), k -> new HashMap<>());
					// ApiVerSn 따른 Map 생성
					HeaderParamValue paramValue = groupMap.computeIfAbsent(String.valueOf(apiParam.getApiVerSn()), ver -> new HeaderParamValue(new LinkedHashMap<>()));
					// Header 기본 String Setting
					paramValue.values.put(apiParam.getApiArtclAtrbNm(), "");
				}
				// Request Body
				case ConstantInfo.API_ARTCL_SE_CD_REQUEST -> { 
					Map<String, ParamValue> groupMap = reqParamMap.computeIfAbsent(apiParam.getApiId(), k -> new HashMap<>());
					// ApiVerSn 따른 Map 생성
					ParamValue paramValue = groupMap.computeIfAbsent(String.valueOf(apiParam.getApiVerSn()), ver -> new ParamValue(new LinkedHashMap<>()));
					// 파라미터 컬럼, 기본 값 추가
					paramValue.values.put(apiParam.getApiArtclAtrbNm(), getDefaultValue(apiParam.getApiArtclDataTypeCd()));
				}
				default -> new HashMap<>();
			}
		}
		
		// 통신 인증 내역 정보 (인증 키)
		ApiCertKeyReqSVO keyVo = new ApiCertKeyReqSVO();
		keyVo.setSaasPrdctId(req.getSaasPrdctId());
		keyVo.setSrvrSeCd(req.getSrvrSeCd());
		List<CertInfoDVO> keyList = apiCertKeyMapper.selectApiCertInfoList(keyVo);
		
		// 최종 API 리스트 + 파라미터 합치고 리턴
		return apiTestList.stream()
				.map(api -> {
					if(headerParamMap.containsKey(api.getApiId())) {
						HeaderParamValue headerInfo = headerParamMap.get(api.getApiId()).get(String.valueOf(api.getApiVerSn()));
						Map<String, String> headerMap = headerInfo.values();
						
						// 인증키 정보 셋팅
						for (CertInfoDVO certInfo : keyList ) {
							headerMap.put(certInfo.getAuthkeyNm(), certInfo.getAuthkey());
						}
						
						api.setHeaderContents(headerMap);
					}
					
					if (reqParamMap.containsKey(api.getApiId())) {
						ParamValue reqInfo = reqParamMap.get(api.getApiId()).get(String.valueOf(api.getApiVerSn()));					
						api.setReqBdyContents(reqInfo.values());
					}
					
					return api;
				}).toList();
	}
	
	private record ParamValue(Map<String, Object> values) {}
	private record HeaderParamValue(Map<String, String> values) {}
	
	/**
	 * 
	 * type 에 따른 컬럼 기본값 리턴
	 * 
	 * @param type
	 * @return
	 */
	private Object getDefaultValue(String type) {
		return switch (type) {
	        case ConstantInfo.API_ARTCL_ATRB_CD_BOOLEAN -> false;
	        case ConstantInfo.API_ARTCL_ATRB_CD_INT -> 0;
	        case ConstantInfo.API_ARTCL_ATRB_CD_FLOAT, ConstantInfo.API_ARTCL_ATRB_CD_DOUBLE  -> 0.0;
	        case ConstantInfo.API_ARTCL_ATRB_CD_STRING -> "";
	        case ConstantInfo.API_ARTCL_ATRB_CD_MAP, ConstantInfo.API_ARTCL_ATRB_CD_OBJECT -> new HashMap<>();
	        case ConstantInfo.API_ARTCL_ATRB_CD_ARRAY, ConstantInfo.API_ARTCL_ATRB_CD_LIST -> new ArrayList<>();
	        default -> null;
	    };
	}

	/**
	 *
	 *
	 * 상세 테스트 API 파라미터 목록
	 *
	 */
	@Override
	public List<StdApiArtclDVO> selectApiArtclList(ApiTestPrdctSVO req) {
		return apiTestMapper.selectApiArtclList(req);
	}
	
	
	/**
	 * 
	 * API 테스트 진행
	 * 
	 * @param req
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> startApiTest(TestExecutionSVO req) {
		
		Map<String, Object> result = new HashMap<>();
		
		StdApiSVO stdApiSVO = new StdApiSVO();
		stdApiSVO.setApiVerSn(req.getApiVerSn());
		stdApiSVO.setApiId(req.getApiId());
		
		StdApiDVO apiInfo = apiTestMapper.selectStdApiInfo(stdApiSVO);
		
		// Response code 가 되는 컬럼 가져오기 
//		 String resultCodeAtrbNm = getResponseCode(req);
		
		HttpMethod callMethod = HttpMethod.valueOf(apiInfo.getHttpCmncSeNm());
		Map<String, String> headerContents = req.getHeaderContents();
		String baseUrl = req.getSrvrSeCd().equals(ConstantInfo.TEST_DEV) ? saasDevUrl : saasProdUrl;
		
		PrdctApiCmncRsltDVO apiTestVO = new PrdctApiCmncRsltDVO();
		Map<String, Object> apiResult = new HashMap<>();
		
		URI getUri = null;
		int codeInt = 0;
		
		try {	
		
			if(HttpMethod.GET.equals(callMethod)) {
				getUri = buildGetUri(baseUrl + apiInfo.getUriAddr(), req.getReqBdyContents());
				apiResult = callApi(callMethod, getUri.toString(), headerContents, null);		
			} else if (HttpMethod.POST.equals(callMethod) || HttpMethod.PUT.equals(callMethod))  {
				getUri = URI.create(baseUrl + apiInfo.getUriAddr());
				apiResult = callApi(callMethod, getUri.toString(), headerContents, req.getReqBdyContents());
			} else if (HttpMethod.DELETE.equals(callMethod))  {
				getUri = URI.create(baseUrl + apiInfo.getUriAddr());
				apiResult = callApi(callMethod, getUri.toString(), headerContents, null);
			} else {
				throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 HTTP 메서드" );
			}
		
			// 통신결과 코드 값
			codeInt = Optional.ofNullable(apiResult)
					.map(map -> map.get(ConstantInfo.RESULT_CD))
					.map(Object::toString)
					.map(Integer::parseInt)
					.orElse(0);
		
		} catch (NullPointerException | NumberFormatException e) {
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "결과코드 변환 실패");
	    }
		
		if (HttpStatus.valueOf(codeInt).is2xxSuccessful()) {
			apiTestVO.setCmncRsltCd(CommonCode.CmncRsltCd.SUCCESS.getCode());
			apiTestVO.setCmncRsltNm(CommonCode.CmncRsltCd.SUCCESS.getDescription());
		} else {
			apiTestVO.setCmncRsltCd(CommonCode.CmncRsltCd.FAILURE.getCode());
			apiTestVO.setCmncRsltNm(CommonCode.CmncRsltCd.FAILURE.getDescription());
		}
		
		apiTestVO.setSaasPrdctId(req.getSaasPrdctId());
		apiTestVO.setSrvrSeCd(req.getSrvrSeCd());
		apiTestVO.setApiId(req.getApiId());
		apiTestVO.setFrstCrtPrcrId(req.getFrstCrtPrcrId());
		apiTestVO.setCmncDt(LocalDateTime.now());
		apiTestVO.setApiVerSn(req.getApiVerSn());
		
		// API 전송 완료 후 테스트 삭제 후 재생성
//		deleteAndCreateApi(apiTestVO);
		
		result.put("apiResult", apiResult);
		result.put("apiTestData", apiTestVO);
		
		return result;
	}
	
	private Map<String, Object> callApi(HttpMethod callMethod, String uri, Map<String, String> headerContents, Map<String, Object> reqBdyContents) {
		return webClientConfig.webClient()
			.method(callMethod)
			.uri(uri)
			.headers(h -> headerContents.forEach(h::add))
			.body(reqBdyContents != null ? BodyInserters.fromValue(reqBdyContents) : BodyInserters.empty())
			.exchangeToMono(this::handleResponse)
			.block();
	}
	
	private Mono<Map<String, Object>> handleResponse(ClientResponse clientResponse) {
		if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
        } else {
        	Map<String, Object> errorMap = new HashMap<>();
            errorMap.put(ConstantInfo.RESULT_CD, String.valueOf(clientResponse.statusCode().value()));
            
            ObjectMapper objectMapper = new ObjectMapper();

            return clientResponse.bodyToMono(String.class)
                    .defaultIfEmpty("{}") // 빈값일 경우 빈 JSON 처리
                    .map(body -> {
                        try {
                            JsonNode jsonNode = objectMapper.readTree(body);
                            errorMap.put("resultData", jsonNode.isNull() || jsonNode.isEmpty() ? null : jsonNode);
                        } catch (Exception e) {
                            // JSON 파싱 실패 시 raw body를 넣어주거나 그대로 둘 수도 있음
                            errorMap.put("errorBody", body);
                        }
                        return errorMap;
                    });
        }
	}
	
	/**
	 * 
	 * query parameter 형식으로 uri 리턴
	 * 
	 * @param baseUrl
	 * @param queryParams
	 * @return
	 */
	private URI buildGetUri(String baseUrl, Map<String, Object> queryParams) {
	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
	    queryParams.forEach((key, value) -> {
	        if (value instanceof Collection<?> values) {
	            values.forEach(v -> builder.queryParam(key, v));
	        } else {
	            builder.queryParam(key, value);
	        }
	    });
	    return builder.build().encode().toUri();
	}
	
	/**
	 * 
	 *  Response code 가 되는 컬럼 가져오기
	 * 
	 * @param req
	 * @return
	 */
	public String getResponseCode(TestExecutionSVO req) {
		
		// Response 셋팅
		ApiTestPrdctSVO artcParam = new ApiTestPrdctSVO();
		artcParam.setSaasPrdctId(req.getSaasPrdctId());
		artcParam.setSrvrSeCd(req.getSrvrSeCd());
		artcParam.setApiId(req.getApiId());
		artcParam.setArtclSeType("rep");
		
		List<StdApiArtclDVO> responseList = selectApiArtclList(artcParam);
		
		// code 가 들어갈 경우 ( result code 구하기 )
		String resultCodeAtrbNm = "";
		for (StdApiArtclDVO apiRepParam : responseList) {
			if (apiRepParam.getApiArtclAtrbNm().toLowerCase().contains("code")) {
				resultCodeAtrbNm  = apiRepParam.getApiArtclAtrbNm();
			}
		}
		
		return resultCodeAtrbNm;
	}
	
	/**
	 * 
	 * API 테스트 삭제 후 생성
	 * 
	 * @param req
	 */
	@Override
	public void deleteAndCreateApi(PrdctApiCmncRsltDVO req) {
		PrdctApiCmncRsltSVO svo = new PrdctApiCmncRsltSVO(req.getSaasPrdctId(), req.getSrvrSeCd(), req.getApiId(), req.getApiVerSn());
		PrdctApiCmncRsltDVO apiCommRsltInfo = selectApiCommRsltInfo(svo);
		
		if (apiCommRsltInfo != null) {
			deleteApiCommRslt(svo);
		}
		apiTestMapper.insertProdApiTest(req);
	}
	
	/**
	 * 
	 * API 통신결과 조회
	 *
	 */
	@Override
	public PrdctApiCmncRsltDVO selectApiCommRsltInfo(PrdctApiCmncRsltSVO req) {
		return apiTestMapper.selectApiCommRsltInfo(req);
	}
	
	
	/**
	 *
	 * API 테스트 삭제
	 *
	 */
	@Override
	public int deleteApiCommRslt(PrdctApiCmncRsltSVO req) {
		return apiTestMapper.deleteApiCommRslt(req);
	}
	
	/**
	 *
	 * API 테스트 생성
	 * 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertProdApiTest(ApiTestSaveRstSVO req) {
		List<PrdctApiCmncRsltDVO> updateList = req.getUpdateList();
		int total = 0; 
		for(PrdctApiCmncRsltDVO apiTest : updateList) {
			int updateCount = apiTestMapper.updateProdApiTest(apiTest);
			total += updateCount;
		}
		
		List<PrdctApiCmncRsltDVO> insertList = req.getInsertList();
		for(PrdctApiCmncRsltDVO apiTest: insertList) {
			int insertCount = apiTestMapper.insertProdApiTest(apiTest);
			total += insertCount;
		}
		return total;
	}

	/**
	 * 
	 *  API 선택 팝업 목록
	 *
	 */
	@Override
	public List<StdApiDVO> selectStdApiList(StdApiSVO req) {
		return apiTestMapper.selectStdApiList(req);
	}
	
	/**
	 * 
	 * API 선택 팝업 목록 수
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public int selectStdApiListTot(StdApiSVO req) {
		return apiTestMapper.selectStdApiListTot(req);
	}

	/**
	 *
	 * 카탈로그 등록 여부 조회
	 *
	 */
	@Override
	public ApiTestProdDVO selectProdInfo(ApiTestPrdctSVO req) {
		return apiTestMapper.selectProdInfo(req);
	}

	/**
	 *
	 * 카탈로그 사용여부 변경
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateCatalog(ApiTestPrdctSVO req) {
		 int resultCount = apiTestMapper.updateCatalog(req);
		 
		 if ( req.getApiVerSn() == null ) {
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "필수값이 없습니다.");
		}
			
		int updateCount = apiTestMapper.updateCatalogVersion(req);
		int deleteCount = apiTestMapper.deleteApiTestByOldVersion(req);
		 
		return (resultCount + updateCount + deleteCount);
	}
	
	/**
	 * 
	 * 파라미터 값 검증
	 *
	 */
	@Override
	public void validateParameter(TestExecutionSVO req) {
		ApiTestPrdctSVO artclListReq = new ApiTestPrdctSVO(req.getSaasPrdctId(), req.getSrvrSeCd(), req.getApiVerSn());
		artclListReq.setArtclSeType("req"); 	// 요청 값
		artclListReq.setApiId(req.getApiId());
		
		Map<String, String> headerMap = req.getHeaderContents();
		Map<String, Object> reqBdyMap = req.getReqBdyContents();
		
		List<StdApiArtclDVO> artclList = selectApiArtclList(artclListReq);
		
		Map<String, String> nextHeaderMap = null;
		Map<String, Object> nextReqBdyMap = null;
		
		for (StdApiArtclDVO artlInfo : artclList) {
			
			String atrbNm = artlInfo.getApiArtclAtrbNm();
			String esntlYn = artlInfo.getApiArtclEsntlYn();
			
			String value = "";
			
			if (ConstantInfo.API_ARTCL_SE_CD_HEADER.equals(artlInfo.getApiArtclSeCd())) {	// Header
				value = headerMap.get(atrbNm);
				if(ConstantInfo.Y_VALUE.equals(esntlYn) 
					&& ( !headerMap.containsKey(atrbNm) || value == null  || value.isBlank())) {
					throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "필수 Header key 누락");
				}
			} else if (ConstantInfo.API_ARTCL_SE_CD_REQUEST.equals(artlInfo.getApiArtclSeCd())) {	// Request Parameter
				value = String.valueOf(reqBdyMap.get(atrbNm));
				if(ConstantInfo.Y_VALUE.equals(esntlYn) && 
						( !reqBdyMap.containsKey(atrbNm) || reqBdyMap.get(atrbNm) == null || StringUtils.isBlank(value))) {
						throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "필수 Request Parameter key 누락");
				}
			}
		} 
		
		// 통신 인증 내역 정보 (인증 키)
		ApiCertKeyReqSVO keyVo = new ApiCertKeyReqSVO();
		keyVo.setSaasPrdctId(req.getSaasPrdctId());
		keyVo.setSrvrSeCd(req.getSrvrSeCd());
		List<CertInfoDVO> keyList = apiCertKeyMapper.selectApiCertInfoList(keyVo);
		
		// 파라미터 그외의 값 들어올경우 필터링
		Set<String> headerKeys = artclList.stream()
				.filter(artcl -> ConstantInfo.API_ARTCL_SE_CD_HEADER.equals(artcl.getApiArtclSeCd()))
				.map(StdApiArtclDVO::getApiArtclAtrbNm)
				.collect(Collectors.toSet());
		
		// 인증내역 정보 검증 통과
		headerKeys.addAll(
				keyList.stream()
			              .map(CertInfoDVO::getAuthkeyNm)
			              .collect(Collectors.toSet())
			);
		
		Set<String> requestKeys = artclList.stream()
				.filter(artcl -> ConstantInfo.API_ARTCL_SE_CD_REQUEST.equals(artcl.getApiArtclSeCd()))
				.map(StdApiArtclDVO::getApiArtclAtrbNm)
				.collect(Collectors.toSet());
		
		nextHeaderMap = filterMap((LinkedHashMap<String, String>) headerMap, headerKeys);
		nextReqBdyMap = filterMap((LinkedHashMap<String, Object>) reqBdyMap, requestKeys);
		
		req.setHeaderContents(nextHeaderMap);
		req.setReqBdyContents(nextReqBdyMap);
	}
	
	/**
	 * 
	 * DB 에 저장된 값 외의 map 의 key를 필터링 처리 
	 * @param <V>
	 * @param map
	 * @param allowedKeys
	 * @return
	 */
	private <V> LinkedHashMap<String, V> filterMap(LinkedHashMap<String, V> map, Set<String> allowedKeys) {
		LinkedHashMap<String, V> result = new LinkedHashMap<>();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            if (allowedKeys.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

	@Override
	public void updateProdApiTest(ApiTestSaveRstSVO req) {
		List<PrdctApiCmncRsltDVO> updateList = req.getUpdateList();
		for(PrdctApiCmncRsltDVO apiTest : updateList) {
			apiTestMapper.updateProdApiTest(apiTest);
		}
		
//		List<GdsApiCommRsltDVO> insertList = req.getInsertList();
//		for(GdsApiCommRsltDVO apiTest: insertList) {
//			insertProdApiTest(apiTest);
//		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateCatalogVersion(ApiTestPrdctSVO req) {
		if ( req.getApiVerSn() == null ) {
			throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "필수값이 없습니다.");
		}
		
		int updateCount = apiTestMapper.updateCatalogVersion(req);
		int deleteCount = apiTestMapper.deleteApiTestByOldVersion(req);
		
		return (updateCount + deleteCount);
	}

	@Override
	public int updateTestStatus(ApiTestSaveRstSVO svo) {
		int apiVerSn = svo.getApiVerSn();
		CmncMngSVO req = new CmncMngSVO(svo.getSaasPrdctId(), svo.getSrvrSeCd());
		req.setApiVerSn(apiVerSn);
		 
		int lastestApiVersion = apiTestMapper.getLastestApiVersion();
		
		// 상품 정보
		ApiTestPrdctSVO prdctSVO = new ApiTestPrdctSVO();
		prdctSVO.setSaasPrdctId(svo.getSaasPrdctId());
		
		ApiTestProdDVO prodInfo = selectProdInfo(prdctSVO);
		
		int prdctApiVerSn = prodInfo.getSaasPrdctApiVerSn();
		String gdsGdntcRegYn = prodInfo.getGdsGdntcRegYn();
		
		int count = 0;
		
		// 최신 버전이면서 상품 등록되지 않은 상태여야 함
		if (lastestApiVersion == apiVerSn
				&& ( prdctApiVerSn != apiVerSn && ConstantInfo.Y_VALUE.equals(gdsGdntcRegYn) ||
					 prdctApiVerSn == apiVerSn && ConstantInfo.N_VALUE.equals(gdsGdntcRegYn))
					) {
			ApiTestPrdctSVO apiDetailParam =  new ApiTestPrdctSVO(req.getSaasPrdctId(), req.getSrvrSeCd(), req.getApiVerSn());
			List<ApiTestDetailListDVO> detailApiList = selectApiTestDetailList(apiDetailParam);
			
			boolean allMatch = detailApiList.stream()
							.allMatch(r -> CommonCode.CmncRsltCd.SUCCESS.getCode().equals(r.getCmncRsltCd()));
			
			// 전체 성공인 경우
			if(allMatch) {
				req.setApiCmncScsYn(ConstantInfo.Y_VALUE);
			} else {	// 카탈로그 등록 직전까지는 계속 'Y' -> 'N'으로 전환될 수 있어야한다.
				req.setApiCmncScsYn(ConstantInfo.N_VALUE);
			}
			count = apiTestMapper.updateApiCommScsYn(req);
		}
		
		return count;
	}
	

}
