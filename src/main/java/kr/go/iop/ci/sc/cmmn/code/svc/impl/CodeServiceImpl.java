/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.code.svc.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.go.iop.ci.sc.cmmn.code.mapper.CodeMapper;
import kr.go.iop.ci.sc.cmmn.code.mapper.vo.CodeDVO;
import kr.go.iop.ci.sc.cmmn.code.svc.CodeService;
import kr.go.iop.ci.sc.cmmn.code.svc.vo.CodeSVO;
import lombok.RequiredArgsConstructor;

/**
 * 공통코드관리 서비스 구현 클래스.
 * @name_ko 공통코드관리 서비스
 * @author lsc
 */
@Service("codeService")
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

	private final CodeMapper codeMapper;

	/* 코드 목록 조회 */
	public List<CodeDVO> selectCodeList(CodeSVO codeSVO) {
		return codeMapper.selectCodeList(codeSVO);
	}

}
