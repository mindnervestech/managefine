app.controller("casesController",function($scope,$http,$rootScope,ngDialog,$upload,$timeout,$filter,$compile) {
		console.log("yogesh patil");
		console.log("------"); 
		 
		
		/*$scope.getCaseId = function(){
			console.log($('#myitem').val());
			
		}*/
		var value = 21;
		$rootScope.caseId = value;
		var data;
		console.log(value);
		  $http({method:'GET',url:'findCaseFile',params:{id:$rootScope.caseId}}).success(function(data) {
				console.log("Ok----");
				console.log(data);
				$scope.fileAttachData = data.caseFlexi;
				$scope.comment = data.comment;
					angular.forEach($scope.comment, function(obj, index){
					
						$scope.comment[index].commetDate = $filter('date')($scope.comment[index].commetDate, "dd-MM-yyyy");
					return;
				});
				
				console.log($scope.fileAttachData);
				
		  });		
		
		 $scope.downloadNotesfile = function(id){
			 
			  console.log(id);
			   $.fileDownload('downloadCaseNotesFile',
				{	   	
					   httpMethod : "POST",
					   data : {
						   attchId : id,
						  
					   }
				}).done(function(e, response)
						{
						}).fail(function(e, response)
						{
							// failure
						});
		 }
		  
		  $scope.downloadfile = function(id){
			  console.log("Doc.....Id");
			  console.log(id);
			   $.fileDownload('downloadCaseFile',
				{	   	
					   httpMethod : "POST",
					   data : {
						   attchId : id,
						  
					   }
				}).done(function(e, response)
						{
						}).fail(function(e, response)
						{
							// failure
						});
		    
		   }
		
		  
		    var file = null;
		    $scope.selectFile = function(files) {
		    	file = files[0];
		    	console.log(file);
		    };
		    $scope.saveCaseInfo = function(){
		    	$scope.caseData.caseId = $('#myitem').val();
		    	$scope.caseData.type = "case";
		    	console.log($scope.caseData);
		    	$upload.upload({
		            url: 'saveFileAndNotes',
		            data: $scope.caseData,
		            file: file,
		            method:'post'
		        }).progress(function (evt) {
		            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
		            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
		        }).success(function (data, status, headers, config) {
		            console.log(data);
		            $.pnotify({
                        title: "Success",
                        type:'Success',
                        text: "File and Activity save Successfully",
                    });
		           
		        });
		    	
		    
		    }
		  
		
});