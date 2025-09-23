package kr.go.iop.ci.sc.cmmn.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cmmn.user.service.UserService;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.cmmn.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시물 controller
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
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 서비스를 이용하기 위한 API")
public class UserController {

	private final UserService userService;
	
	/**
	 * 로그인한 사용자 목록 조회
	 * @param vo
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/v1/cmmn/user/user-list")
	@Operation(summary = "사용자 목록", description = "사용자 목록 조회")
	public ApiResponseVO getUserList(@RequestBody UserVO vo) throws ParseException {
		log.debug("getUserList");

		List<UserVO> list = userService.selectUserList(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("list", list);

		return ResponseUtils.build(rtnMap);
	}
	
	/**
	 * 게시물 목록 건수 조회
	 * @param vo
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/v1/cmmn/user/list-count")
	@Operation(summary = "사용자 목록 건수 조회", description = "사용자 목록 건수 조회")
	public ApiResponseVO getUserListCnt(@RequestBody UserVO vo) throws ParseException {
		log.debug("getUserListCnt");

		int listCnt = userService.selectUserListCnt(vo);

		HashMap<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("listCnt", listCnt);

		return ResponseUtils.build(rtnMap);
	}

	/**
	 * 선택한 사람 로그인 
	 * @param vo
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/v1/cmmn/user/user-login")
	@Operation(summary = "사용자 로그인", description = "사용자 로그인")
	public ApiResponseVO getUserLogin(@RequestBody UserVO vo, HttpServletResponse response) throws ParseException,IOException {
		log.debug("getUserLogin");
		
		UserVO userInfo = userService.selectUserInfo(vo);
		
		HashMap<String, Object> rtnMap = new HashMap<>();

		 if (userInfo == null) {
			rtnMap.put("result", "error");
			rtnMap.put("resultMsg", "처리 중 오류가 발생하였습니다.");
		 }else {
			
			rtnMap.put("userInfo", userInfo);
			// 세션에 사용자 정보 저장
		    //session.setAttribute("LOGIN_USER", userInfo);
		 }

		 return ResponseUtils.build(rtnMap);
	}
	

	/**
	 * 선택한 사람 로그아웃 
	 * @param vo
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/v1/cmmn/user/user-logout")
    @Operation(summary = "사용자 로그인", description = "사용자 로그아웃")
    public ApiResponseVO getUserLogout(HttpSession session) throws ParseException {
        session.invalidate(); // 세션 초기화
        
        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("result", "success");
		rtnMap.put("resultMsg", "로그아웃 처리되 되었습니다.");
        return ResponseUtils.build(rtnMap);
        
    }
}
