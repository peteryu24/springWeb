<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/helloMonkey/">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <link rel="stylesheet" href="/helloMonkey/css/comment/changeComment.css">
    <title>Edit your Comment</title>
    <style>
        
    </style>
</head>
<body>

    <div class="commentForm">
        <form id="newComment" onsubmit="return changeComment(event)">
        	<sec:csrfInput/>
            <input type="hidden" name="commentId" value="${commentId}">
            <input type="hidden" name="postId" value="${postId}">

            <label for="newComment">Edit your comment:</label>
            <input type="text" id="newComment" name="newComment" required="required">
			<div class="buttonGroup">
            	<input type="submit" value="Update Comment">
            	<button class="goBackButton" type="button" onclick="location.href='post/detailPost.do?postId=${postId}'">Go Back</button>
            </div>	
        </form>
    </div>

    <script>
	    $(document).ready(function() {
	        let token = localStorage.getItem('jwtToken');
	        let csrfToken = $("input[name='_csrf']").val();
	
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
	                    	console.log('from verifyToken to refreshToken');
	                        refreshToken();
	                    } else {
	                    	console.log('from verifyToken to handleTokenError');
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
	 	    
        function changeComment(event) {
            event.preventDefault();
            let formData = $('#newComment').serialize();
            $.ajax({
                url: 'comment/changeComment.do',
                type: 'POST',
                data: formData,
                success: function(response) {
                    if(response.status === "success") {
                        alert("comment 변경 완료");
                        window.location.href = 'post/detailPost.do?postId=' + $('[name="postId"]').val();
                    } else {
                        alert("Error");
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
