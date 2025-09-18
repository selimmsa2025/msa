/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.code.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.go.iop.ci.sc.cmmn.code.mapper.vo.CodeDVO;
import kr.go.iop.ci.sc.cmmn.code.svc.vo.CodeSVO;

/**
 * 공통코드관리 매퍼 인터페이스.
 * @name_ko 공통코드관리 매퍼
 * @author lsc
 */
@Mapper
public interface CodeMapper {

	/* 코드 목록 조회 */
	public List<CodeDVO> selectCodeList(CodeSVO codeSVO);

}
