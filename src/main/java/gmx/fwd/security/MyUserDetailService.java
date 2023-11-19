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

    private final UserMapper userMapper;

    @Autowired
    public MyUserDetailService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
        UserVO userVO = userMapper.securityLogin(email);
        
        if (userVO == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new User(userVO.getEmail(), userVO.getPassword(), getAuthorities(userVO));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserVO userVO) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
