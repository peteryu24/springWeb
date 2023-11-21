<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<base href="http://localhost:8080/yellowAsian/">
	<script src="/yellowAsian/js/post/pageController.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<link rel="stylesheet" href="/yellowAsian/css/log/showLogs.css">
	<title>Show Logs</title>
</head>
<body>
	<div id="logList">
		<table border="1">
			<thead>
				<tr>
					<th>Category</th>
					<th>ID</th>
					<th>Activity</th>
					<th>Time</th>
				</tr>
			</thead>
			<tbody id="logsTbody">
				<!-- 동적으로 추가됨 
                	로그 작성 순으로 오름차순 -->
			</tbody>
		</table>
	</div>

	<div id="pageNumbers"></div>

	<div class="buttonGroup">
		<button class="loginLog" type="button" onclick="setCategoryToLogin()">Login
			Log</button>
		<button class="writePostLog" type="button"
			onclick="setCategoryToPost()">Post Log</button>
		<button class="writePostLog" type="button"
			onclick="deleteCategorySession()">View All</button>
		<button class="goBack" type="button" onclick="goBackAndClearSession()">
			Go<br>Back
		</button>
	</div>


	<script type="text/javascript">
		$(document).ready(function() {
			showLogs();
		});

		function showLogs(){
			
			let token = localStorage.getItem('jwtToken');
			
			$.ajax({
				url: "log/showLogs.do",
				type: "GET",
				dataType: "json",
				beforeSend: function(xhr) {
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
				data: {
					currentPage : sessionStorage.getItem('currentPage'),
					category : sessionStorage.getItem('category')
				},
				success: function(response) {
					Logs(response.logs);
					let fetchedTotalPage = response.totalPage;
					pageController.setTotalPage(fetchedTotalPage);
					generatePageNumbers(fetchedTotalPage);
				},
				error: function(xhr, status, error) {
                	alert("Error");
                    console.log("Error: " + error);
                    console.log(xhr.responseText);
                }
			});
		}

		function generatePageNumbers(totalPage) {
			let pageNumbersClick = '';
			for (let i = 1; i <= totalPage; i++) {
				pageNumbersClick += '<span class="pageNumber">' + i + '</span>';
			}
			$("#pageNumbers").html(pageNumbersClick);

			$(".pageNumber").on('click', function() {
				let pageNumber = $(this).text();
				pageController.setCurrentPage(pageNumber);
				console.log("페이지로 이동", currentPage)
				
				showLogs();

			});
		}

		// 동적으로 받은 게시글들 보여주기
		function Logs(data) {
			console.log(data)
			let tbody = $("#logsTbody");
			tbody.empty();
			data.forEach(function(log) {
				let createTime = new Date(log.createTime).toLocaleString();
				
				let category = 'login'; // login log가 더 많기에
				
		        if (log.category === 2) { 
		        	category = 'post';
		        } 
		        
				let $tr = $('<tr>');
				let $td = $('<td>');

				$tr.append($td.clone().text(category));
				$tr.append($td.clone().text(log.username));
				$tr.append($td.clone().text(log.activity));
				$tr.append($td.clone().text(createTime));
				tbody.append($tr);

			});
		}

		function goBackAndClearSession() {
			sessionStorage.clear();
			location.href = 'post/goShowAllPosts.do';
		}

		function setCategoryToLogin() {
			pageController.setCategory(1);
			sessionStorage.setItem('currentPage', 1);
			showLogs();
		}

		function setCategoryToPost() {
			pageController.setCategory(2);
			sessionStorage.setItem('currentPage', 1);
			showLogs();
		}

		function deleteCategorySession() {
			sessionStorage.clear();
			showLogs();
		}
		
	</script>
</body>
</html>