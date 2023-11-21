<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/yellowAsian/">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="/yellowAsian/css/post/writePost.css">
    <title>Write a Post</title>
</head>
<body>
	<% if (request.getAttribute("errorMessage") != null) { %>
	    <div class="error-message">
	        <p><%= request.getAttribute("errorMessage") %></p>
	    </div>
	<% } %>

    <div class="postForm">
        <form id="writePost" action="post/writePost.do" method="post" enctype="multipart/form-data">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required="required">
            
            <label for="content">Content:</label>
            <input type="text" id="content" name="content" required="required">
            
            <label for="uploadFile">Upload File:</label>
            <input type="file" id="uploadFile" name="uploadFile">

            <div class="buttonGroup">
                <button type="submit" class="postButton">Post</button>
                <button class="goBackButton" type="button" onclick="location.href='post/goShowAllPosts.do'">Go Back</button>
            </div>
        </form>
    </div>

</body>
</html>
