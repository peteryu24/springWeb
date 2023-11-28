<h2>Aop를 이용한 로그 남기기</h2>
@Aspect 어노테이션을 감지할 수 있도록 <strong>AopInit.java</strong> 에서 @Configuration, @EnableAspectJAutoProxy 명시
<br>
그리고 <strong>WriteLog.java</strong> 에서는 @Aspect, @Component를 명시
