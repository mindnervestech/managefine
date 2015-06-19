app.controller("SupplierInfoController",function($scope,$http) {
	
	
	 $http({method:'GET',url:'supplierinformation'}).success(function(response) {
	    	console.log(response);
	    	$scope.supplierlist = response;
	    });
	
	$scope.selectCustomer = function(){
	   console.log("Hiiii");	
	 
	}
	
	$scope.viewdata=function(id){
		console.log("id");
		console.log(id);
		
		 $http({method:'GET',url:'selectedsupplierinfo',params:{id:id}}).success(function(data) {
		    	console.log(data);
		    	$scope.supplierlist1 = data;
		    });
	}
	

	
});