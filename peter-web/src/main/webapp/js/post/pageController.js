var pageController = (function() {
	return {
		setCurrentPage : function(page) {
			currentPage = page;
			sessionStorage.setItem('currentPage', currentPage);
			console.log("currentPage session setting됨!")
		},
		setTotalPage : function(page) {
			totalPage = page;
			sessionStorage.setItem('totalPage', totalPage);
			console.log("setTotalPage session setting됨!")
		},
		setOrderBy : function(order) {
			orderBy = order;
			sessionStorage.setItem('orderBy', orderBy);
			console.log("orderBy session setting됨!")
		},
		setSearchedKeyword : function(Keyword) {
			searchedKeyword = Keyword;
			sessionStorage.setItem('searchedKeyword', searchedKeyword);
			console.log("searchedKeyword session setting됨!")
		},
		setCategory: function(category) {
        	category = category;
        	sessionStorage.setItem('category', category);
        	console.log("setCategory session setting됨!")
        }
	};
})();