# web project🌱
to be more friendly with Mr.Spring (feat. openlayers)  
<br>    
## software 🌑
- java : 1.8
- spring : 4.2.2.RELEASE
- DB : postgreSQL  42.2.20
## features 📂 
- (un)register, login(log out) 
- posts, comments, file CRUD  
- logger with AOP 
- security + jwt token(refresh token) + csrf token
- openlayers
## toolkit 🛠️
<a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="javascript" width="40" height="40"/> </a>
<a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a>
<a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a>

<a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a>
<a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a>
<br>
<br>
## DEMO 👁️‍🗨️
<img src="https://github.com/peteryu24/peter-web/assets/67302252/6e7b6451-3395-45a5-bc41-eae8f68d2914">
<br> 
<br>

## feature details 📋
- [project preview](https://github.com/peteryu24/peter-web/tree/aaadd5c265ab29c0706ab9951a103482b20e4031/src/main) 
- [aop](https://github.com/peteryu24/peter-web/tree/30158f37a028a0d9ef5e3f8d93da71ddb37c9543/src/main/java/gmx/fwd/aop)
- [cheap query](https://dudefromkorea.tistory.com/16)
- [page control](https://github.com/peteryu24/peter-web/tree/dfaf8e39c8ac3f2c9036f1eb2336b7d18de01231/src/main/webapp/js/post)
- [avoid view count abuse](https://dudefromkorea.tistory.com/15)
- [jwt token + spring security](https://github.com/peteryu24/peter-web/tree/b1b17697b6440258118683e80ee743bc568a1c49/src/main/java/gmx/fwd/jwt)
- [manipulating post / comment](https://github.com/peteryu24/peter-web/blob/1cfac18526c3bc6e06fef12ea48fe60e22b93ff8/src/main/java/gmx/fwd/controller/post)
<br>

## project layout 📌
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
│     │        └─ vo : getter, setter for value object
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
│        └─ js : folder for js(s)
│    
└─ pom.xml : maven settings
```
<br>

## before using ☢️
make sure `<base href="http://localhost:8080/asYouWish/">` is unified on every jsp

