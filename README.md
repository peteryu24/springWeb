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

