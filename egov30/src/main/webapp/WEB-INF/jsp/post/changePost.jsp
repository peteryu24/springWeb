<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/egov30/">
    <title>Edit your Post</title>
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

        .editForm {
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
            margin-top: 10px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .buttonGroup {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .buttonGroup button, .buttonGroup input[type="submit"] {
            width: 48%;
            padding: 10px;
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

    <div class="editForm">
        <form id="changeContent" onsubmit="return changeContent(event)">
            <label for="newContent">Edit your Content</label>
            
            <!-- 숨겨서 전송 -->
            <input type="hidden" name="postId" value="${postId}"> 
		    
            <input type="text" id="newContent" name="newContent" required="required">
            
            <div class="buttonGroup">
                <input type="submit" value="Edit" class="postButton" />
                <button class="goBackButton" type="button" onclick="location.href='post/detailPost.do?postId=${postId}'">Go Back</button>
            </div>
        </form>
    </div>

    <script>

        function checkContent() {
            var newContent = document.getElementById("newContent").value;
            if (newContent.trim() === "") {
                alert("게시글 내용은 null을 허용하지 않습니다.");
                return false;
            }            
            return true;
        }

        function changeContent(event) {
            event.preventDefault(); 
            if (!checkContent()) { 
                return;
            }
            // 폼 데이터 직렬화 key=value로 만들어 ajax로 간편하게 데이터 보냄
            var formData = $('#changeContent').serialize(); // 반환하는 데이터 타입은 기본적으로 application/x-www-form-urlencoded 
            $.ajax({
                url: 'post/changePost.do',
                type: 'POST',
                data: formData, // controller의 consume
                dataType: "json", // controller의 produces
                success: function(response) {
                    if (response.status === "success") {
                        window.location.href = 'post/detailPost.do?postId=' + $('[name="postId"]').val(); // GET 방식임
                    } else {
                        alert("게시물 업데이트 실패");
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
