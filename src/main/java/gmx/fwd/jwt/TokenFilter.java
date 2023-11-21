package gmx.fwd.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;

public class TokenFilter extends GenericFilterBean {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		System.out.println("doFilter in TokenFilter is called");
		String token = resolveToken((HttpServletRequest) req); // http 요청으로 토큰 추출
		System.out.println("token is " + token);
		try {
			if (token != null && tokenProvider.validateToken(token)) {
				// 유효성 검증
				Authentication auth = token != null ? tokenProvider.getAuthentication(token) : null;
				// 인증 정보 설정
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (ExpiredJwtException e) {
			System.out.println("Token Expired");
		}
		filterChain.doFilter(req, res);
	}

	private String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		System.out.println("bearerToken is: " + bearerToken);
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // bearer 토큰 추출
			return bearerToken.substring(7);
		}
		return null;
	}
}
