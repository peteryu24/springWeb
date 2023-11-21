<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<script type="text/javascript" src="../js/lib/jquery-3.2.0.min.js"></script>
    <base href="http://localhost:8080/yellowAsian/">
    <title>Login Page</title>
    <style>
        body {
            font-family: 'Roboto', sans-serif; /* 폰트 설정 */
            background-color: #f2f2f2; /* 연한 회색 */
            display: flex;
            justify-content: center; /* 수평 사운데 정렬 */
            align-items: center; /* 수직 가운데 정렬 */
            height: 100vh;
            margin: 0; /* 바깥 여백 X */
        }
        
        .loginContainer {
            width: 250px;
            height: 300px;
            padding: 10px; /* 내부 여백 */
            border-radius: 10px; /* 둥근 모서리 */
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
            background-color: #ffffff; /* 흰색 */
        }

        h1 {
            text-align: center;
            color: #333; /* 어두운 회색 */
            font-weight: 500; /* 글 두께 */
            margin-bottom: 25px; /* 밑 여백 */
        }

        .inputGroup {
            margin-bottom: 20px;
            position: relative; /* 상대 위치 */
        }

        .inputGroup label {
            font-weight: 500;
            color: #555; /* 어두운 회색 */
        }

        .inputGroup input {
            width: 100%;
            padding: 10px 0;
            border: none;
            border-bottom: 2px solid #ccc; /* 회색 실선 테두리 */
            border-radius: 0;
            background: transparent; /* 투명 배경 */
            outline: none;
        }

        .inputGroup input:focus {
            border-bottom-color: #007BFF; /* 파란색 */
        }

        .buttonGroup {
            display: flex;
            justify-content: space-between;
        }

        .loginButton, .registerButton {
            width: 70pt;
            height: 30pt;
            border: none;
            color: #fff; /* 흰색 */
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .loginButton {
            background-color: #007BFF; /* 파란색 */
        }

        .loginButton:hover {
            background-color: #0056b3; /* 진한 파랑 */
        }

        .registerButton {
            background-color: #28a745; /* 그린 */
        }

        .registerButton:hover {
            background-color: #218838; /* 진한 그린 */
        }

    </style>
</head>

<body>
    <div class="loginContainer">
        <h1>Welcome</h1>
        <form id="loginForm">
            <div class="inputGroup">
                <label for="username">ID</label>
                <input type="text" id="username" name="username" required="required">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"required="required">
            </div>
            <div class="buttonGroup">
                <button type="submit" class="loginButton">LOGIN</button>
                <button class="registerButton" type="button" onclick="location.href='user/goRegisterPage.do'">REGISTER</button>
            </div>
        </form>
    </div>

	<script>
	    $(document).ready(function() {
	        $('#loginForm').submit(function(event) {
	            event.preventDefault();
	            login();
	        });
	    });
	
	    function login() {
	        var formData = {
	            'username': $('#username').val(),
	            'password': $('#password').val()
	        };

	        $.ajax({
	            url: 'user/login.do',
	            type: 'POST',
	            data: formData,
	            success: function(response) {
	                localStorage.setItem('jwtToken', response.token);
	                window.location.href = 'post/goShowAllPosts.do';
	            },
	            error: function(xhr) {
	                alert('Login failed: ' + xhr.responseText);
	            }
	        });
	    }

	</script>

</body>

</html>
