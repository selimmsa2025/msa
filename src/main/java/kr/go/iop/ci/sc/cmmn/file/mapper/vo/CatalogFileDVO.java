/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.file.mapper.vo;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
/**
 * 첨부파일 조회 결과용 정보 VO.
 * @name_ko 첨부파일 화면표시 VO
 * @author lsh
 */
@Schema(description = "첨부파일 DVO")
@Getter
@Setter
public class CatalogFileDVO implements Serializable {
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
	
	@Schema(description = "최초 생성 일시")
    private String frstCrtDt;  
	
	@Schema(description = "최종 변경 처리자 아이디")
    private String lastChgPrcrId;   
	
	@Schema(description = "최종 변경 일시")
    private String lastChgDt;  

}