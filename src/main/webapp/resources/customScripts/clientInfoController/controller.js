app.controller("ClientInfoController",function($scope,$http) {
	
	
	 $http({method:'GET',url:'clientinformation'}).success(function(response) {
	    	console.log(response);
	    	$scope.clientlist = response;
	    });
	
	$scope.selectCustomer = function(){
	   console.log("Hiiii");	
	 
	}
	
	$scope.viewdata=function(id){
		console.log("id");
		console.log(id);
		
		 $http({method:'GET',url:'selectedclientinfo',params:{id:id}}).success(function(data) {
		    	console.log(data);
		    	$scope.clientlist1 = data;
		    });
	}
	

	
});