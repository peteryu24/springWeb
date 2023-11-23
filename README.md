## Authentication auth = tokenProvider.getAuthentication(token);
<br>
Authentication: 
<br>
org.springframework.security.authentication.UsernamePasswordAuthenticationToken@441d024a: 
<br>
Principal: org.springframework.security.core.userdetails.User@7a: 
<br>
Username: z; 
<br>
Password: [PROTECTED]; 
<br>
Enabled: true; 
<br>
AccountNonExpired: true; 
<br>
credentialsNonExpired: true; 
<br>
AccountNonLocked: true; 
<br>
Granted Authorities: ROLE_USER; 
<br>
Credentials: [PROTECTED]; 
<br>
Authenticated: true; 
<br>
Details: null; 
<br>
Granted Authorities: ROLE_USER

<br><br>
https://jake-seo-dev.tistory.com/59
<br>
javadoc





## showAllPosts.jsp 페이지 로드(게시물 가져올 때)시 토큰 검증함
## changePost.jsp 게시글 수정에 대해서는 토큰과 권한 검증. 뒤로가기에 는 검증하지 않음(어차피 뒤로가기에 해당하는 detailPosts에서 게시글 가져올 때 검증하기에 자동으로 막힘)
