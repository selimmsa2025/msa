/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.file.svc;

import java.util.List;

import kr.go.iop.ci.sc.cmmn.file.mapper.vo.CatalogFileDVO;
import kr.go.iop.ci.sc.cmmn.file.svc.vo.CatalogFileSVO;
/**
 * 첨부파일 인터페이스.
 * @name_ko 상품관리 인터페이스
 * @author lsh
 */
public interface CatalogFileService {
	
	/**
	 * 파일 업로드
	 * @param fileSVO 파일 SVO
	 * @return 등록성공건수
	 */
	int insertFile(CatalogFileSVO fileSVO);
	
	/**
	 * 파일 정보 가져오기
	 * @param fileSVO 파일 SVO
	 * @return
	 */
	List<CatalogFileDVO> selectFileList(CatalogFileSVO fileSVO);
	
	/**
	 * 파일 다운로드
	 * @param atchFileSn 첨부파일일련번호
	 * @return 
	 */
	CatalogFileDVO selectFileInfo(CatalogFileSVO fileSVO);

	/**
	 * 파일 삭제
	 * @param atchFileSn 첨부파일일련번호
	 * @return 파일삭제건수
	 */
	int deleteFile(CatalogFileSVO fileSVO);

	/**
     * 첨부파일 일련번호 생성용
     * @return
     */
	Long selectMaxFileSn(CatalogFileSVO fileSVO);
	
	/**
	 * 첨부파일 그룹번호 생성
	 * @return
	 */
	String selectMaxFileGroupSn();
}