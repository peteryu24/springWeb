<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<script src="../js/post/pageController.js"></script>
	<meta charset="UTF-8">
	<base href="http://localhost:8080/yellowAsian/">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<title>Show Logs</title>
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
	
	#logList {
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
		margin-right: 10px; /* 버튼 사이 간격 조절 */
		border: none;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.3s;
		background-color: #007BFF;
		color: #fff;
	}
	
	.pageNumber:hover {
		color: red;
		cursor: pointer;
	}
	</style>
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
			$.ajax({
				url: "log/showLogs.do",
				type: "GET",
				dataType: "json",
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
				$tr.append($td.clone().text(log.email));
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