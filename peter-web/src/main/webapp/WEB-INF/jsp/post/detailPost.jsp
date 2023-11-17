<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="http://localhost:8080/peter-web/">
<title>Detailed view of the Post</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

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
	
	h1 {
		color: #333;
	}
	
	.postDetails {
		width: 70%;
		border-radius: 10px;
		box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
		background-color: #ffffff;
		padding: 15px;
		margin-bottom: 20px;
	}
	
	table {
		width: 100%;
		border-collapse: collapse;
		margin-top: 20px;
	}
	
	th, td {
		padding: 10px;
		text-align: left;
		border-bottom: 1px solid #ddd;
	}
	
	.buttonGroup {
		display: flex;
		justify-content: space-between;
		width: 70%;
		margin-top: 20px;
	}
	
	.buttonGroup button {
		width: 23%;
		padding: 10px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.3s;
		background-color: #007BFF;
		color: #fff;
	}
	
	.writeComment:hover, .editPost:hover, .goBackButton:hover {
		background-color: #0056b3;
	}
	
	.deletePost:hover {
		background-color: #c9302c;
	}
	
	.editButton, .deleteButton, .deleteFile {
		width: auto; /* 너비 자동 조정 */
		padding: 5px 10px; /* 내부 여백 조정 */
		margin-right: 10px; /* 오른쪽 여백 추가 */
		border: none;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.3s;
		background-color: #888; /* 어두운 회색 */
		color: #fff;
	}
	
	/* 버튼 호버 스타일 */
	.editButton:hover {
		background-color: #ccc; /* 얕은 회색 */
	}
	
	.deleteButton:hover, .deleteFile:hover {
		background-color: #c9302c;
	}
</style>
</head>
<body>

	<div class="postDetails">
		<p>Title: ${post.title}</p>
		<p>Content: ${post.content}</p>

		<c:if test="${file != null}">
			<p>
				Attached File: <a href="file/downloadFile.do?fileId=${file.fileId}">${file.fileName}</a>
				<button class="deleteFile"
					onclick="location.href='file/deleteFile.do?fileId=${file.fileId}&postId=${post.postId}'">
					Delete File</button>
			</p>
		</c:if>

		<!-- 댓글이 있을 때만 -->
		<c:if test="${not empty comments}">
			<h3>Comments:</h3>
			<table border="1">
				<thead>
					<tr>
						<th>Comment</th>
						<th>Writer</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="comment" items="${comments}">
						<tr>
							<td>${comment.comment}</td>
							<td>${comment.email}</td>
							<td>
								<button class="editButton"
									onclick="editComment(${comment.commentId}, ${post.postId})">
									Edit</button>

								<button class="deleteButton"
									onclick="deleteComment(${comment.commentId}, ${post.postId})">
									Delete</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>


	<div class="buttonGroup">
		<button class="writeComment" type="button"
			onclick="location.href='comment/goWriteComment.do?postId=${post.postId}'">Write
			Comment</button>
		<button class="editPost" type="button" id="editPostButton">Edit
			Post</button>
		<button class="deletePost" type="button"
			onclick="deletePost(${post.postId})">Delete Post</button>
		<button class="goBackButton" type="button"
			onclick="location.href='post/goShowAllPosts.do'">Go Back</button>
	</div>
	<script>

    $('#editPostButton').click(function(){
        var postId = ${post.postId}; 
        $.ajax({
            url: 'post/checkAvailabilityToChangePost.do', 
            type: 'GET',
            data: { postId: postId },
            dataType: 'json', 
            success: function(response){
                if(response.status === 'success') {
                    window.location.href = response.redirect; // GET 방식
                } else {
                    alert('권한이 없습니다.');
                }
            },
            error: function(){
                alert('ajax 에러');
            }
        });
    });
    
    function deletePost(postId) {
        $.ajax({
            url: 'post/deletePost.do', 
            type: 'GET',
            data: {
                postId: postId
            },
            dataType: 'json', 
            success: function(response){
                if(response.status === 'success') {
                	if(confirm('게시글을 삭제하시겠습니까?')) {
                	window.location.href = response.redirect;
                    alert('게시글 삭제완료');
                	}
                } else {
                    alert('권한이 없습니다.');
                }
            },
            error: function(){
                alert('ajax 에러');
            }
        });       
	}
    
    function editComment(commentId, postId) {
        $.ajax({
            url: 'comment/checkAvailabilityToChangeComment.do', 
            type: 'GET',
            data: {
                commentId: commentId,
                postId: postId
            },
            dataType: 'json', 
            success: function(response){
                if(response.status === 'success') {
                	 window.location.href = response.redirect;
                } else {
                	alert('권한이 없습니다.');
                }
            },
            error: function(){
            	alert('ajax 에러');
            }
        });
    }

    function deleteComment(commentId, postId) {
	    $.ajax({
	        url: 'comment/deleteComment.do', 
	        type: 'GET',
	        data: {
	            commentId: commentId,
	            postId: postId
	        },
	        dataType: 'json', 
	        success: function(response){
	            if(response.status === 'success') {
	            	if(confirm('댓글을 삭제하시겠습니까?')) {
	            	window.location.href = response.redirect;
	                alert('댓글 삭제완료');
	            	}
	            } else {
	                alert('권한이 없습니다.');
	            }
	        },
	        error: function(){
	            alert('ajax 에러');
	        }
	    });       
    }

</script>

</body>
</html>
