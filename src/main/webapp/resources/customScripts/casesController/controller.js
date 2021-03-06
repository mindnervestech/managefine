app.controller("casesController",function($scope,$http,$rootScope,ngDialog,$upload,$timeout,$filter,$compile) {
		
	$scope.showEditButton = function() {
		if(!($scope.selectedValue === undefined || $scope.selectedValue === 'undefined')) {
			$http({method:'GET',url:'findCaseFile',params:{id:$scope.selectedValue}}).success(function(data) {
				$( "#dialog" ).dialog({"width":686,"top": 10,"height":560,open: function(){
	                $('.ui-dialog-titlebar-close').addClass('ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only');
	                $('.ui-dialog-titlebar-close').append('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">close</span>');
	            }});
				$scope.fileAttachData = data.caseFlexi;
				$scope.comment = data.comment;
				console.log($scope.comment);
					angular.forEach($scope.comment, function(obj, index){
						$scope.comment[index].commetDate = $filter('date')($scope.comment[index].commetDate, "dd-MM-yyyy");
					return;
				});
		  });
		}
		
	};
	
	
		
		
		  		
		
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
		  $scope.deletefile = function(id){
			  $http({method:'GET',url:'deleteCaseFile',params:{id:id}}).success(function(data) {
				  $.pnotify({
                      title: "Success",
                      type:'Success',
                      text: "Attachment deleted Successfully",
                  });
			  });
			  
		   }
		  $scope.getCaseId = function(id){
			 $scope.userid = id;
			  console.log(id);
			  
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
		    	if(file == null){
		    		$http({method:'POST',url:'saveFileAndNotes1',data:$scope.caseData}).success(function(response) {
			    		 $.pnotify({
		                        title: "Success",
		                        type:'Success',
		                        text: "File and Activity save Successfully",
		                    });
			    	 });
		    	}else{
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
		    	 
		    
		    }
		  
		
});