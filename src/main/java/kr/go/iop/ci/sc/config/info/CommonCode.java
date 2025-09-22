package kr.go.iop.ci.sc.config.info;

public class CommonCode {
	
	// 인스턴스화 방지
    private CommonCode() {}
	
	// 통신결과코드
	public enum CmncRsltCd {
		
		PENDING("A0050001", "대기"),
		SUCCESS("A0050002", "성공"),
		FAILURE("A0050003", "실패")
		;

        private final String code;
        private final String description;

        CmncRsltCd(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() { return code; }
        public String getDescription() { return description; }

        public static CmncRsltCd fromCode(String code) {
            for (CmncRsltCd s : values()) {
                if (s.code.equals(code)) return s;
            }
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }
	
}
