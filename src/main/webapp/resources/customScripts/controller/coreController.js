app.controller("flexiAttributeController",function($scope,$http) {
	
	
	$scope.init = function(asJson) {
		$scope.flexiAttr = asJson;
	}
	
	
	$scope.addFlexiAttr = function () {
		
		$scope.flexiAttr.push({
			name:"",
			type:"",
			value:""
				});
	}
	
});
