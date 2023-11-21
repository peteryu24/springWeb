<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<base href="http://localhost:8080/yellowAsian/">
	<script type="text/javascript" src="/yellowAsian/js/lib/jquery-3.2.0.min.js"></script>
	<link rel="stylesheet" href="/yellowAsian/css/login/login.css">
    <title>Login Page</title>
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
	                alert('Login failed');
	            }
	        });
	    }

	</script>

</body>

</html>
