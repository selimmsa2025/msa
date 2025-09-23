package kr.go.iop.ci.sc.cp.cpm.mapper.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.iop.ci.sc.cmmn.file.mapper.vo.CatalogFileDVO;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "상품 결과 조회 VO")
@Getter
@Setter
public class ProdDVO implements Serializable {

	private static final long serialVersionUID = -4963571177026675980L;

	/* 상품정보 반환 s */
	@Schema(description = "saas제품아이디")
	private String saasPrdctId;

	@Schema(description = "saas제품제공방식구분코드")
	private String saasPrdctPvsnMthSeCd;

	@Schema(description = "saas제품유형코드")
	private String saasPrdctTypeCd;

	@Schema(description = "saas제품명")
	private String saasPrdctNm;

	@Schema(description = "saas제품상세내용")
	private String saasPrdctDtlCn;

	@Schema(description = "saas제품요약내용")
	private String saasPrdctSmryCn;

	@Schema(description = "saas제품제조사코드")
	private String saasPrdctMkrCd;

	@Schema(description = "saas제품api버전일련번호")
	private Integer saasPrdctApiVerSn;

	@Schema(description = "saas제품서비스도메인주소")
	private String saasPrdctSrvcDmnAddr;

	@Schema(description = "saas제품서비스상세url주소")
	private String saasPrdctSrvcDtlUrlAddr;

	@Schema(description = "saas제품faq주소")
	private String saasPrdctFaqAddr;

	@Schema(description = "사용여부")
	private String useYn;

	@Schema(description = "상품안내문등록여부")
	private String gdsGdntcRegYn;

	@Schema(description = "제품이미지첨부파일그룹번호")
	private String prdctImgAtchFileGroupNo;

	@Schema(description = "화면이미지첨부파일그룹번호")
	private String scrnImgAtchFileGroupNo;

	@Schema(description = "설명이미지첨부파일그룹번호")
	private String explnImgAtchFileGroupNo;
	


	@Schema(description = "최초생성일시")
	private String frstCrtDt;

	@Schema(description = "최초생성처리자아이디")
	private String frstCrtPrcrId;

	@Schema(description = "최종변경일시")
	private String lastChgDt;

	@Schema(description = "최종변경처리자아이디")
	private String lastChgPrcrId;

	/* 상품정보 반환 e */
    
    /* 상품 첨부파일 리스트 s*/
	
//	@Schema(description = "첨부파일리스트")
//	private List<CatalogFileDVO> scrnFileList;
//	private List<CatalogFileDVO> explnFileList;
	/* 상품 첨부파일 리스트 e*/	
	
	/* 기관정보 반환 s */

	@Schema(description = "기관코드")
	private String institutionCode;

	@Schema(description = "기관명")
	private String institutionName;

	/* 기관정보 반환 e */

	/* 상품 코드명칭 반환 s */
	@Schema(description = "상품제공방식구분명")
	private String saasPrdctPvsnMthSeNm;

	@Schema(description = "SAAS제품유형명")
	private String saasPrdctTypeNm;

	@Schema(description = "SAAS제품제조사명")
	private String saasPrdctMkrNm;

	@Schema(description = "SAAS제품평균평점 ")
	private String saasPrdctRevScrAvg;

	@Schema(description = "SAAS제품리뷰수")
	private String saasPrdctRevCnt;
	
	@Schema(description = "SAAS제품이용자수")
	private String saasPrdctSubCnt;
	
	
	/* 상품 코드명칭 반환 e */

	/* 상품 서비스 반환 s */
	@Schema(description = "서비스아이디")
	private String srvcId;

	@Schema(description = "서비스명")
	private String srvcNm;

	@Schema(description = "서비스내용")
	private String srvcCn;

	@Schema(description = "서비스요약내용")
	private String srvcSmryCn;
	
	@Schema(description ="첨부파일그룹번호")
	private String atchFileGroupNo;
	
	@Schema(description = "서비스상세url주소")
	private String srvcDtlUrlAddr;


	
	/* 상품 서비스 반환 e */
}
