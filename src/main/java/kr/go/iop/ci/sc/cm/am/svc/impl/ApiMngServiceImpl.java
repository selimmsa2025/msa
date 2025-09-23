/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cm.am.svc.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.go.iop.ci.sc.cm.am.mapper.ApiMngMapper;
import kr.go.iop.ci.sc.cm.am.mapper.vo.AmArtcDVO;
import kr.go.iop.ci.sc.cm.am.mapper.vo.AmDVO;
import kr.go.iop.ci.sc.cm.am.mapper.vo.AmProdDVO;
import kr.go.iop.ci.sc.cm.am.svc.ApiMngService;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmArtcSVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmSVO;
import kr.go.iop.ci.sc.cm.am.svc.vo.ApiVerSVO;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import kr.go.iop.ci.sc.cmmn.utils.DateUtil;
import kr.go.iop.ci.sc.cmmn.utils.ExcelUtil;
import kr.go.iop.ci.sc.config.info.ConstantInfo;
import kr.go.iop.ci.sc.cm.am.svc.vo.AmProdSVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 표준API관리 서비스 구현 클래스.
 * 
 * @name_ko 표준API관리 서비스
 * @author lsc, ksj
 */
@Slf4j
@Service("apiMngService")
@RequiredArgsConstructor
public class ApiMngServiceImpl implements ApiMngService {

	private final ApiMngMapper apiMngMapper;

	/* 표준API목록조회 */
	@Override
	public List<AmDVO> selectStndApiList(AmSVO svo) {
		int page = svo.getPage() <= 0 ? 1 : svo.getPage();
		int offset = (page - 1) * svo.getPageSize();
		svo.setOffset(offset);
		return apiMngMapper.selectStndApiList(svo);
	}

	/* 표준API목록건수조회 */
	@Override
	public int selectStndApiCnt(AmSVO svo) {
		return apiMngMapper.selectStndApiCnt(svo);
	}

	/* 표준API단건조회 */
	@Override
	public AmDVO selectStndApiInfo(AmSVO svo) {
		AmDVO api = apiMngMapper.selectStndApiInfo(svo);
		List<AmArtcDVO> paramList = apiMngMapper.selectStndApiArtclList(svo);
		api.setParamList(paramList);
		return api;
	}

	/* 표준API항목조회 */
	@Override
	public List<AmArtcDVO> selectStndApiArtclList(AmSVO svo) {
		return apiMngMapper.selectStndApiArtclList(svo);
	}

	/* 표준API_ID 최대값 조회 */
	@Transactional
	@Override
	public String selectNextStndApiId() {
		return apiMngMapper.selectNextStndApiId();
	}

	/* 표준API등록_API기본정보 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertStndApi(AmSVO svo) {
		// 중복검사
		if (apiMngMapper.selectStndApiNmCnt(svo) > 0) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "이미 사용 중인 API명입니다.");
		}
		if (apiMngMapper.selectStndApiUriCnt(svo) > 0) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "이미 사용 중인 URI입니다.");
		}

		String apiId = apiMngMapper.selectNextStndApiId(); 
		svo.setApiId(apiId);
		log.info("[insertStndApi] 발급된 apiId={}", apiId);

		int result = apiMngMapper.insertStndApi(svo);
		if (result != 1) {
			throw new RuntimeException("기본 정보 등록 실패");
		}

		List<AmArtcSVO> paramList = svo.getParamList();
		if (paramList != null && !paramList.isEmpty()) {
			for (AmArtcSVO artc : paramList) {
				artc.setApiId(apiId);
				int row = apiMngMapper.insertStndApiArtcl(artc);
				if (row != 1) {
					throw new RuntimeException("항목 등록 실패");
				}
			}
		}

		// 상품api테스트 초기 세팅
		List<AmProdDVO> gdsList = apiMngMapper.selectProdList(svo);
		if (gdsList != null && !gdsList.isEmpty()) {
			for (AmProdDVO vo : gdsList) {
				AmProdSVO insertVo = new AmProdSVO();
				insertVo.setSaasPrdctId(vo.getSaasPrdctId());
				insertVo.setApiVerSn(svo.getApiVerSn());
				insertVo.setApiId(apiId);
				insertVo.setSrvrSeCd(ConstantInfo.TEST_DEV); // 개발서버
				insertVo.setCmncRsltCd(ConstantInfo.TEST_WAIT); // 대기
				insertVo.setFrstCrtPrcrId("1");
				int r = apiMngMapper.insertProdStndApiR(insertVo); // 상품-표준API 관계
				if (r != 1) {
					throw new RuntimeException("상품-표준API 관계 등록 실패");
				}
				apiMngMapper.insertProdApiTest(insertVo); // 통신결과세팅
				insertVo.setSrvrSeCd(ConstantInfo.TEST_OPS);
				int r2 = apiMngMapper.insertProdApiTest(insertVo); // 통신결과세팅
				if (r2 != 1) {
					throw new RuntimeException("통신테이블 등록 실패");
				}
			}
		}

		return 1;
	}

	/* 표준API등록_API항목정보 */
	@Transactional
	@Override
	public int insertStndApiArtcl(AmArtcSVO svo) {
		return apiMngMapper.insertStndApiArtcl(svo);
	}

