package gmx.fwd.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import gmx.fwd.security.MyUserDetailService;

import java.util.Date;

import javax.servlet.http.Cookie;

@Component
public class TokenProvider {
    
    @Autowired
    private MyUserDetailService userDetailService;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:60000}") // 1min: 60000, 5min: 300000, 24h: 86400000
    private long validityInMilliseconds;
    
    @Value("${security.jwt.token.refresh-expire-length:300000}") 
    private long refreshValidityInMilliseconds;
    
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24; // a day

    // JWT 생성 시 사용
    protected String createToken(String username, String role) { // 토큰 생성

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                    .compact();
    }
    
    protected String createRefreshToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                    .compact();
    }
    
    protected Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/"); // 애플리케이션의 모든 경로에서 쿠키 사용
        refreshCookie.setMaxAge(COOKIE_MAX_AGE); // a day
      //refreshCookie.setSecure(false); // 개발 환경이 HTTP인 경우 false로 설정
        return refreshCookie;
    }

    protected String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token); // 토큰으로부터 사용자 이름 추출
        UserDetails userDetails = userDetailService.loadUserByUsername(username); // 사용자 상세 정보 로드
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    
    // 토큰 검증
    protected boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Expired or invalid JWT token" + e.getMessage());
            return false; 
        }
    }
}
