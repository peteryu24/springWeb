package gmx.fwd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import gmx.fwd.service.user.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserDetailService userDetailService;

	@Autowired
	public SecurityConfig(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/user/**").permitAll() // 해당 디렉토리는 인증 없이 허용
				.anyRequest().authenticated() // 16번줄에 허용한 경로 외에는 인증 필요
			.and()
				.formLogin()
					.loginPage("/user/welcomePage.do") // 로그인 페이지
					.failureUrl("/user/welcomePage.do?error=true") // 로그인 실패 시 
					.permitAll()
			.and()
				.logout()
				.permitAll()	
			.and()
	        	.csrf().disable();  
		/* http
	        .cors().configurationSource(corsConfigurationSource());*/
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.userDetailsService(userDetailService);
	}

}
