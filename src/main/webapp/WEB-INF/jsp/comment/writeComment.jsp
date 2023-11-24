<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/helloMonkey/">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <link rel="stylesheet" href="/helloMonkey/css/comment/writeComment.css">
    <title>Write a Comment</title>
</head>
<body>

    <div class="commentForm">
        <form id="writecommentForm" onsubmit="return writeComment(event)">
            <label for="comment">Submit your Comment</label>
            <sec:csrfInput/>
            <!-- 숨겨서 전송 -->
            <input type="hidden" name="postId" value="${postId}"> 
            <input type="text" id="comment" name="comment" required="required">
            
            <input type="submit" value="Post" class="postButton" />
            <button class="goBackButton" type="button" onclick="location.href='post/detailPost.do?postId=${postId}'">Go Back</button>
        </form>
    </div>

    <script>
	    $(document).ready(function() {
	        let token = localStorage.getItem('jwtToken');
	        csrfToken = $("input[name='_csrf']").val();
	
	        if (!token) {
	            refreshToken();
	        } else {
	            verifyToken();
	        }
	    });
	
	    function verifyToken(attemptedRefresh = false) {
	        let token = localStorage.getItem('jwtToken');
	        if (token) {
	            $.ajax({
	                url: 'token/verifyToken.do',
	                type: 'GET',
	                beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Authorization", "Bearer " + token);
	                },
	                success: function(response) {
	                    console.log('Token is valid');
	                },
	                error: function(error) {
	                    if (!attemptedRefresh) {
	                        refreshToken();
	                    } else {
	                        handleTokenError();
	                    }
	                }
	            });
	        }
	    }
	
	    function refreshToken() {
	        $.ajax({
	            url: 'token/refreshToken.do',
	            type: 'GET',
	            success: function(response) {
	                console.log('Success in refreshToken. Token is => ', response.token);
	                localStorage.setItem('jwtToken', response.token);
	                console.log('Refreshed token');
	                verifyToken(true);
	            },
	            error: function(xhr) {
	                console.log('Error in refreshToken');
	                handleTokenError(xhr);
	            }
	        });
	    }

	
	    function handleTokenError(xhr = null) {
	        let errorMessage = xhr ? (xhr.status + ': ' + xhr.statusText) : 'Unknown error';
	        alert('Authentication failed - ' + errorMessage);

	        localStorage.removeItem('jwtToken');
	        window.location.href = 'user/logout.do';
	    }
		
        function writeComment(event) {
            event.preventDefault();
            let formData = $('#writecommentForm').serialize();
            let token = localStorage.getItem('jwtToken');
            $.ajax({
                url: 'comment/writeComment.do',
                type: 'POST',
                data: formData,
                success: function(response) {
                	if(response.status === "success"){
                		alert("댓글 작성 완료");
                    window.location.href = 'post/detailPost.do?postId=' + $('[name="postId"]').val();
                	}else{
                		alert("댓글 작성 실패");
                	}
                },
                error: function(error) {
	            	alert('ajax error', error);
	            }
            });
        }

    </script>

</body>
</html>
