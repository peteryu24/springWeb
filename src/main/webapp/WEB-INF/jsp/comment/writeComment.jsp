<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/yellowAsian/">
    <title>Write a Comment</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f2f2f2;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .commentForm {
            width: 70%;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
            padding: 20px;
            margin-top: 50px;
        }

        label {
            color: #333;
            font-weight: bold;
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .postButton, .goBackButton {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            background-color: #007BFF;
            color: #fff;
            width: 70pt;
            height: 70pt;
        }

        .postButton:hover, .goBackButton:hover {
            background-color: #0056b3;
        }

        .goBackButton {
            background-color: #007BFF;
            margin-left: 20px;
        }
    </style>
</head>
<body>

    <div class="commentForm">
        <form id="writecommentForm" onsubmit="return writeComment(event)">
            <label for="comment">Submit your Comment</label>
            <!-- 숨겨서 전송 -->
            <input type="hidden" name="postId" value="${postId}"> 
            <input type="text" id="comment" name="comment" required="required">
            
            <input type="submit" value="Post" class="postButton" />
            <button class="goBackButton" type="button" onclick="location.href='post/detailPost.do?postId=${postId}'">Go Back</button>
        </form>
    </div>

    <script>
        function writeComment(event) {
            event.preventDefault();
            var formData = $('#writecommentForm').serialize();
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
                    alert("Error occurred: " + error);
                }
            });
        }
    </script>

</body>
</html>
