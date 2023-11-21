package gmx.fwd.mapper.user;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import gmx.fwd.vo.uservo.UserVO;

@Mapper
public interface UserMapper {

	int register(UserVO user);

	UserVO login(Map<String, String> params);
	
	UserVO getByUsername(String username);

	int changePassword(UserVO user);

	int unregisterUser(String username);

	UserVO securityLogin(String username);

}
