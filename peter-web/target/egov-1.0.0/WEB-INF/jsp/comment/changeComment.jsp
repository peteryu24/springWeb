<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/peter-web/">
    <title>Edit your Comment</title>
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

        .buttonGroup button, .buttonGroup input[type="submit"] {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            background-color: #007BFF;
            color: #fff;
        }

        button:hover, input[type="submit"]:hover {
            background-color: #0056b3;
        }
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
