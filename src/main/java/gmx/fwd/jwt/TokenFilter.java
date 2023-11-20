package gmx.fwd.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

	private TokenProvider tokenProvider;

	public TokenFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		String token = resolveToken((HttpServletRequest) req); // http 요청으로 토큰 추출
		try {
			if (token != null && tokenProvider.validateToken(token)) {
				// 유효성 검증
				Authentication auth = token != null ? tokenProvider.getAuthentication(token) : null;
				// 인증 정보 설정
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (ExpiredJwtException e) {
			System.out.println("token expired!!");
	        	// 만료된 토큰
	       		HttpServletResponse httpResponse = (HttpServletResponse) res;
	        	httpResponse.sendRedirect("/user/logout.do"); // 리다이렉션 URL 설정
	        	return; 
		}
		filterChain.doFilter(req, res);
	}

	private String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // bearer 토큰 추출
			return bearerToken.substring(7);
		}
		return null;
	}
}
