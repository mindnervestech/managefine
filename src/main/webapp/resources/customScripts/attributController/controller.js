app.controller("attributesController",function($scope,$http,ngDialog,$upload) {
		
   // $scope.projectsearch.projectValue.push({});
	$scope.newprojectValue = function($event){
		$scope.projectsearch.projectValue.push( { } );
		$event.preventDefault();
		console.log($scope.projectsearch.projectValue);
	};
	
	$scope.saveProjectInfo = function(){
		console.log("Hiiiiiiii");
	}
	
});