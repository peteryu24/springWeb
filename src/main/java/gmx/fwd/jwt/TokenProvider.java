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

import javax.annotation.PostConstruct;

import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {
	
	@Autowired
	MyUserDetailService userDetailService;

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}") // 1h
	private long validityInMilliseconds;

	@PostConstruct
	protected void init() {
		// secret 키 초기화
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, String role) { // 토큰 생성

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", role);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	private String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

	public Authentication getAuthentication(String token) {
		String username = getUsernameFromToken(token); // 토큰으로부터 사용자 이름 추출
		System.out.println("getAuthentication in TokenProvider is called");
        UserDetails userDetails = userDetailService.loadUserByUsername(username); // 사용자 상세 정보 로드
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtException("Expired or invalid JWT token");
		}
	}
}
