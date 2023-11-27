package gmx.fwd.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        System.out.println("token is " + token);

        // 응답에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\":\"" + token + "\"}");
        
        // refresh token
        String refreshToken = tokenProvider.createRefreshToken(username, role);
        System.out.println("refresh Token is " + refreshToken);

        // 로그인 성공 했을 때 httpOnly 쿠키에 리프레쉬 토큰 저장
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/"); // 애플리케이션의 모든 경로에서 쿠키 사용
        refreshCookie.setMaxAge(60 * 60 * 24); // 1 week
        //refreshCookie.setSecure(false); // 개발 환경이 HTTP인 경우 false로 설정
        response.addCookie(refreshCookie);
    }
}
