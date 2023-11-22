package gmx.fwd.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.user.UserMapper;
import gmx.fwd.vo.uservo.UserVO;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	/*
	 * UserVo user로 회원가입 정보(아이디, 비번, 닉네임)이 @UserController에서 넘어옴
	 * 
	 * int 타입의 @UserMapper로 쿼리문 실행
	 * 가입한 email이 기존에 이미 등록된 email이면 실패
	 * 성공 실패 여부를 boolean으로 @UserController로 리턴
	 */
	public boolean register(UserVO user) {
		
		if((userMapper.getByUsername(user.getUsername())) != null) {
			return false;
		}
		return userMapper.register(user) > 0;
	}

	/*
	 * email과 password가 각각 @UserController에서 넘어옴
	 * 
	 * HashMap에 email과 password를 넣음
	 * 해당 HashMap을 @UserMapper로 념겨주어 쿼리문 실행
	 */
	public UserVO login(String username, String password) {
		Map<String, String> userInfo = new HashMap<>();
		userInfo.put("username", username);
		userInfo.put("password", password);

		return userMapper.login(userInfo);
	}

	/*
	 * 이메일로 해당 유저 정보를 DB에서 가져오는 로직
	 * 
	 * 비밀번호 변경시 이메일을 키값으로 유저정보를 가져올 때 사용
	 */
	private UserVO getByUsername(String username) {
		return userMapper.getByUsername(username);
	}

	/*
	 * email, newPassword, confirmPassword가 각각 @UserController에서 넘어옴
	 * 
	 * whitelist 개념을 도입하여
	 * 해당 이메일이 없는 계정일 경우(이메일을 세션으로 가져왔기 때문에 그럴 일은 없음 아마도?)
	 * 계정은 있지만 해당 비밀번호가 아닐 경우
	 * 확인 차 받은 두 개의 비밀번호가 서로 일치하지 않을 경우
	 * return false;
	 * 
	 * whitelist 통과시 @UserMapper로 비밀번호 변경 쿼리
	 * int 타입으로 반환됨
	 * 에러 처리는 controller에서 다시
	 */
	public boolean changePassword(String username, String currentPassword, String newPassword, String confirmNewPassword) {
		UserVO user = getByUsername(username);

		// whiteList
		if (user == null)
			return false; // 해당 이메일이 일치하는 계정이 없는 경우
		if (!(user.getPassword().equals(currentPassword)))
			return false; // 해당 계정의 비밀번호가 아닌 경우
		if (!(newPassword.equals(confirmNewPassword)))
			return false; // 비밀번호 재확인이 일치하지 않는 경우

		user.setPassword(confirmNewPassword);
		
		return userMapper.changePassword(user) > 0;
	}

	/*
	 * 세션으로 뽑아낸 email을 넘겨받음
	 * 해당 email을 가지고 @UserMapper로 쿼리 실행
	 * 
	 *  int형 반환되고 @UserController에 boolean으로 반환
	 */
	public boolean unregisterUser(String username) {
		return userMapper.unregisterUser(username) > 0;
	}
}
