<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<base href="http://localhost:8080/egov11/">
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<link rel="stylesheet" href="/egov11/css/login/changePassword.css">
	<title>Change your Password</title>
</head>
<body>
    <div class="passwordContainer">
        <form action="user/changePasswordAction.do" method="post" onsubmit="return checkPassword()">
        	<sec:csrfInput/>
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
