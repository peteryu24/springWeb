package gmx.fwd.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// @Aspect를 찾을 수 있도록
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // CGLIB 프록시 활성화
public class AopInit {
    
}
