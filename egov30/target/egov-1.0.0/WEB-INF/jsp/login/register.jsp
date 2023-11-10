<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/egov30/">
    <title>Register Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .loginContainer {
            width: 250px;
            height: 350px; 
            padding: 10px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
        }

        h2 { /* Adjusted for h2 */
            text-align: center;
            color: #333;
            font-weight: 500;
            margin-bottom: 25px;
        }

        .inputGroup {
            margin-bottom: 20px;
            position: relative;
        }

        .inputGroup label {
            font-weight: 500;
            color: #555;
        }

        .inputGroup input {
            width: 100%;
            padding: 10px 0;
            border: none;
            border-bottom: 2px solid #ccc;
            border-radius: 0;
            background: transparent;
            outline: none;
        }

        .inputGroup input:focus {
            border-bottom-color: #007BFF;
        }

        .buttonGroup {
            display: flex;
            justify-content: space-between;
        }

        .goBackButton, .registerButton {
            width: 70pt;
            height: 30pt;
            border: none;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .goBackButton {
            background-color: #d9534f; 
        }

        .goBackButton:hover {
            background-color: #c9302c;
        }

        .registerButton {
            background-color: #28a745;
        }

        .registerButton:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="loginContainer">
        <h2>Welcome</h2>
          	<form id="registerForm">
	            <div class="inputGroup">
	                <label for="email">Email</label>
	                <input type="text" id="email" name="email" required="required">
	                
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
