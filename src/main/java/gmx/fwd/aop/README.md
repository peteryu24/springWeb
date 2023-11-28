## Aop를 이용한 로그 남기기
@Aspect 어노테이션을 감지할 수 있도록 **AopInit.java** 에서 @Configuration, @EnableAspectJAutoProxy 명시
<br>
그리고 **WriteLog.java** 에서는 @Aspect, @Component를 명시
<br><br>
### logger 객체 선언 및 Poincut 설정

