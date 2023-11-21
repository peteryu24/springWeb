<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<base href="http://localhost:8080/yellowAsian/">
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="/yellowAsian/css/login/register.css">
    <title>Register Page</title>
</head>
<body>
    <div class="loginContainer">
        <h2>Welcome</h2>
          	<form id="registerForm">
	            <div class="inputGroup">
	                <label for="username">Email</label>
	                <input type="text" id="username" name="username" required="required">
	                
	                <label for="password">Password</label>
	                <input type="password" id="password" name="password" required="required">
	
	                <label for="nickname">Nickname</label>
	                <input type="text" id="nickname" name="nickname" required="required">
	            </div>
	            
	            <div class="buttonGroup">
	                <button class="goBackButton" type="button" onclick="location.href='user/welcomePage.do'">Go<br>Back</button>
	                <input type="submit" value="Apply" class="registerButton">
	            </div>
           	</form>
    </div>


<script>
    $(document).ready(function(){
        $("#registerForm").on("submit", function(event){
            event.preventDefault(); 
			// 데이터를 key = value 형식으로 직렬화
            var formData = $(this).serialize();

            $.ajax({
                type: "POST",
                url: "user/register.do",
                data: formData,
                dataType: "json",
                success: function(response){
                    if(response.registerFlag === "success") {
                        alert("환영합니다");
                        location.href = "user/welcomePage.do"
                    } else if(response.registerFlag === "fail") {
                        alert("아이디가 중복됩니다");
                        location.reload(); // refresh
                    }
                },
                error: function(){
                    alert("서버와의 통신 중 에러가 발생했습니다.");
                }
            });
        });
    });
</script>
    
</body>
</html>
