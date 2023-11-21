<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/yellowAsian/">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="/yellowAsian/css/comment/changeComment.css">
    <title>Edit your Comment</title>
    <style>
        
    </style>
</head>
<body>

    <div class="commentForm">
        <form id="newComment" onsubmit="return changeComment(event)">
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
        function changeComment(event) {
            event.preventDefault();
            var formData = $('#newComment').serialize();
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
                    alert("Error: " + error);
                }
            });
        }
    </script>

</body>
</html>
