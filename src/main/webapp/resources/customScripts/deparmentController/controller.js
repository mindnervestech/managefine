app.controller("TimeController",function($scope,$http) {
	$scope.departments = [{}];
	$scope.addMoreDepartment = function() {
		console.log("addMoreDepartment");
		$scope.departments.push({});
	};
	
	$scope.romove = function(index,deptId){
		  console.log(index);
		  $scope.departments.splice(index, 1);
		 // $scope.departments.id = index;
		  $http({url:'deleteDepartments',method:'POST',params:{dId:deptId}}).success(function(response){
		  
		  });  
	  };
	
	$http({url:'getDeparment',method:'POST'}).success(function(response){
		console.log("deparment");
		console.log(response);
		$scope.departments = response;
	});
	
	$scope.saveDepartments = function() {
		console.log("DEPART")
		$http({url:'saveDepartments',method:'POST',data:$scope.departments}).success(function(response){
			
			$http({url:'getDeparment',method:'POST'}).success(function(response){
				console.log("deparment");
				console.log(response);
				$scope.departments = response;
			});
			$.pnotify({
                title: "Success",
                type:'success',
                text: "Department Save Successfully",
            });
			
		});
	};
});
