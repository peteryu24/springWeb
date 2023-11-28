<h2>Aop를 이용한 로그 남기기</h2>
@Aspect 어노테이션을 감지할 수 있도록 <span style="font-weight: 900;">AopInit.java</span>에서 @Configuration, @EnableAspectJAutoProxy를 초기화
<br>
그리고 WriteLog.java에서는 @Aspect, @Component를 명시합니다.
