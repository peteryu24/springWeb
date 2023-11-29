var pageController = (function() {
	return {
		setCurrentPage : function(page) {
			currentPage = page;
			sessionStorage.setItem('currentPage', currentPage);
			console.log("currentPage session set!")
		},
		setTotalPage : function(page) {
			totalPage = page;
			sessionStorage.setItem('totalPage', totalPage);
			console.log(totalPage);
			console.log("setTotalPage session set!")
		},
		setOrderBy : function(order) {
			orderBy = order;
			sessionStorage.setItem('orderBy', orderBy);
			console.log("orderBy session set!")
		},
		setSearchedKeyword : function(Keyword) {
			searchedKeyword = Keyword;
			sessionStorage.setItem('searchedKeyword', searchedKeyword);
			console.log("searchedKeyword session set!")
		},
		setCategory: function(category) {
        	category = category;
        	sessionStorage.setItem('category', category);
        	console.log("setCategory session set!")
        }
	};
})();
