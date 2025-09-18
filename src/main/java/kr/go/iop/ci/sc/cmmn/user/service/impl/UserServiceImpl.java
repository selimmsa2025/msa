package kr.go.iop.ci.sc.cmmn.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.go.iop.ci.sc.cmmn.user.mapper.UserMapper;
import kr.go.iop.ci.sc.cmmn.user.service.UserService;
import kr.go.iop.ci.sc.cmmn.vo.UserVO;
import lombok.RequiredArgsConstructor;

/**
 * 게시물 service implements
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
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	/**
	 * 사용자 목록 조회
	 */
	@Override
	public List<UserVO> selectUserList(UserVO vo) {
		return userMapper.selectUserList(vo);
	}

	/**
	 * 사용자 목록 건수 조회
	 */
	@Override
	public int selectUserListCnt(UserVO vo) {
		return userMapper.selectUserListCnt(vo);
	}
	
	/**
	 * 사용자 정보 조회
	 */
	@Override
	public UserVO selectUserInfo(UserVO vo) {
		return userMapper.selectUserInfo(vo);
	}
	
}
