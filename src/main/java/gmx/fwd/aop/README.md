## log using AOP
@Aspect 어노테이션을 감지할 수 있도록 **AopInit.java** 에서 @Configuration, @EnableAspectJAutoProxy 명시
<br><br>
그리고 **WriteLog.java** 에서는 @Aspect, @Component를 명시
<br><br>
### declare logger Object & set Pointcut
![logger](https://github.com/peteryu24/peter-web/assets/67302252/7da0a524-cb73-4ff6-bcf3-83546da0a4ed)
### logger on transaction (@AfterThrowing)
![error for transaction](https://github.com/peteryu24/peter-web/assets/67302252/6150225e-78e3-4cb5-9a18-fe0185401b28)
### logger at login (@AfterReturning)
![logger logic](https://github.com/peteryu24/peter-web/assets/67302252/26ac04f6-f215-4b01-acfe-73c9e5552fb0)





