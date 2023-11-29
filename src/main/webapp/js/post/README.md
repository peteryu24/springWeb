## Generating "PAGING" efficiently
created a setter for page number, category, searched keyword, etc... (sessionStorage)
<br><br>
the created setter is called whenever the referring button is activated
<br><br>
lastly, the process of retrieving the actual posts with AJAX is ***minimized*** <br>being called only once, and it sends what is stored in ***sessionStorage***
### setter for pages
![page setter](https://github.com/peteryu24/peter-web/assets/67302252/a40d4cb0-3bf7-455b-a68b-7d7dfde1d159)
<br>
### referring buttons
![jsp](https://github.com/peteryu24/peter-web/assets/67302252/2c57663f-83c9-4bd7-b783-abb139ab340d)
### ajax ([checkout jsp](https://github.com/peteryu24/peter-web/blob/d0936afabd3a9fafc96b8cffb6f7f6a26ca3b6a1/src/main/webapp/WEB-INF/jsp/post/showAllPosts.jsp))
![func showPosts](https://github.com/peteryu24/peter-web/assets/67302252/64db77a0-0cf7-4e66-ae0b-1c415f910e0a)


