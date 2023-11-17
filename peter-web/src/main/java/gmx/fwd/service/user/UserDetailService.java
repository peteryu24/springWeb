package gmx.fwd.service.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.user.UserMapper;
import gmx.fwd.vo.uservo.UserVO;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			UserVO userVO = userMapper.getByEmail(email);
			System.out.println("hello");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
			System.out.println();
			System.out.println();
			
			System.out.println();
			return new org.springframework.security.core.userdetails.User(userVO.getEmail(), userVO.getPassword(), getAuthorities(userVO));

		} catch (Exception e) {
			throw new UsernameNotFoundException("해당 유저 정보 없음: " + email);
		}

	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(UserVO userVO) {
	    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

}
