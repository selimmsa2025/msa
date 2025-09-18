/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.code.web;

import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cmmn.code.mapper.vo.CodeDVO;
import kr.go.iop.ci.sc.cmmn.code.svc.CodeService;
import kr.go.iop.ci.sc.cmmn.code.svc.vo.CodeSVO;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통코드관리 화면처리를 위한 Controller.
 * @name_ko 공통코드관리 컨트롤러
 * @author lsc
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "공통코드", description = "공통코드 서비스를 이용하기 위한 API")
public class CodeController {

	private final CodeService codeService;

	@PostMapping("/v1/cmmn/code/list")
	@Operation(summary = "코드 목록 조회", description = "코드 목록 조회")
	public ApiResponseVO getCodeList(@RequestBody CodeSVO codeSVO) throws ParseException {
		log.debug("#### getCodeList");

		HashMap<String, Object> rtnMap = new HashMap<>();

		List<CodeDVO> list = codeService.selectCodeList(codeSVO);
		rtnMap.put("list", list);

		return ResponseUtils.build(rtnMap);
	}

}
