스프링 프레임워크를 사용하는 경우, XSS 및 CSRF 방어, 그리고 HTTPS 설정을 다음과 같이 구현할 수 있습니다:

### XSS 방어

1. **입력값 필터링**: `@Valid` 및 `@SafeHtml` (Hibernate Validator)와 같은 어노테이션을 사용하여 입력값을 검증하고 필터링합니다.

2. **출력 인코딩**: JSP의 `<c:out>` 태그나 Thymeleaf의 `${}`를 사용하여 출력값을 인코딩합니다.

3. **Content Security Policy**: 스프링 시큐리티의 `ContentSecurityPolicyHeaderWriter`를 사용하여 CSP를 구성합니다.

4. **안전한 프레임워크 사용**: Thymeleaf와 같은 모던 웹 템플릿 엔진은 기본적으로 XSS 방지 기능을 제공합니다.

### CSRF 방어 및 CORS 정책 구현

1. **스프링 시큐리티의 CSRF 보호**: 스프링 시큐리티는 기본적으로 CSRF 보호 기능을 제공합니다. `.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())`를 사용하여 구성할 수 있습니다.

2. **CORS 설정**: `@CrossOrigin` 어노테이션을 컨트롤러에 사용하거나 스프링 시큐리티의 `CorsConfigurationSource`를 통해 CORS 정책을 정의합니다.

### HTTPS 설정

1. **SSL/TLS 설정**: `application.properties` 또는 `application.yml` 파일에 SSL 관련 설정을 추가합니다.

2. **HTTPS 리디렉션**: 스프링 시큐리티 설정에서 `.requiresChannel().anyRequest().requiresSecure()`를 사용하여 HTTP 요청을 HTTPS로 리디렉트합니다.

3. **HSTS 설정**: 스프링 시큐리티의 `headers().httpStrictTransportSecurity()`를 사용하여 HSTS 정책을 적용합니다.

### JWT와 CSRF 토큰 동시 사용

스프링 시큐리티와 JWT를 사용하는 경우, CSRF 보호를 유지하면서 JWT를 인증 메커니즘으로 사용할 수 있습니다. JWT는 `Authorization` 헤더를 통해 전송되고, CSRF 토큰은 별도의 쿠키나 헤더를 통해 관리됩니다. 이렇게 하면 두 시스템이 독립적으로 작동하면서도 상호 보완적인 보안 계층을 제공합니다.
<br><br>
https://jake-seo-dev.tistory.com/59
<br>
javadoc
