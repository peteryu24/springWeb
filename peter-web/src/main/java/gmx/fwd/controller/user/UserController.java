package gmx.fwd.controller.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gmx.fwd.service.user.UserService;
import gmx.fwd.vo.uservo.UserVO;

/*
 * Accept
 * 클라이언트에서 웹서버로 요청시 요청메세지에 담기는 헤더
 * (ex. 웹서버에게 json 데이터만 처리할 수 있으니 json 데이터 형식으로 응답을 돌려줘)
 * 데이터를 보내고 받을 때 타입을 지정해주는 것
 * 
 * 
 * Content-Type
 * HTTP 메세지에 담겨 보내는 데이터의 형식을 알려주는 헤더
 * 만약 지정하지 않는다면 받는 입장에서는 단순히 텍스트 데이터로 받음
 * 단순히 현재 전송하는 데이터의 타입 설정
 * 
 * GET방식의 경우 
 * value = text로 보내지기 때문에
 * 따로 Content-Type 설정 필요 X
 * 
 * POST나 PUT일 경우
 * ajax: Content-Type = application/json
 * <form>: Content-Type = multipart/form-data
 */

@Controller
@RequestMapping("/user")
/*
 * @RequestMapping("user")
 * 
 * login.jsp에서 "location.href='/user/register.do'"로 호출시,
 * 절대 경로
 * http://localhost:8080 후 바로 시작
 * 따라서 패키지명 분실
 */
public class UserController {

	@Autowired
	private UserService userService;

	// 시작화면 (로그인 화면)
	@GetMapping("/welcomePage.do")
	public String loginPage() {
		return "login/login";
	}

	// 회원가입 페이지로 넘겨주는 컨트롤러
	@GetMapping("/goRegisterPage.do")
	public String register() {
		return "login/register";
	}

	/*
	 * 회원가입 처리 컨트롤러
	 * 
	 * form으로 들어온 정보(아이디, 비번, 닉네임)를 UserVo user로 받음
	 * BindingResult로 검증하고, 회원가입 처리를 @UserService로 넘겨줌
	 * 
	 * 성공시, 로그인 페이지로 이동
	 * 
	 * ModelAttribute
	 * 클라이언트가 전송하는 form 형태의 HTTP Body와
	 * 요청 파라미터들을 생성자나 Setter로 바인딩
	 */
	// @PostMapping("/register.do")
	@ResponseBody
	@RequestMapping(value = "/register.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "application/json")
	public HashMap<String, String> register(@Valid @ModelAttribute UserVO user, BindingResult bindingResult) {

		HashMap<String, String> response = new HashMap<>();

		if (bindingResult.hasErrors()) {
			response.put("registerFlag", "fail");
			return response;
		}

		if (userService.register(user)) {
			response.put("registerFlag", "success");
		} else {
			response.put("registerFlag", "fail");
		}

		return response;
	}

	/*
	 * 로그인 처리 컨트롤러
	 * 
	 * form으로 email과 password를 각각 받음
	 * 해당 email과 password로 @UserService로 로그인 시도
	 * 
	 * session 개념을 포함하여 로그인 성공시, 유저의 email로 세션 설정 이름은 "sessionEmail" 
	 */
	@PostMapping("/login.do")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session, HttpServletResponse response) {
		UserVO user = userService.login(email, password);

		// HTTP 캐시 제어 헤더 설정
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		if (user != null) {
			session.setAttribute("sessionEmail", user.getEmail()); // 세션 설정
			return "post/showAllPosts"; // success
		} else {
			return "login/login"; // fail
		}
	}

	/*
	 * 로그아웃 처리 컨트롤러
	 * 
	 * 존재했던 세션을 만료시키고
	 * 로그인 화면으로 이동(첫 화면)
	 */
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 종료
		return "login/login";
	}

	// 비빌번호 변경 페이지로 이동시켜주는 컨트롤러
	@GetMapping("/showChangePassword.do")
	public String showChangePasswordPage() {
		return "login/changePassword";
	}

	/*
	 * 비밀번호 변경 담당 컨트롤러
	 * 
	 * 현재 비밀번호와 바꾸고 싶은 비밀번호를 확인 차 두 개로 받음
	 * 1. curretPassword 2. newPassword 2. confirmNew Password
	 * 
	 * 세션을 도입하여, 해당 유저의 email 가져옴
	 * @UserService로 email, currentPassword, newPassword, confirmNewPassword 넘겨줌
	 */
	@PostMapping("/changePasswordAction.do")
	public String changePasswordAction(@RequestParam(name = "currentPassword") String currentPassword,
			@RequestParam(name = "newPassword") String newPassword, @RequestParam(name = "confirmNewPassword") String confirmNewPassword,
			HttpSession session) {

		String email = (String) session.getAttribute("sessionEmail");

		if (email == null) {
			return "errorPage";
		}

		if (userService.changePassword(email, currentPassword, newPassword, confirmNewPassword)) {
			return "post/showAllPosts";
		} else {
			return "login/changePassword";
		}
	}

	/*
	 * 회원탈퇴 담당 컨트롤러 
	 * 
	 * 세션을 도입하여 해당 컨트롤러 호출시 @UserService로 email을 넘겨주고
	 * 세션 만료
	 * 
	 * isFlag로 성공 여부 판단
	 */
	@GetMapping("/unregister.do")
	public String unregister(HttpSession session) {
		
		String email = (String) session.getAttribute("sessionEmail");

		if (email != null) {

			if (userService.unregisterUser(email)) {
				session.invalidate();
				return "login/login";
			}
		}
		return "post/showAllposts";

	}

}
