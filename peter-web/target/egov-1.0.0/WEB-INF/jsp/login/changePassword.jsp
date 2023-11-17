<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="http://localhost:8080/peter-web/">
<title>Change your Password</title>
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

    .passwordContainer {
        width: 250px;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        background-color: #ffffff;
    }

    .inputGroup {
        margin-bottom: 20px;
        position: relative;
    }

    .inputGroup label {
        font-weight: 500;
        color: #555;
        display: block;
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
	    text-align: center;        
	    vertical-align: middle;    
	    transform: none;
    }

    .buttonGroup button {
        background-color: #ccc;
        color: #000;
    }

    .buttonGroup button:hover {
        background-color: #aaa;
    }

    .buttonGroup input[type="submit"] {
        background-color: #007BFF;
        color: #fff;
    }

    .buttonGroup input[type="submit"]:hover {
        background-color: #0056b3;
    }

</style>
</head>
<body>
    <div class="passwordContainer">
        <form action="user/changePasswordAction.do" method="post" onsubmit="return checkPassword()">
            <div class="inputGroup">
                <label for="currentPassword">Current Password</label>
                <input type="password" id="currentPassword" name="currentPassword" required>
            </div>

            <div class="inputGroup">
                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" required>
            </div>

            <div class="inputGroup">
                <label for="confirmNewPassword">Confirm New Password</label>
                <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
            </div>

            <div class="buttonGroup">
                <input type="submit" value="Change">
                <button type="button" onclick="location.href='post/goShowAllPosts.do'">Go Back</button>
            </div>
        </form>
    </div>

    <script>
        function checkPassword() {
            var newPassword = document.getElementById("newPassword").value;
            var confirmPassword = document.getElementById("confirmNewPassword").value;

            if (newPassword !== confirmPassword) {
                alert("Passwords do not match.");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
