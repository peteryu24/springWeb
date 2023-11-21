package gmx.fwd.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import gmx.fwd.jwt.TokenProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    	System.out.println("onAuthentuactionSucess in MyAuthenticationSuccessHandler is called");
    	
        // 인증된 사용자의 username 추출
        String username = authentication.getName();
        
        // 사용자 역할 (예: "ROLE_USER") 추출
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_USER");

        // JWT 토큰 생성
        String token = tokenProvider.createToken(username, role);
        System.out.println(token + " token is");

        // 응답에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\":\"" + token + "\"}");
    }
}
