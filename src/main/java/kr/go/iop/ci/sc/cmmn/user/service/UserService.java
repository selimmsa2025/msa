package kr.go.iop.ci.sc.cmmn.user.service;

import java.util.List;

import kr.go.iop.ci.sc.cmmn.vo.UserVO;

/**
 * 게시물 service
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
public interface UserService {

	/**
	 * 사용자 목록 조회
	 * 
	 * @return
	 */
	public List<UserVO> selectUserList(UserVO vo);

	/**
	 * 사용자 목록 건수 조회
	 */
	public int selectUserListCnt(UserVO vo);
	
	/**
	 * 사용자 정보 조회
	 */
	public UserVO selectUserInfo(UserVO vo);
	
	
}
