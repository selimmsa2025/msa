package kr.go.iop.ci.sc.cmmn.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 VO - 전체 서비스에 동일하게 적용 필요함
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.11.13
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.11.13    양정숙        최초 생성
 * </pre>
 */
@Schema(description = "사용자 정보")
@Getter
@Setter
public class UserVO implements Serializable {
	private static final long serialVersionUID = -7384031555947545531L;

	@Schema(description = "계정id")
	private String accountId;

	@Schema(description = "기관코드")
	private String institutionCode;

	@Schema(description = "기관명")
	private String institutionNm;

	@Schema(description = "부서id")
	private String deptId;

	@Schema(description = "직급코드")
	private String gradeCode;

	@Schema(description = "직급명")
	private String gradeName;

	@Schema(description = "사용자명")
	private String userName;

	@Schema(description = "사용자id")
	private String userId;

	@Schema(description = "통합id")
	private String unityId;

	@Schema(description = "이메일")
	private String email;

	@Schema(description = "부서명")
	private String deptName;

	@Schema(description = "직위코드")
	private String positionCode;

	@Schema(description = "직위명")
	private String positionName;

	@Schema(description = "직위상세")
	private String positionDetail;

	@Schema(description = "직책명")
	private String dutyCode;

	@Schema(description = "사진경로")
	private String photoPath;

	@Schema(description = "이동전화번호")
	private String mobileNumber;

	@Schema(description = "사무실전화번호")
	private String officeNumber;

	@Schema(description = "사무실fax번호")
	private String officeFaxNumber;

	@Schema(description = "사무실우편번호")
	private String officeZipcode;

	@Schema(description = "사무실주소")
	private String officeAdres;

	@Schema(description = "사무실상세주소")
	private String officeDetailAdres;

	@Schema(description = "홈페이지")
	private String homepage;

	@Schema(description = "겸직유형")
	private String concurrentType;

	@Schema(description = "파견유형")
	private String dispatchType;

	@Schema(description = "존폐유형")
	private String mntnabType;

	@Schema(description = "사용자유형")
	private String userType;

	@Schema(description = "확정유형")
	private String confmType;

	@Schema(description = "pkidn값")
	private String pkiDn;

	@Schema(description = "생년월일")
	private String birthDate;

	@Schema(description = "온나라아이디")
	private String onId;

	@Schema(description = "담당직무")
	private String userCharge;

	@Schema(description = "나라이음아이디")
	private String eumId;

	@Schema(description = "사용자정렬순서")
	private Integer userOrder;

	@Schema(description = "지능형id")
	private String iopId;

	@Schema(description = "수정일시")
	private String updateTime;

	@Schema(description = "카탈로그권한코드")
	private String catalogAuthrtCd;
	
	@Schema(description = "카탈로그권한코드명")
	private String catalogAuthrtNm;
	
	@Schema(description = "권한목록")
	private String[] userAuthrtList;
}
