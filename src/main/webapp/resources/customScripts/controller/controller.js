app.controller("TimeController",function($scope,$http) {
	$scope.departments = [{}];
	$scope.addMoreDepartment = function() {
		console.log("addMoreDepartment");
		$scope.departments.push({});
	};
	
	$scope.saveDepartments = function() {
		$http({url:'saveDepartments',method:'POST',data:$scope.departments}).success(function(response){
			
		});
	};
});
