<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://localhost:8080/egov30/">
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
        <button class="loginLog" type="button" onclick="setCategoryToLogin()">Login Log</button>
        <button class="writePostLog" type="button" onclick="setCategoryToPost()">Post Log</button>
        <button class="writePostLog" type="button" onclick="deleteCategorySession()">View All</button>       
        <button class="goBack" type="button" onclick="goBackAndClearSession()">Go<br>Back</button>
    </div>
    
    
    <script type="text/javascript">
	    $(document).ready(function() {
	        showAllPosts();  
	        showPageNum();
	    });
	    
	    var pageController = (function() {
	        return {
	            setcurrentPageForLog: function(page) {
	                currentPageForLog = page;
	                sessionStorage.setItem('currentPageForLog', currentPageForLog);
	                console.log("currentPageForLog session setting됨!")
	            },
	            setTotalPage: function(page) {
	                totalPage = page;
	                sessionStorage.setItem('totalPage', totalPage);
	                console.log("setTotalPage session setting됨!")
	            },
	            setCategory: function(category) {
	            	category = category;
	            	sessionStorage.setItem('category', category);
	            	console.log("setCategory session setting됨!")
	            }
	        };
	    })();
	    
        function showAllPosts() {
            $.ajax({
                url: "log/logsPerPage.do", 
                type: "GET",
                dataType: "json",
                data: {
                    currentPage: sessionStorage.getItem('currentPageForLog'),
                    category: sessionStorage.getItem('category')
                },
                success: Posts, 
                error: function(xhr, status, error) {
                    alert("error");
                    console.log("에러: " + error);
                    console.log(xhr.responseText);
                }
            });
        }
        
        function showPageNum(totalPage) {
            if (totalPage) {
                generatePageNumbers(totalPage);
            } else {
                $.ajax({
                    url: "log/totalPageNum.do",
                    type: "GET",
                    dataType: "json",
                    data: {
                    	category : sessionStorage.getItem('category')
                    },
                    success: function(response) {
                    	console.log(response);
                        var fetchedTotalPage = response.totalPage;
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
        }
        
        function generatePageNumbers(totalPage) {
            var pageNumbersClick = '';
            for (var i = 1; i <= totalPage; i++) {
                pageNumbersClick += '<span class="pageNumber">' + i + '</span>';
            }
            $("#pageNumbers").html(pageNumbersClick);

            $(".pageNumber").on('click', function() {
                var pageNumber = $(this).text();
                pageController.setcurrentPageForLog(pageNumber);
                console.log("페이지로 이동",currentPageForLog)
               
                $.ajax({
                    url: "log/logsPerPage.do",
                    type: "GET",
                    dataType: "json",
                    data: {
                        currentPage: sessionStorage.getItem('currentPageForLog'),
                        category: sessionStorage.getItem('category')
                    },
                    success: function(data) {
                        Posts(data);
                    },
                    error: function(xhr, status, error) {
                        alert("Error");
                        console.log("Error: " + error);
                        console.log(xhr.responseText);
                    }
                });
            });
        }

		// 동적으로 받은 게시글들 보여주기
        function Posts(data) { 
        	console.log(data)
            var tbody = $("#logsTbody");
            tbody.empty();
            data.forEach(function(log) {
                var createTime = new Date(log.createTime).toLocaleString(); 
                var $tr = $('<tr>');
                var $td = $('<td>');
                
                $tr.append($td.clone().text(log.category)); 
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
        	pageController.setCategory("login");
        	showAllPosts();
        	showPageNum();
        }
        
        function setCategoryToPost() {
        	pageController.setCategory("Post");
        	showAllPosts();
        	showPageNum();
        }
        
        function deleteCategorySession() {
        	sessionStorage.clear();
        	showAllPosts();
        	showPageNum();
        }
		
    </script>
</body>
</html>