web project
- spring과 더욱 친해지기 위해 만들어본 게시판 + openlayers
- 회원가입 로그인
-  게시판, 댓글  crud
-  log
-  jwt token, session
-  security
-  openlayers

forntend
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
<br>
backend
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<br>
db & etc
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
<img src="https://img.shields.io/badge/Openlayers-1F6B75?style=for-the-badge&logo=openlayers&logoColor=white">

[aop](https://github.com/peteryu24/peter-web/tree/4ec63c5d1ad5d36fcc953d56e03dd78e9988cfb2/src/main/java/gmx/fwd/aop)
[조회수 어뷰징 방지](https://dudefromkorea.tistory.com/15)
```
🌱 peter-web 
├─ src
│  └─ main
│     ├─ java
│     │  └─ gmx
│     │     └─ fwd
│     │        ├─ controller : controller folder
│     │        ├─ service : service folder
│     │        ├─ mapper : mapper interface(linked with mapper.xml - mybatis)
│     │        ├─ interceptor : session based interceptor(now using token)
│     │        ├─ aop : log with aop
│     │        ├─ jwt : settings for jwt token
│     │        ├─ security : spring security
│     │        └─ vo : known as DAO(Data Access Object)
│     ├─ resources
│     │  ├─ db : query for creating tables
│     │  ├─ gmx
│     │  │  └─ context : folder for settings.xml
│     │  ├─ mapper : xml based query using mybatis
│     │  └─ properties : properties for file downloads
│     └─ webapp
│        ├─ WEB-INF
│        │  ├─ config : place for dispatcher-servlet.xml
│        │  ├─ jsp : folder for jsp files
│        │  └─ web.xml : web settings
│        ├─ css : css folder
│        ├─ error : error.jsp
│        └─ js
│           ├─ lib : library for js
│           ├─ map : js for map(OpenLayers)
│           └─ post : paging settings
└─ pom.xml : maven settings
```

