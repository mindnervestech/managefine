app.controller("FlexiAttributeController",function($scope,$http) {
	
  console.log("---FlexiAttributeController-----");
  
  $scope.loadData = function() {
	  console.log("---FlexiAttributeController load data-----");
  };
  
  $scope.selectEntityFirst = function(value){
	  console.log("select entity");
	  console.log(value);
	  $scope.selecteditem = value;
	  
	  $http({url:'getUserFlexiAttribute',method:'POST',params:{userId:$scope.selecteditem}}).success(function(response){
		  console.log("GET flxexi");
			console.log(response);
			$scope.item = response;
		});
	  
  };
  
  $scope.item = [{name:"",type:"",model:""}
                 ];
  
  $scope.addMore = function(){
	  $scope.item.push({});
	  
  };
  
  $scope.romove = function(index){
	  console.log(index);
	  $scope.item.splice(index, 1);
  };

  $scope.submitFlexiAttribute = function(){
	  console.log("select Flexi");
	  console.log($scope.selecteditem);
	  for(var i=0;i<$scope.item.length;i++){
		  $scope.item[i].model = $scope.selecteditem;  
	  }
	  
	  console.log($scope.item);
	  
	  $http({url:'saveFlexiAttribute',method:'POST',data:$scope.item}).success(function(response){
		  console.log("save flxexi");
			console.log(response);
		});
	  
	
  };
  

});