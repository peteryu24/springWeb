package gmx.fwd.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/token")
public class TokenVerifier {

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/verifyToken.do")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // 토큰 검증
        boolean isValid = tokenProvider.validateToken(token.substring(7));
        
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        return ResponseEntity.ok("Token is valid");
    }
    
    @GetMapping("/refreshToken.do")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null || !tokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        try {
            String username = tokenProvider.getUsernameFromToken(refreshToken);
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .orElse("ROLE_USER");
            String newAccessToken = tokenProvider.createToken(username, role);
            String newRefreshToken = tokenProvider.createRefreshToken(username, role); // 새 리프레시 토큰 생성

            // 리프레시 토큰을 쿠키에 저장
            Cookie newRefreshCookie = new Cookie("refreshToken", newRefreshToken);
            newRefreshCookie.setHttpOnly(true);
            newRefreshCookie.setMaxAge(24 * 60 * 60); // 예: 24시간
            response.addCookie(newRefreshCookie);

            return ResponseEntity.ok(Collections.singletonMap("token", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing refresh token: " + e.getMessage());
        }
    }


}
