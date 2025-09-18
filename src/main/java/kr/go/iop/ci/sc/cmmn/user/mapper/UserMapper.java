package kr.go.iop.ci.sc.cmmn.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.go.iop.ci.sc.cmmn.vo.UserVO;

/**
 * 게시물 mapper
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
@Mapper
public interface UserMapper {

	/**
	 * 사용자 목록 조회
	 * 
	 * @return
	 */
	public List<UserVO> selectUserList(UserVO vo);

	/**
	 * 시용자 목록 건수 조회
	 * 
	 * @return
	 */
	public int selectUserListCnt(UserVO vo);
	
	/**
	 * 시용자 정보 조회
	 * 
	 * @return
	 */
	public UserVO selectUserInfo(UserVO vo);
	
}
