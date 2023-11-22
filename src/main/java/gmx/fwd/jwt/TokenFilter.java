package gmx.fwd.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.JwtException;

public class TokenFilter extends GenericFilterBean {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        try {
        	
            String token = resolveToken(httpRequest);
            
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            
            filterChain.doFilter(req, res);
            
        } catch (JwtException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
            httpResponse.getWriter().write("Unauthorized: " + e.getMessage());
            return; // 필터 체인의 나머지 부분을 실행하지 않고 요청 처리 중단
        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 상태 코드 설정
            httpResponse.getWriter().write("Internal Server Error: " + e.getMessage());
            return; // 마찬가지로 처리 중단
        }
    }

    private String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
