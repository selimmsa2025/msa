/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.file.svc.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.go.iop.ci.sc.cmmn.file.mapper.CatalogFileMapper;
import kr.go.iop.ci.sc.cmmn.file.mapper.vo.CatalogFileDVO;
import kr.go.iop.ci.sc.cmmn.file.svc.CatalogFileService;
import kr.go.iop.ci.sc.cmmn.file.svc.vo.CatalogFileSVO;
import lombok.RequiredArgsConstructor;
/**
 * 첨부파일 서비스 구현 클래스.
 * @name_ko 첨부파일 서비스
 * @author lsh
 */
@Service("catalogFileService")
@RequiredArgsConstructor
public class CatalogFileServiceImpl implements CatalogFileService {
	
	private final CatalogFileMapper catalogFileMapper;

	/**
	 * 파일 업로드
	 */
	@Override
    public int insertFile(CatalogFileSVO fileSVO) {
//        Long maxFileSn = catalogFileMapper.selectMaxFileSn();
//        fileSVO.setAtchFileSn((maxFileSn != null ? maxFileSn : 0)); // (임시) 첨부파일 일련번호 생성용
        return catalogFileMapper.insertFile(fileSVO);
    }

	/**
	 * 파일 불러오기
	 */
	@Override
	public List<CatalogFileDVO> selectFileList(CatalogFileSVO fileSVO) {
		return catalogFileMapper.selectFileList(fileSVO);
	}

	/**
	 * 파일 다운로드
	 */
	@Override
	public CatalogFileDVO selectFileInfo(CatalogFileSVO fileSVO) {
		return catalogFileMapper.selectFileInfo(fileSVO);
	}

	/**
	 * 파일 삭제
	 */
	@Override
	public int deleteFile(CatalogFileSVO fileSVO) {
	    return catalogFileMapper.deleteFile(fileSVO);
	}

	/**
    * 첨부파일 일련번호 생성용
    */
	@Override
	public Long selectMaxFileSn(CatalogFileSVO fileSVO) {
		return catalogFileMapper.selectMaxFileSn(fileSVO);
	}

	/**
	 * 첨부파일 그룹번호 생성
	 */
	@Override
	public String selectMaxFileGroupSn() {
		return catalogFileMapper.selectMaxFileGroupSn();
	}

}