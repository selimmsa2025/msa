package kr.go.iop.ci.sc.config.info;

import java.util.regex.Pattern;

/**
 * static 변수 목록
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023/04/05
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자       수정내용
 * ----------    --------   ---------------------------
 * 2023.04.05.   MSA팀       최초 생성
 * 2023.11.13    양정숙       MSA팀 적용
 * </pre>
 */
public final class ConstantInfo {

	/** Y/N */
	public static final String Y_VALUE = "Y";
	public static final String N_VALUE = "N";

	public static final String SUCC_YN = "successYn";

	/** 처리 결과 값 */
	public static final String RTN_STTS_SUCCESS = "SUCCESS";
	public static final String RTN_STTS_FAIL = "FAIL";
	public static final String RTN_STTS_WAIT = "WAIT";

	/** result */
	public static final String RESULT = "result";
	public static final String RESULT_CD = "resultCd";
	public static final String RESULT_MSG = "resultMsg";
	public static final String RESULT_YN = "resultYn";
	public static final String RESULT_LIST = "resultList";
	public static final String RESULT_INFO = "resultInfo";
	public static final String RESULT_MAP = "resultMap";
	public static final String RESULT_CNT = "resultCnt";
	public static final String RESULT_VO = "resultVo";
	
	/** status */
	public static final String UNKNOWN_STRING = "unknown";
	
	/** jsonView */
	public static final String JSON_VIEW = "jsonView";

	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";
	
	public static final String FWD_URL = "fwdUrl";

	public static final String TOT_CNT = "totalRecordCount";

	public static final String UPLD_MULTI_FILE = "upldMultiFile";

	public static final String EGOV_MAP = "egovMap";

	public static final String ERR_MSG = "errMsg";

	/** token */
	public static final String TOKEN_CLAIM_NAME = "authorities";
	public static final String TOKEN_ACCESS_KEY = "access-token";
	public static final String TOKEN_REFRESH_KEY = "refresh-token";
	public static final String TOKEN_USER_ID = "token-id";
	public static final String TOKEN_USER_INFO = "userInfo";

	public static final String UPLOAD_FILE_PATH = "UPLOAD_FILE_PATH";
	public static final String UPLOAD_FILE_BASE_PATH = "UPLOAD_FILE_BASE_PATH";
	public static final String CKEDITOR_FILE_PATH = "CKEDITOR_FILE_PATH";
	
	public static final Pattern FILEEXT_PATTERN = Pattern.compile("jpg|jpeg|gif|png|bmp");

	public static final int SPLIT_YEAR = 4;
	public static final int SPLIT_MONTH = 6;
	public static final int SPLIT_DATE = 8;
	public static final int BYTE_SIZE = 1024;

	public static final String AUTH_STTS_CD_OK = "AT000001";
	public static final String AUTH_STTS_CD_REFRESH = "AT000002";
	public static final String AUTH_STTS_CD_NO = "AT000003";

	public static final String SYS_AUTH_ADMIN = "A000001";
	public static final String SYS_AUTH_USER = "A000002";
	public static final String SYS_AUTH_NOBODY = "A000099";

	public static final String ANONYMOUS_USER = "anonymousUser";
	
	//2025.08.11 작성
	public static final String TEST_WAIT = "A0050001";
	
	public static final String TEST_DEV = "A0060001";
	public static final String TEST_OPS = "A0060002";

	//2025.08.13 작성
	public static final String NEXT_API_ID = "nextId";
	
	// HTTP 통신 구분 코드
	public static final String HTTP_METHOD_GET = "B0020001";
	public static final String HTTP_METHOD_POST = "B0020002";
	public static final String HTTP_METHOD_PUT = "B0020003";
	public static final String HTTP_METHOD_DELETE = "B0020004";

	// API항목구분코드
	public static final String API_ARTCL_SE_CD_HEADER = "B0030001";
	public static final String API_ARTCL_SE_CD_REQUEST = "B0030002";
	public static final String API_ARTCL_SE_CD_RESPONSE = "B0030003";
	
	// API항목자료유형코드
	public static final String API_ARTCL_ATRB_CD_BOOLEAN = "B0040001";
	public static final String API_ARTCL_ATRB_CD_INT = "B0040002";
	public static final String API_ARTCL_ATRB_CD_FLOAT = "B0040003";
	public static final String API_ARTCL_ATRB_CD_DOUBLE = "B0040004";
	public static final String API_ARTCL_ATRB_CD_STRING = "B0040005";
	public static final String API_ARTCL_ATRB_CD_MAP = "B0040006";
	public static final String API_ARTCL_ATRB_CD_ARRAY = "B0040007";
	public static final String API_ARTCL_ATRB_CD_LIST = "B0040008";
	public static final String API_ARTCL_ATRB_CD_OBJECT = "B0040009";
	
	//1001 API요청응답구분코드
	public static final String API_DMND_RSPNS_REQ = "B0010001";
	
	
	
	private ConstantInfo() {
		throw new IllegalStateException("Utility class");
	}
}