	/* 표준API수정_API */
	@Transactional
	@Override
	public int updateStndApi(AmSVO svo) {
		if (apiMngMapper.selectStndApiNmCnt(svo) > 0) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "이미 사용 중인 API명입니다.");
		}
		if (apiMngMapper.selectStndApiUriCnt(svo) > 0) {
			throw new ApiBizException(HttpStatus.BAD_REQUEST, "이미 사용 중인 URI입니다.");
		}

		int resultCnt = apiMngMapper.updateStndApi(svo);
		deleteStndApiArtcl(svo);

		List<AmArtcSVO> paramList = svo.getParamList();
		if (paramList == null || paramList.isEmpty()) {
			return 1;
		}

		for (AmArtcSVO artc : paramList) {
			artc.setApiId(svo.getApiId());
			int row = apiMngMapper.insertStndApiArtcl(artc);
			if (row != 1) {
				throw new RuntimeException("항목 등록 실패");
			}
		}
		return resultCnt;

	}

	/* 표준API삭제_API 기본정보 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteStndApi(AmSVO svo) {

		log.debug("deleteProdApiTest params apiId={}, ver={}, gdsGdntcRegYn={}", svo.getApiId(), svo.getApiVerSn(),
				svo.getGdsGdntcRegYn());

		deleteProdStndApiR(svo); // 상품-api관계테이블 삭제 
		int delTest = apiMngMapper.deleteProdApiTest(svo); //통신결과내역테이블 삭제 
		log.debug("deleteProdApiTest deleted rows={}", delTest);

		int result = apiMngMapper.deleteStndApi(svo); //api기본테이블n업데이트 
		if (result != 1) {
			throw new RuntimeException("기본 정보 삭제 실패");
		}
		return result;
	}

	/* 표준API삭제_API 항목정보 */
	@Override
	public int deleteStndApiArtcl(AmSVO svo) {
		try {
			return apiMngMapper.deleteStndApiArtcl(svo);
		} catch (Exception e) {
			throw new RuntimeException("API 항목 정보 삭제 실패", e);
		}
	}

	/* 표준API버전등록 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Integer> insertStndApiVer(ApiVerSVO svo) {

		Integer nextVer = apiMngMapper.selectNextStndApiVer();
		svo.setApiVerSn(nextVer);

		int verInserted = apiMngMapper.insertStndApiVer(svo);
		if (verInserted != 1) {
			throw new RuntimeException("버전 등록 실패 (apiVerSn=" + nextVer + ")");
		}
		// 현재(직전버전)
		final int prevVer = nextVer - 1;

		// 초기상태(직전버전없을경우)
		if (prevVer <= 0) {
			return apiMngMapper.selectStndApiVerList();
		}

		AmSVO listQuery = new AmSVO();
		listQuery.setPage(1);
		listQuery.setPageSize(1000);
		listQuery.setApiVerSn(prevVer);

		List<AmDVO> apiList = apiMngMapper.selectStndApiList(listQuery);
		if (apiList == null || apiList.isEmpty()) {
			return apiMngMapper.selectStndApiVerList();
		}

		for (AmDVO src : apiList) {

			AmSVO infoParam = new AmSVO();
			infoParam.setApiId(src.getApiId());
			infoParam.setApiVerSn(src.getApiVerSn());
			AmDVO apiDetail = apiMngMapper.selectStndApiInfo(infoParam);
			if (apiDetail == null) {
				throw new RuntimeException("API 상세 조회 실패 (apiId=" + src.getApiId() + ")");
			}

			final String clonedApiId = src.getApiId();

			AmSVO insertApi = new AmSVO();
			insertApi.setApiVerSn(nextVer);
			insertApi.setApiId(clonedApiId);
			insertApi.setApiNm(apiDetail.getApiNm());
			insertApi.setSaasPrdctTypeCd(apiDetail.getSaasPrdctTypeCd());
			insertApi.setApiDmndRspnsSeCd(apiDetail.getApiDmndRspnsSeCd());
			insertApi.setHttpCmncSeCd(apiDetail.getHttpCmncSeCd());
			insertApi.setUriAddr(apiDetail.getUriAddr());

			List<AmArtcDVO> paramList = apiMngMapper.selectStndApiArtclList(infoParam);
			List<AmArtcSVO> paramListForInsert = new ArrayList<>();

			if (paramList != null) {
				for (AmArtcDVO d : paramList) {
					AmArtcSVO s = new AmArtcSVO();
					s.setApiVerSn(nextVer);
					s.setApiId(clonedApiId);
					s.setApiArtclSeCd(d.getApiArtclSeCd());
					s.setApiArtclSn(d.getApiArtclSn());
					s.setApiArtclAtrbNm(d.getApiArtclAtrbNm());
					s.setApiArtclDataTypeCd(d.getApiArtclDataTypeCd());
					s.setApiArtclEsntlYn(d.getApiArtclEsntlYn());
					s.setApiArtclCn(d.getApiArtclCn());
					paramListForInsert.add(s);
				}
			}

			int insertedApi = apiMngMapper.insertStndApi(insertApi);
			if (insertedApi != 1) {
				throw new RuntimeException("기본 정보 등록 실패 (apiId=" + clonedApiId + ")");
			}

			for (AmArtcSVO artc : paramListForInsert) {
				int row = apiMngMapper.insertStndApiArtcl(artc);

				if (row != 1) {
					throw new RuntimeException(
							"항목 등록 실패 (apiId=" + clonedApiId + ", apiArtclSn=" + artc.getApiArtclSn() + ")");
				}
			}

			if (insertedApi > 0) {
				List<AmProdDVO> gdsList = apiMngMapper.selectProdList(insertApi);
				// 테스트테이블과 관계테이블에 insert
				for (AmProdDVO vo : gdsList) {
					AmProdSVO insertVo = new AmProdSVO();
					insertVo.setSaasPrdctId(vo.getSaasPrdctId());
					insertVo.setApiVerSn(nextVer);
					insertVo.setApiId(clonedApiId);
					insertVo.setSrvrSeCd(ConstantInfo.TEST_DEV);// 테스트결과 초기 셋팅 : 개발서버
					insertVo.setCmncRsltCd(ConstantInfo.TEST_WAIT);// 테스트결과 초기 셋팅 : 대기
					insertVo.setFrstCrtPrcrId("1");
					apiMngMapper.insertProdStndApiR(insertVo);
					apiMngMapper.insertProdApiTest(insertVo);
					insertVo.setSrvrSeCd(ConstantInfo.TEST_OPS);// 테스트결과 초기 셋팅 : 운영서버
					apiMngMapper.insertProdApiTest(insertVo);

					// 카탈로그 미등록 상품 버전 업데이트
					AmProdSVO updVo = new AmProdSVO();
					updVo.setSaasPrdctId(vo.getSaasPrdctId());
					updVo.setApiVerSn(nextVer);
					apiMngMapper.updateProdApiVer(updVo);
				}

			}

			// 이전 통신결과내역 데이터 삭제(카탈로그 미등록 상품에 대한 api)
			AmSVO delParam = new AmSVO();
			delParam.setApiId(clonedApiId);
			delParam.setApiVerSn(prevVer);
			delParam.setGdsGdntcRegYn(ConstantInfo.N_VALUE);// 미등록
			apiMngMapper.deleteProdApiTest(delParam);
		}

		return apiMngMapper.selectStndApiVerList();
	}

	/* 표준API버전목록조회 */
	@Override
	public List<Integer> selectStndApiVerList() {
		return apiMngMapper.selectStndApiVerList();

	}

	// 2025.08.19 API 추가에 따른 테스트 정보 추가를 위한 메서드
	/* 상품 리스트 조회 */
	@Override
	public List<AmProdDVO> selectProdList(AmSVO vo) {
		return apiMngMapper.selectProdList(vo);
	}

	/* 상품표준API관계등록 */
	@Override
	public int insertProdStndApiR(AmProdSVO vo) {
		return apiMngMapper.insertProdStndApiR(vo);
	}

	/* 상품 API 통신 항목 등록(초기셋팅) */
	@Override
	public int insertProdApiTest(AmProdSVO vo) {
		return apiMngMapper.insertProdApiTest(vo);
	}

	/* 상품표준API관계삭제 */
	@Override
	public int deleteProdStndApiR(AmSVO vo) {
		try {
			return apiMngMapper.deleteProdStndApiR(vo);
		} catch (Exception e) {
			throw new RuntimeException("상품표준API관계삭제 실패", e);
		}
	}

	/* 상품 API 통신 항목 삭제 */
	@Override
	public int deleteProdApiTest(AmSVO vo) {
		try {
			return apiMngMapper.deleteProdApiTest(vo);
		} catch (Exception e) {
			throw new RuntimeException("통신결과내역 항목 삭제 실패", e);
		}
	}

	/* 표준 API 목록 엑셀 다운로드 (현재페이지/전체) */
	@Override
	public void selectStndApiListExcelDownload(HttpServletRequest request, HttpServletResponse response, AmSVO svo,
			String scope) {

		String excelFileName = DateUtil.getNowDateString() + "_" + "표준API 목록" + ".xlsx";

		// 엑셀 관련 변수
		Map<String, String> mapInfo = new HashMap<>();
		List<String[]> dataList = new ArrayList<>();
		List<String> titleList = new ArrayList<>();
		mapInfo.put("fileName", excelFileName);
		mapInfo.put("sheetName", "표준API 목록");
		mapInfo.put("excelTitle", "표준API 목록");
		

		int page = (svo.getPage() < 1) ? 1 : svo.getPage();
		int pageSize = (svo.getPageSize() < 1) ? 10 : svo.getPageSize();
		int offset = (page - 1) * pageSize;

		svo.setPage(page);
		svo.setPageSize(pageSize);
		svo.setOffset(offset);

		List<AmDVO> apiList = apiMngMapper.selectStndApiList(svo);

		// 엑셀 열 제목
		titleList.add("번호");
		titleList.add("API ID");
		titleList.add("API명");
		titleList.add("API 버전번호");
		titleList.add("제공유형");
		titleList.add("요청/응답");
		titleList.add("HTTP통신구분");
		titleList.add("URI주소");
		titleList.add("등록일시");

		if (apiList != null && !apiList.isEmpty()) {
			String[] resultArray = null;
			AmDVO result = null;
			for (int i = 0; i < apiList.size(); i++) {
				result = apiList.get(i);
				int num = offset + i + 1;
				resultArray = new String[titleList.size()];
				resultArray[0] = String.valueOf(num);
				resultArray[1] = result.getApiId();
				resultArray[2] = result.getApiNm();
				resultArray[3] = String.valueOf(result.getApiVerSn());
				resultArray[4] = result.getSaasPrdctTypeNm();
				resultArray[5] = result.getApiDmndRspnsSeNm();
				resultArray[6] = result.getHttpCmncSeNm();
				resultArray[7] = result.getUriAddr();
				resultArray[8] = result.getFrstCrtDt();

				dataList.add(resultArray);
			}
		}
		ExcelUtil.excelFileDownload(request, response, mapInfo, titleList, null, dataList);
	}
	
	/* 표준 API 상세 엑셀 다운로드 */ 
	@Override
	public void selectStndApiInfoExcelDownload(HttpServletRequest request, HttpServletResponse response, AmSVO svo) {
	    String excelFileName = DateUtil.getNowDateString() + "_" + "API 상세(" + svo.getApiId() + ").xlsx";

	    Map<String, String> mapInfo = new HashMap<>();
	    mapInfo.put("fileName", excelFileName);
	    mapInfo.put("sheetName", "표준API 상세");
	    mapInfo.put("excelTitle", "표준API 상세");

	    List<String> apiTitleList = new ArrayList<>();
	    List<String> apiDataList = new ArrayList<>();

	    AmDVO api = apiMngMapper.selectStndApiInfo(svo);
	    List<AmArtcDVO> paramList = apiMngMapper.selectStndApiArtclList(svo);

	    if (api != null) {
	        api.setParamList(paramList);
	        String[][] basics = {
	                {"API ID",        nz(api.getApiId())},
	                {"API명",         nz(api.getApiNm())},
	                {"API 버전번호",  String.valueOf(api.getApiVerSn())},
	                {"제공유형",      nz(api.getSaasPrdctTypeNm())},
	                {"요청/응답",     nz(api.getApiDmndRspnsSeNm())},
	                {"HTTP통신구분",  nz(api.getHttpCmncSeNm())},
	                {"URI주소",       nz(api.getUriAddr())},
	                {"등록일시",      nz(api.getFrstCrtDt())}
	        };
	        for (String[] b : basics) {
	            apiTitleList.add(b[0]);
	            apiDataList.add(b[1]);
	        }
	    }

	    Map<String, List<String[]>> sectionDataMap = new LinkedHashMap<>();
	    List<String> sectionHeader = Arrays.asList("속성명", "타입", "필수", "설명");

	    if (api != null && api.getParamList() != null) {
	        String[] sections = {"HeaderParameter", "RequestBody", "ResponseBody"};
	        String[] sectionTitles = {"Header Parameter", "Request Body", "Response Body"};

	        for (int i = 0; i < sections.length; i++) {
	            String section = sections[i];
	            String sectionTitle = sectionTitles[i];

	            List<String[]> sectionData = api.getParamList().stream()
	                    .filter(p -> section.equals(p.getApiArtclSeNm()))
	                    .map(p -> new String[]{
	                            nz(p.getApiArtclAtrbNm()),
	                            nz(p.getApiArtclDataTypeNm()),
	                            yn(p.getApiArtclEsntlYn()),
	                            nz(p.getApiArtclCn())
	                    })
	                    .toList();

	            if (!sectionData.isEmpty()) {
	                sectionDataMap.put(sectionTitle, sectionData);
	            }
	        }
	    }

	    ExcelUtil.excelFileDownload(request, response, mapInfo,
	            apiTitleList, apiDataList,
	            sectionDataMap, sectionHeader);
	}
	    

	// 상세 엑셀 헬퍼
	private static String nz(String v) { return v == null ? "" : v; }
	private static String yn(String v) {
	    if ("Y".equalsIgnoreCase(v)) return "Y";
	    if ("N".equalsIgnoreCase(v)) return "N";
	    return nz(v);
	}

}
