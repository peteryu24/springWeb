package gmx.fwd.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication Success for user: {}", authentication.getName());
        
        String username = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_USER");

        addJwtTokenToResponse(response, username, role);
        addRefreshTokenToCookie(response, username, role);
    }

    private void addJwtTokenToResponse(HttpServletResponse response, String username, String role) throws IOException {
        String token = tokenProvider.createToken(username, role);
        logger.debug("JWT Token is {}", token);

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\":\"" + token + "\"}");
    }

    private void addRefreshTokenToCookie(HttpServletResponse response, String username, String role) {
        String refreshToken = tokenProvider.createRefreshToken(username, role);
        logger.debug("Refresh Token is {}", refreshToken);

        Cookie refreshCookie = tokenProvider.createRefreshTokenCookie(refreshToken);
        response.addCookie(refreshCookie);
    }
}
