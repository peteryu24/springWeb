## web project🌱
- spring과 더욱 친해지기 위해 만들어본 게시판(feat. openlayers)
- 회원가입 로그인
- 게시판, 댓글  crud
- aop log
- security + jwt token + csrf token
- openlayers
## toolkit
#### forntend
<a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a>
<a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a>
<a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="javascript" width="40" height="40"/> </a>

#### backend
<a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a>

#### db & etc
<a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a>

<br>
프로젝트 설명
- [project view](https://github.com/peteryu24/peter-web/tree/359551bdda6cd3d0662c07c9358716bb6bf7a0b6/src/main)
- [aop](https://github.com/peteryu24/peter-web/tree/4ec63c5d1ad5d36fcc953d56e03dd78e9988cfb2/src/main/java/gmx/fwd/aop)
- [조회수 어뷰징 방지](https://dudefromkorea.tistory.com/15)
- [gis](https://github.com/peteryu24/peter-web/tree/be1ff5ef19d269f9671344d32999f1dced1e5d1c/src/main/webapp/WEB-INF/jsp/map)
- [jwt](https://github.com/peteryu24/peter-web/tree/c87e34295e7d621410b18cfa8ffcd2cd17aae438/src/main/java/gmx/fwd/jwt)
- [security](https://github.com/peteryu24/peter-web/tree/c87e34295e7d621410b18cfa8ffcd2cd17aae438/src/main/java/gmx/fwd/security)
- [DB 최적화](https://dudefromkorea.tistory.com/16)

### project layout
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
│     │        ├─ aop : logger using aop
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

