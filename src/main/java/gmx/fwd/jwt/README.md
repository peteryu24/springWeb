## jwt token + refresh token + security + csrf token
create ***jwt token*** and ***refresh token*** at login (***security login***)
<br><br>
every action requires a ***vaild token*** and ***csrf token***(POST)
<br><br>
if the current token expires, a ***new token*** is given through ***response header cookie***
<br><br>
### spring security settings (tokenFilter before login)
![security xml](https://github.com/peteryu24/peter-web/assets/67302252/9c491796-29d5-4e9f-894c-1d6f6c4d7519)
<br>
### implementing UserDetailService
![implements UserDetailService](https://github.com/peteryu24/peter-web/assets/67302252/2e0d98fd-520a-4d0b-a13f-5ce3bd21a9b9)
<br>
### token example in DevTools
![token](https://github.com/peteryu24/peter-web/assets/67302252/4689e34e-64e3-43c6-9e64-5ce8fac99fe3)
<br>
### refresh token example in DevTools
![refresh token](https://github.com/peteryu24/peter-web/assets/67302252/75910c89-9bff-418b-a42a-520598b9bdf4)
<br>
### the whole process in DevTools <br>verifyToken.do (401 Unauthorized) -> refreshToken.do -> verifyToken.do
![whole process](https://github.com/peteryu24/peter-web/assets/67302252/2d81c04a-819d-4860-bddd-1acff3fb93f4)
<br>
### logic for the whole process in jsp
![whole process in jsp](https://github.com/peteryu24/peter-web/assets/67302252/5617f3a5-d513-401a-8679-90ea3fda47eb)
<br>
### pom.xml for jwt token and spring security
![pom for token](https://github.com/peteryu24/peter-web/assets/67302252/8354d7b9-cead-4c05-832c-55ee3ce85c29)


