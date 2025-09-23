/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.file.svc.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
/**
 * 첨부파일 조회용 정보 VO.
 * @name_ko 첨부파일 조회 VO
 * @author lsh
 */
@Schema(description = "첨부파일 SVO")
@Getter
@Setter
public class CatalogFileSVO implements Serializable {
	private static final long serialVersionUID = -7384031555947545531L;
	
	@Schema(description = "첨부파일 그룹번호")
    private String atchFileGroupNo;   
	
	@Schema(description = "첨부파일 일련번호")
    private Long atchFileSn;        
	
	@Schema(description = "첨부파일명")
    private String atchFileNm;        
	
	@Schema(description = "사용여부")
    private String useYn;             
	
	@Schema(description = "첨부파일내용")
    private String atchFileCn;       
	
	@Schema(description = "원본파일명")
    private String orgnlFileNm;     
	
	@Schema(description = "첨부파일경로명")
    private String atchFilePathNm;    
	
	@Schema(description = "확장자명")
    private String extnNm;            
	
	@Schema(description = "파일크기")
    private Long fileSz;              
	
	@Schema(description = "최초 생성 처리자 아이디")
    private String frstCrtPrcrId;     
	
	@Schema(description = "최종 변경 처리자 아이디")
    private String lastChgPrcrId;   

	@Schema(description = "업로드 파일", type = "string", format = "binary")
    private List<MultipartFile> uploadFile;

}