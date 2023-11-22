<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<base href="http://localhost:8080/egov11/">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/egov11/css/post/detailPost.css">
	<title>Detailed view of the Post</title>
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
							<td>${comment.username}</td>
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
	let token = localStorage.getItem('jwtToken');
	
    $('#editPostButton').click(function(){
        var postId = ${post.postId}; 
        $.ajax({
            url: 'post/checkAvailabilityToChangePost.do', 
            type: 'GET',
            data: { postId: postId },
            dataType: 'json', 
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function(response){
                if(response.status === 'success') {
                    window.location.href = response.redirect; // GET 방식
                } else {
                    alert('권한이 없습니다.');
                }
            },
            error: function(xhr, status, error) {
            	alert('Token Expired');
                console.log(xhr.responseText);
                window.location.href = 'user/logout.do';
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
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
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
            error: function(xhr, status, error) {
            	alert('Token Expired');
                console.log(xhr.responseText);
                window.location.href = 'user/logout.do';
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
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function(response){
                if(response.status === 'success') {
                	 window.location.href = response.redirect;
                } else {
                	alert('권한이 없습니다.');
                }
            },
            error: function(xhr, status, error) {
            	alert('Token Expired');
                console.log(xhr.responseText);
                window.location.href = 'user/logout.do';
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
	        beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
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
	        error: function(xhr, status, error) {
            	alert('Token Expired');
                console.log(xhr.responseText);
                window.location.href = 'user/logout.do';
            }
	    });       
    }

</script>

</body>
</html>
