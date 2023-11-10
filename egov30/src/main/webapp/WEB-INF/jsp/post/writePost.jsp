<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/egov30/">
    <title>Write a Post</title>
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

        .postForm {
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

        input[type="text"], input[type="file"] {
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

        .buttonGroup button {
            width: 48%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            background-color: #007BFF;
            color: #fff;
        }

        button:hover {
            background-color: #0056b3;
        }
        
        .error-message {
		    color: red;
		    margin-top: 10px;
		}
		
    </style>
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
