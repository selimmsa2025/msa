package kr.go.iop.ci.sc.cp.cpm.svc.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "상품 조회 VO")
@Getter
@Setter
public class ProdSVO implements Serializable {

	private static final long serialVersionUID = -7845537423400931459L;

	@Schema(description = "saas제품아이디")
	private String saasPrdctId;
	
	@Schema(description = "계정ID")
	private String accountId;
	
	@Schema(description = "saas제품명")
	private String saasPrdctNm;
	
	@Schema(description = "서비스아이디")
	private String srvcId;
	
	@Schema(description = "기관코드")
	private String institutionCode;
	
	@Schema(description = "saas제품유형코드")
	private String saasPrdctTypeCd;
	
	@Schema(description = "정렬코드")
	private String sortCd;

	@Schema(description ="첨부파일그룹번호")
	private String atchFileGroupNo;

}
