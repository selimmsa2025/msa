/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.code.svc.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.iop.ci.sc.cmmn.vo.CmmnVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통코드관리 조회용 정보 VO.
 * @name_ko 공통코드관리 조회 VO
 * @author lsc
 */
@Schema(description = "운영현황-상품, 운영현황-코드")
@Getter
@Setter
public class CodeSVO extends CmmnVO {

	private static final long serialVersionUID = -1535438781145766280L;

	@Schema(description = "공통그룹코드리스트")
	private List<String> comGroupCdList;

}
