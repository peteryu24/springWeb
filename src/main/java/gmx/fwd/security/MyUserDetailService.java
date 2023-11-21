package gmx.fwd.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.user.UserMapper;
import gmx.fwd.vo.uservo.UserVO;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
    private UserMapper userMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername in MyUserDetailService is called");
		System.out.println("username is:" +username);
        UserVO userVO = userMapper.securityLogin(username);
        if (userVO == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(userVO.getUsername(), userVO.getPassword(), getAuthorities(userVO));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserVO userVO) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
