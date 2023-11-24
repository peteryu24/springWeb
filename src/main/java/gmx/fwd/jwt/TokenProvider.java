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

	@Value("${security.jwt.token.expire-length:10000}") // 1min: 60000, 5min: 300000, 24h: 86400000
	private long validityInMilliseconds;
	
	@Value("${security.jwt.token.refresh-expire-length:20000}") 
	private long refreshValidityInMilliseconds;

	
	private String encodedSecretKey;

	@PostConstruct
	protected void init() {
		// secretKey 초기화
		encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//JWT 생성, 검증 시 init에서 인코딩 한 secretKey를 Base64 디코딩하여 사용
	protected String createToken(String username, String role) { // 토큰 생성

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", role);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		
		byte[] decodedSecretKey = Base64.getDecoder().decode(encodedSecretKey);

		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(validity)
					.signWith(SignatureAlgorithm.HS256, decodedSecretKey)
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
	                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(encodedSecretKey))
	                .compact();
	}

	protected String getUsernameFromToken(String token) {
	    byte[] decodedSecretKey = Base64.getDecoder().decode(encodedSecretKey);
	    return Jwts.parser().setSigningKey(decodedSecretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public Authentication getAuthentication(String token) {
		String username = getUsernameFromToken(token); // 토큰으로부터 사용자 이름 추출
		System.out.println("getAuthentication in TokenProvider is called");
        UserDetails userDetails = userDetailService.loadUserByUsername(username); // 사용자 상세 정보 로드
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	// init에서 인코딩 한 secretKey를 Base64 디코딩하여 사용
	protected boolean validateToken(String token) {
	    try {
	    	byte[] decodedSecretKey = Base64.getDecoder().decode(encodedSecretKey);
	        Jwts.parser().setSigningKey(decodedSecretKey).parseClaimsJws(token);
	        return true;
	    } catch (JwtException | IllegalArgumentException e) {
	    	System.out.println("Expired or invalid JWT token" + e.getMessage());
	        return false; 
	    }
	}


}
