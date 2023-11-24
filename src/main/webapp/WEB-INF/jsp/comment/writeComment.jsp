<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/egov11/">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <link rel="stylesheet" href="/egov11/css/comment/writeComment.css">
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
	
	
	 	    if (!token) {
	 	    	alert('Token Expired');
	         	localStorage.removeItem('jwtToken');
	             console.log(xhr.responseText);
	             window.location.href = 'user/logout.do';
	 	    }
	
	 	    $.ajax({
	 	        url: 'token/verifyToken.do', 
	 	        type: 'GET',
	 	        beforeSend: function(xhr) {
	 	            xhr.setRequestHeader("Authorization", "Bearer " + token);
	 	        },
	 	        success: function(response) {
	 	            console.log('Token is valid');
	 	        },
	 	        error: function() {
	 	            alert('Token is invalid or expired');
	 	            localStorage.removeItem('jwtToken');
	 	            window.location.href = 'user/logout.do'; 
	 	        }
	 	    });
	    });
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
