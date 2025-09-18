package kr.go.iop.ci.sc.cmmn.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 페이징처리를 위한 공통 VO
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
@Schema(description = "공통 VO")
@Getter
@Setter
public class CmmnVO implements Serializable {
	private static final long serialVersionUID = -7384031555947545531L;

	@Schema(description = "번호")
	private String rnum;

	@Schema(description = "페이지 사용 여부")
	private String pageYn;

	@Schema(description = "고급검색 여부")
	private String dtlSchYn;

	@Schema(description = "현재 페이지")
	private int currentPage;

	@Schema(description = "페이지 글 수")
	private int pageSize;
	
	public int getCurrentPage() {
		if(this.currentPage == 0) {
			return 1;
		} else {
			return this.currentPage;
		}
	}
	
	public int getPageSize() {
		if(this.pageSize == 0) {
			return 10;
		} else {
			return this.pageSize;
		}
	}
}
