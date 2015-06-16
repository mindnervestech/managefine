app.controller("ClientInfoController",function($scope,$http) {
	
	
	 $http({method:'GET',url:'clientinformation'}).success(function(response) {
	    	console.log(response);
	    	$scope.projectType = response;
	    });
	
	$scope.selectCustomer = function(){
	   console.log("Hiiii");	
	 
	}
	
});