app.controller("createProjectController",function($scope,$http,$rootScope,ngDialog,$upload,$timeout,$filter,$compile) {
	
	$scope.progressValue = 150;
	$scope.definePartSaveMsge = 0;
	$scope.defineParts= {
			 partsValue:[],
			 projectId:'',
			 totalEstimatedRevenue:''
	    	};
	$scope.index = 10;
    $scope.Message = "";
    maximumId = 2;
    $rootScope.MainInstance = 0;
    $scope.selectRootNode= null;
    $scope.projectsearch= {
    		projectValue:[],
    		parentId:'',
    		projectId:'',
    		projectTypes:'',
    		projectDescription:'',
    		projectColor:''
    	};
    
    var selectedItems = [];
    var buttons = [];
    
     
    //buttons.push(new primitives.orgdiagram.ButtonConfig("add", "ui-icon-person", "Add"));
	buttons.push(new primitives.orgdiagram.ButtonConfig("edit", "ui-icon-gear", "Edit"));
	buttons.push(new primitives.orgdiagram.ButtonConfig("editInfo", "ui-icon-close", "EditInfo"));
	$scope.data = {};
    $scope.initDate = function (startTime, endTime,createdDate,productionDate,projectLastUpdate) {
    	console.log("endTime" + endTime) ;
    	console.log(productionDate);
    	console.log(createdDate);
    
    	var arrlast = projectLastUpdate.split("-");
    	var lastArr = arrlast[2]+"-"+arrlast[1]+"-"+arrlast[0];
    	
    	var arrPdate = productionDate.split("-");
    	var aDateArr = arrPdate[2]+"-"+arrPdate[1]+"-"+arrPdate[0];
    	
    	var arrCdate = createdDate.split("-");
    	var cDateArr = arrCdate[2]+"-"+arrCdate[1]+"-"+arrCdate[0];
    	console.log(lastArr);
    	console.log(aDateArr);
       	
    	$scope.data.startDate = new Date(moment(startTime,"YYYY-MM-DD")); 
    	$scope.data.endDate =	new Date(moment(endTime,"YYYY-MM-DD"));
    	$scope.data.createdDate = createdDate;//new Date(moment(cDateArr,"YYYY-MM-DD"));
    	$scope.data.productionDate = productionDate;//new Date(moment(aDateArr,"YYYY-MM-DD"));
    	$scope.data.projectLastUpdate = projectLastUpdate;//$filter('date')(lastArr, "yyyy-MM-dd");
    	//$scope.data.projectLastUpdate = new Date(moment(lastArr,"YYYY-MM-DD"));
    	console.log($scope.data);
    		
    }
	$scope.myOptions = {
    		cursorItem : 0,
    		highlightItem : 0,
    		hasSelectorCheckbox : primitives.common.Enabled.True,
    		templates : [getContactTemplate()],
    		defaultTemplateName : "contactTemplate",
    		buttons:buttons,
    		selectedItems:selectedItems,
    	
    };
	
	$scope.editdata = {};
	$scope.myOptions.onButtonClick = function (e, data) {
		
		switch (data.name) {
        	case "editInfo":
        		$scope.fileAttachData = null;
        		$rootScope.currentParentId = data.context.id;
        			$http({method:'GET',url:'/time/findAttachFile',params:{id:$rootScope.currentParentId,mainInstance:$rootScope.MainInstance}}).success(function(data) {
        				console.log("Ok----");
        				console.log(data);
        				$scope.fileAttachData = data;
        				$scope.taskCompilation = $scope.fileAttachData.taskCompilation;
        				$scope.completeStatus =   $scope.fileAttachData.comment;
        				angular.forEach($scope.fileAttachData.projectAttachment, function(obj, index){
        					
        					$scope.fileAttachData.projectAttachment[index].docDate = $filter('date')($scope.fileAttachData.projectAttachment[index].docDate, "dd-MMM-yyyy");
        					return;
        				});
        				angular.forEach($scope.fileAttachData.projectcomments, function(obj, index){
        					
        					$scope.fileAttachData.projectcomments[index].commetDate = $filter('date')($scope.fileAttachData.projectcomments[index].commetDate, "dd-MMM-yyyy");
        					return;
        				});
        				
        				console.log($scope.fileAttachData);
        				
        				if($scope.fileAttachData.comment != "notAllow"){
        					
        					if($scope.fileAttachData.thisNodeId != null){
                    			ngDialog.open({
                        			template:'addProjectNotsAndAtt',
                        			scope:$scope,
                        			closeByDocument:false
                        			
                        		}); 
                				}else{
                					console.log("First Add Node");
                					$.pnotify({
                                        title: "Error",
                                        type:'error',
                                        text: "First Add Data",
                                    });
                				}
        					
        				}else{
        					/*$.pnotify({
                                title: "Error",
                                type:'error',
                                text: "First Add Data",
                            });*/
        					
        				}
        				
        				
        			});
        			
        		
        		break;
        	case "add":
        	
        		console.log("=-=-=-=-=");
        		console.log(data.context.id);
        		console.log($rootScope.MainInstance);
        		console.log("=-=-=-=-=");
        		
        		$http({method:'GET',url:'AddJspPage',params:{id:data.context.id,mainInstance:$rootScope.MainInstance}}).success(function(data) {
        			console.log(data);
        			        			
        			ngDialog.open({
            			template:data,
            			plain:true,
            			//controller: 'ProjectHierarchyController',
            			scope:$scope,
            			closeByDocument:false,
            			className: 'ngdialog-theme-plain'
            		});
        			        			
        			
        		});
        		
        		
        		break;
        	case "edit":
        		
        		$rootScope.currentParentId = data.context.id;
        		
    			$http({method:'GET',url:'/time/selectedUser',params:{mainInstance:$rootScope.MainInstance,projectId:data.context.id}}).success(function(response) {
    				console.log(response);
    				
    				
    				$http({method:'GET',url:'/time/findselectedAllUser',params:{mainInstance:$rootScope.MainInstance,projectId:data.context.id}}).success(function(response1) {
        	    		console.log(response1);
        	    		
        	    		var selectedUsers = [];
        	    		$scope.allUser = response1;
        	    		angular.forEach($scope.allUser,function(user,key) {
        	    			console.log(user.id);
    	        	    	angular.forEach(response,function(value,key1) {
    	    	    			console.log(value);
    	        	    		if(user.id == value){
    	        	    			selectedUsers.push(user);
    	        	    		}
    	        	    	});
            	        	});
            	    	$scope.findSelectedUser = response;
            	    	$scope.findUser = $scope.allUser;
            	    	console.log($scope.findUser);
            	    	console.log($scope.findSelectedUser);
            	    	if(data.context.id == 1)
            	    		$scope.findUser = $scope.allUser;
        	    	});
    				
	    	    	
	    	    });
    			
    			
    			 $http({method:'GET',url:'/time/selectedSupplier',params:{mainInstance:$rootScope.MainInstance}}).success(function(response) {
    		  	    	console.log(response);
    		  	    	$scope.findSelectedSupplier = response;
    		     	    });
    			
    			 $http({method:'GET',url:'/time/findCliect'}).success(function(response) {
     				console.log(response);
     		    	$scope.findCliect = response;
     		    });
    			 
    			$http({method:'GET',url:'EditJspPage',params:{id:data.context.id,mainInstance:$rootScope.MainInstance}}).success(function(data) {
        			ngDialog.open({
            			template:data,
            			plain:true,
            			//controller: 'ProjectHierarchyController',
            			scope:$scope,
            			closeByDocument:false,
            			className: 'ngdialog-theme-plain'
            		});
        		});

        		
        		break;	
		}
	};
	 
	 
	$scope.defineParts.partsValue.push({});
	$scope.newpartValue = function($event){
		$scope.defineParts.partsValue.push( {  } );
		$event.preventDefault();
		console.log($scope.defineParts.partsValue);
		$scope.definePartSaveMsge = 0;
	};
	
	$http({method:'GET',url:'/time/getAllPartNo'}).success(function(response) {
	    
	    	$scope.partNos = response;
	    	console.log($scope.partNos);
  	});
	
	$scope.allowDefinePart;
	
	
	$scope.saveParts = function(){
		$scope.defineParts.projectId = $rootScope.MainInstance;
		//$scope.defineParts.totalEstimatedRevenue = $scope.totalEstimat;
		console.log($scope.defineParts);
				
		 $http({method:'POST',url:'/time/saveDefineParts',data:$scope.defineParts}).success(function(response) {
	 			console.log(response);
	 			$scope.definePartSaveMsge = 1;
	 			
	 	});
	}
	
	$scope.removeDefinepart = function(index){
		
		console.log("Remove...");
		console.log($scope.totalEstimat);
		console.log($scope.defineParts.partsValue);
		console.log(index);
		angular.forEach($scope.defineParts.partsValue,function(value,key) {
			console.log(value.estimatedRevenue);
			if(key == index){
				console.log(value.estimatedRevenue);
				$scope.totalEstimat = $scope.totalEstimat - value.estimatedRevenue;
				$scope.defineParts.partsValue.splice(index, 1);
			}
			
		});
		$scope.definePartSaveMsge = 0; 
		$scope.defineParts.totalEstimatedRevenue = $scope.totalEstimat;
	}
	
	$scope.calculatestimat = function(annualQty,suggestedResale,index){
		$scope.totalEstimat = 0;
		console.log(annualQty);
		console.log(suggestedResale);
		console.log(index);
		if(suggestedResale == undefined || suggestedResale == ""){
			suggestedResale= 1;
		}
		if(annualQty == undefined || annualQty == ""){
			annualQty = 1;
		}
		
		angular.forEach($scope.defineParts.partsValue,function(value,key) {
			if(key == index){
				value.estimatedRevenue = annualQty * suggestedResale;;
			}
			$scope.totalEstimat = $scope.totalEstimat + value.estimatedRevenue;
		});
		console.log($scope.totalEstimat);
		$scope.defineParts.totalEstimatedRevenue = $scope.totalEstimat;
	}
	
	$scope.closeThisDialog  = function(){
		console.log("HERE :::::::::::::::::::::: ");
		ngDialog.close();
	} 
    var items = [
    ];
    
    $scope.pro = {};
    $http({method:'GET',url:'AllProjectType'}).success(function(response) {
    	console.log(response);
    	$scope.projectType = response;
    });
    
    $http({method:'GET',url:'findCliect'}).success(function(response) {
    	console.log(response);
    	$scope.findCliect = response;
    });
    
    $http({method:'GET',url:'findSupplierData'}).success(function(response) {
    	console.log(response);
    	$scope.findSupplier = response;
    });
    
    $http({method:'GET',url:'findUser'}).success(function(response) {
    	console.log(response);
    	$scope.findUser = response;
    	$scope.allUser = response;
    });
    
  
    
    $scope.saveProjectType = function(){
    	console.log("-----------");
    	console.log($scope.pro);
    	
    	 $http({method:'POST',url:'saveproject',data:$scope.pro}).success(function(response) {
 			console.log("Ok"); 
 			console.log(response);
 			 $http({method:'GET',url:'AllProjectType'}).success(function(response) {
 		    	console.log(response);
 		    	$scope.projectType = response;
 		    });
 			ngDialog.close();
 			});
    	
    }
   
    $scope.onProjectTypeChange = function(id){
    	console.log(id);
    	
    }
    
    $scope.addHierarchyProjectType = function(){
    	ngDialog.open({
			template:'addProjectType',
			scope:$scope,
			closeByDocument:false,
			
		});
    }
   
    $scope.viewHierarchy = function(id,rootId,pName,cName,sDate,eDate){
    	console.log(id);
    	console.log(rootId);
    	$scope.cName = cName;
    	$scope.sDate = sDate;
    	$scope.eDate = eDate;
    	$rootScope.MainInstance = rootId;
    	$scope.selectRootNode = id;
    	var className = 'showDataPnotify';
    	
    	//if(data[0].day == 'sunday') {
		//	$scope.sundayData = data;
		//	$scope.dayName = "Sunday";
			htmlTemplate = '<table class="table"><tr><td>Client Name :</td><td>{{cName}}</td></tr><tr><td>StartDate</td><td>{{sDate}}</td></tr><tr><td>EndDate</td><td>{{eDate}}</td></tr></table>';
		//}
		
		$.pnotify({
		    title: pName,
		    type:'info',
		    text: htmlTemplate,
		    addclass: className,
		    hide: false,
		    sticker: false
		});
		var element = $('.'+className);
		$compile(element)($scope);
    	
    	
    	console.log($scope.selectRootNode);
    	items = [];
    	
    	$http({method:'GET',url:'/time/selectAllProjectType',params:{id:id,rootId:rootId}}).success(function(data) {
    		console.log("---====---");
        	console.log(data);
        	if(data.length>0) {
        		$scope.projectid = data[0].projectId;
        		angular.forEach(data,function(value,key) {
        			if(value.parentId == null){
        				value.projectTypes = pName;
        			}
        			items.push(new primitives.orgdiagram.ItemConfig({
        				id: value.id,
        				parent: value.parentId,
        				projectTypes: value.projectTypes,
        				projectDescription: value.projectDescription,
        				projectCompleted: value.completed,
        				status: value.status,
           				itemTitleColor: primitives.common.Colors.RoyalBlue
        			}));
        			selectedItems.push(value.id);
        		});
        	} else {
        		items.push(new primitives.orgdiagram.ItemConfig({
    				id: 0,
    				parent: null,
    				projectTypes: "Root",
    				projectDescription: "",
    				itemTitleColor: primitives.common.Colors.RoyalBlue
    			}));
        	}
        	$scope.myOptions.selectedItems = selectedItems;
        	$scope.myOptions.items = items;
        	
    		console.log($scope.myOptions);	
    	});
    	
    	$scope.selectSupplier = [];
    	$scope.selectUser = [];
    	 
    	 $http({method:'GET',url:'/time/findSupplierData'}).success(function(response) {
  	    	console.log(response);
  	    	$scope.findSupplier = response;
  	    	
    	  $http({method:'GET',url:'/time/selectedSupplier',params:{mainInstance:$rootScope.MainInstance}}).success(function(response) {
  	    	console.log(response);
  	    	$scope.findSelectedSupplier = response;
     	    });
  	    	
  	    	
  	    });
    	  
    	 $http({method:'GET',url:'/time/findPits'}).success(function(response) {
  	    	$scope.findPits = response;
  	    	console.log($scope.findPits);
  	    });
    	    
    	    $http({method:'GET',url:'/time/findUser'}).success(function(response) {
    	    	console.log(response);
    	    	$scope.findUser = response;
    	    	$scope.allUser = $scope.findUser; 
    	    });
    	   
    	    
    	    $http({method:'GET',url:'/time/getAllDefinePartData',params:{id:$rootScope.MainInstance}}).success(function(response) {
    			console.log(response);
    			$scope.defineParts = response;
    	    	console.log($scope.defineParts);
    	    	//$scope.defineParts.partsValue.push({});
    	    	if($scope.defineParts.partsValue.length == 0){
    	    		$scope.defineParts.partsValue.push({});
    	    		console.log("Enter.......");
    	    	}
    	    	$scope.totalEstimat = $scope.defineParts.totalEstimatedRevenue;
    		});
    	
    }


    var file = null;
    $scope.selectFile = function(files) {
    	file = files[0];
    	console.log(file);
    };
    $scope.saveAttachment = function(){
    	
    	console.log($rootScope.currentParentId);
    	$scope.editdata.projectId  = $rootScope.currentParentId;
		$scope.editdata.thisNodeId = $rootScope.MainInstance;
		console.log($scope.editdata);
    	$upload.upload({
            url: 'saveFile',
            data: $scope.editdata,
            file: file,
            method:'post'
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            console.log(data);
            $http({method:'GET',url:'/time/findAttachFile',params:{id:$rootScope.currentParentId,mainInstance:$rootScope.MainInstance}}).success(function(data) {
				console.log("Ok----");
				console.log(data);
				$scope.fileAttachData = data;
				
				angular.forEach($scope.fileAttachData.projectAttachment, function(obj, index){
					
					$scope.fileAttachData.projectAttachment[index].docDate = $filter('date')($scope.fileAttachData.projectAttachment[index].docDate, "dd-MM-yyyy");
					return;
				});
				angular.forEach($scope.fileAttachData.projectcomments, function(obj, index){
					
					$scope.fileAttachData.projectcomments[index].commetDate = $filter('date')($scope.fileAttachData.projectcomments[index].commetDate, "dd-MM-yyyy");
					return;
				});
            });
           
        });
    	
    
    }
    
    
    
    $scope.projAttri = [];
	//for(var i=0;i<12;i++) {
		//var j = {keyValue:'',q1:'',q2:'',q3:'',q4:''};
		//$scope.projAttri[i] = j;
	//}
    
    $scope.projAttri = [{keyValue:'Revival Plan of Old Customers',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Unique Customer - BOM %',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'New Discovery Claims',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'New Azerity Customers',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Own Solution Proposed',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'MBO Projects',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Cumulative NNR',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Cumulative Design Win',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Sourcing Projects',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Socket Replacement Projects',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'New Development Projects',q1:'',q2:'',q3:'',q4:''},
                		{keyValue:'Addition of new lines Projects',q1:'',q2:'',q3:'',q4:''}];
                	//}
                		console.log($scope.projAttri);
                		$scope.newArray = [];
                		$scope.newArray.push.apply($scope.newArray, $scope.projAttri);
	
	$scope.saveAttribut = {};
	$scope.showMsgSave = 0;
	$scope.saveAttributes = function(projectAtt){
		console.log($scope.newArray);
		console.log($scope.projAttri);
	
		if($scope.projAttri[0] != undefined){
			$scope.projAttri[0].keyValue = "Revival Plan of Old Customers";
		}
		if($scope.projAttri[1] != undefined){
			$scope.projAttri[1].keyValue = "Unique Customer - BOM %";
		}
		if($scope.projAttri[2] != undefined){
			$scope.projAttri[2].keyValue = "New Discovery Claims";
		}
		if($scope.projAttri[3] != undefined){
			$scope.projAttri[3].keyValue = "New Azerity Customers";
		}
		if($scope.projAttri[4] != undefined){
			$scope.projAttri[4].keyValue = "Own Solution Proposed";
		}
		if($scope.projAttri[5] != undefined){
			$scope.projAttri[5].keyValue = "MBO Projects";
		}
		if($scope.projAttri[6] != undefined){
			$scope.projAttri[6].keyValue = "Cumulative NNR";
		}
		if($scope.projAttri[7] != undefined){
			$scope.projAttri[7].keyValue = "Cumulative Design Win";
		}
		if($scope.projAttri[8] != undefined){
			$scope.projAttri[8].keyValue = "Sourcing Projects";
		}
		if($scope.projAttri[9] != undefined){
			$scope.projAttri[9].keyValue = "Socket Replacement Projects";
		}
		if($scope.projAttri[10] != undefined){
			$scope.projAttri[10].keyValue = "New Development Projects";
		}
		if($scope.projAttri[11] != undefined){
			$scope.projAttri[11].keyValue = "Addition of new lines Projects";
		}
		
		angular.forEach($scope.newArray, function(obj, index){
			angular.forEach($scope.projAttri, function(obj1, index1){
				console.log(obj.keyValue);
				console.log(obj1.keyValue);
				if(obj.keyValue == obj1.keyValue){
					console.log("-=-=-");
					if(obj1.q1 == true){
						obj.q1 = true;
					}
					if(obj1.q2 == true){
						obj.q2 = true;
					}
					if(obj1.q3 == true){
						obj.q3 = true;
					}
					if(obj1.q4 == true){
						obj.q4 = true;
					}
				}
			});
		});
		
		
		angular.forEach($scope.newArray, function(obj, index){
			if(obj.q1 == ""){
				obj.q1 = false;
			}
			if(obj.q2 == ""){
				obj.q2 = false;
			}
			if(obj.q3 == ""){
				obj.q3 = false;
			}
			if(obj.q4 == ""){
				obj.q4 = false;
			}
		});
		console.log($scope.newArray);
		$scope.saveAttribut.projectAtt = $scope.newArray;
		$scope.saveAttribut.currentParentId = $rootScope.currentParentId;
		$scope.saveAttribut.MainInstance = $rootScope.MainInstance;
		console.log($scope.saveAttribut);
		
		
		 $http({method:'POST',url:'/time/saveAttribues',data:$scope.saveAttribut}).success(function(response) {
			 $scope.showMsgSave = 1;
		 });
		
		
	}
	
	$scope.findattributes = function(){
		 $http({method:'GET',url:'/time/findattributes',params:{mainInstance:$rootScope.MainInstance}}).success(function(data) {
			 console.log("Hiiii..Bye");
			 console.log(data);
			 $scope.projAttri = data.projectAtt;
		 });
	}
   /* $scope.defineParts = function(){
    	 $http({method:'GET',url:'/time/findPits'}).success(function(response) {
 	    	$scope.findPits = response;
 	    	console.log($scope.findPits);
 	    });
    }*/
    
   $scope.downloadfile = function(id){
	   
	   $.fileDownload('/time/downloadStatusFile',
		{	   	
			   httpMethod : "POST",
			   data : {
				   attchId : id,
				   mainInstance : $rootScope.MainInstance,
				   currentParentId : $rootScope.currentParentId
			   }
		}).done(function(e, response)
				{
				}).fail(function(e, response)
				{
					// failure
				});
    
   }
   
    $scope.saveComment = function(comment){
    	
    	$scope.editdata.projectId  = $rootScope.currentParentId;
		$scope.editdata.thisNodeId = $rootScope.MainInstance;
		$scope.editdata.comment = comment;
    	
    	console.log($scope.editdata);
    	 $http({method:'POST',url:'/time/saveComment',data:$scope.editdata}).success(function(response) {
  			console.log("Ok"); 
  			console.log(response);
  			
  			 $http({method:'GET',url:'/time/findAttachFile',params:{id:$rootScope.currentParentId,mainInstance:$rootScope.MainInstance}}).success(function(data) {
 				console.log("Ok----");
 				console.log(data);
 				$scope.fileAttachData = data;
 				
 				angular.forEach($scope.fileAttachData.projectAttachment, function(obj, index){
					
					$scope.fileAttachData.projectAttachment[index].docDate = $filter('date')($scope.fileAttachData.projectAttachment[index].docDate, "dd-MM-yyyy");
					return;
				});
				angular.forEach($scope.fileAttachData.projectcomments, function(obj, index){
					
					$scope.fileAttachData.projectcomments[index].commetDate = $filter('date')($scope.fileAttachData.projectcomments[index].commetDate, "dd-MM-yyyy");
					return;
				});
             });
  			
  			});
    }
    
    	$scope.projectT = 0;
    	$scope.projectD = 0;
    	$scope.saveCreateProject = function(pro){
    		
    		console.log("HIHIHIIHIHIIH");
    		console.log(pro.string);
    	};
    	
    	$scope.editProjectInfo = function(){
    		console.log($scope.pro.projectTypes);
   		 $scope.projectsearch.parentId = $rootScope.currentParentId;
   		 $scope.projectsearch.projectId = $scope.projectid;
   		 $scope.projectsearch.projectTypes = $scope.pro.projectTypes;
   		 $scope.projectsearch.projectDescription = $scope.pro.projectDescription;
   	     $scope.projectsearch.projectColor = $scope.pro.projectColor;
   	     
   		 console.log($scope.projectsearch);

   		 if($scope.pro.projectTypes != null && $scope.pro.projectTypes != ""){
			 $scope.projectT = 0;
		 }else{
			 $scope.projectT = 1;
		 }
		 
		 if($scope.pro.projectDescription != null && $scope.pro.projectDescription != ""){
			 $scope.projectD = 0;
		 }else{
			 $scope.projectD = 1; 
		 }
   		 
		 if($scope.pro.projectTypes != null && $scope.pro.projectDescription != null && $scope.pro.projectTypes != "" && $scope.pro.projectDescription != ""){
   		 $http({method:'POST',url:'editProjectChild',data:$scope.projectsearch}).success(function(response) {
   			console.log("Ok"); 
   			console.log(response);
   			
   			angular.forEach($scope.myOptions.items,function(value,key) {
        		if(response == value.id){
        			value.projectTypes =  $scope.projectsearch.projectTypes;
        			value.projectDescription = $scope.projectsearch.projectDescription;
        		
      		}
        	});
   			
   			});
   		ngDialog.close();
    	}
   		
    	}
    	
    	$scope.task = function(taskC){
    		console.log(taskC);
    		$http({method:'GET',url:'/time/saveTask',params:{id:$rootScope.currentParentId,mainInstance:$rootScope.MainInstance,task : taskC}}).success(function(data) {
    			console.log(data);
	        	$scope.myOptions.items = [];
    			items = [];
    			
    			if(data.length>0) {
    				
            		$scope.projectid = data[0].projectId;
            		angular.forEach(data,function(value,key) {
            			items.push(new primitives.orgdiagram.ItemConfig({
            				id: value.id,
            				parent: value.parentId,
            				projectTypes: value.projectTypes,
            				projectDescription: value.projectDescription,
            				projectCompleted: value.completed,
            				status: value.status,
               				itemTitleColor: primitives.common.Colors.RoyalBlue
            			}));
            		});
            	} else {
            		items.push(new primitives.orgdiagram.ItemConfig({
        				id: 0,
        				parent: null,
        				projectTypes: "Root",
        				projectDescription: "",
        				itemTitleColor: primitives.common.Colors.RoyalBlue
        			}));
            	}
            	$scope.myOptions.items = items;
    			
    		});
    		
    		/*$scope.editdata.projectId  = $rootScope.currentParentId;
    		$scope.editdata.thisNodeId = $rootScope.MainInstance;
    		$scope.editdata.comment = comment;
        	
        	console.log($scope.editdata);
        	 $http({method:'POST',url:'/time/saveComment',data:$scope.editdata}).success(function(response) {*/
    		
    	}
    	
    $scope.history = function(){
    	console.log("history call");
    	console.log($rootScope.currentParentId);
    	console.log();
    	$http({method:'GET',url:'/time/getAllHistory',params:{mainInstance:$rootScope.MainInstance}}).success(function(response) {
    		
    		console.log(response);
    		$scope.historyRecord = response;
    	});
    }	
    	
        
    $scope.setCursorItem = function (item) {
        $scope.myOptions.cursorItem = item;
    };

    $scope.setHighlightItem = function (item) {
        $scope.myOptions.highlightItem = item;
    };

    $scope.deleteItem = function (index) {
        $scope.myOptions.items.splice(index, 1);
    };

    $scope.addItem = function (index, parent) {
    	console.log("index:"+index+",parent:"+parent);
        var id = $scope.index++;
        $scope.myOptions.items.splice(index, 0, new primitives.orgdiagram.ItemConfig({
            id: id,
            parent: parent,
            name: "New title " + id,
            description: "New description " + id,
           // image: "demo/images/photos/b.png"
        }));
    };

    $scope.onMyCursorChanged = function () {
        $scope.Message = "onMyCursorChanged";
    };

    $scope.onMyHighlightChanged = function () {
        $scope.Message = "onMyHighlightChanged";
    };

    function getContactTemplate() {
        var result = new primitives.orgdiagram.TemplateConfig();
        result.name = "contactTemplate";

        result.itemSize = new primitives.common.Size(150, 90);
        result.minimizedItemSize = new primitives.common.Size(2, 2);
        result.minimizedItemCornerRadius = 5;
        result.highlightPadding = new primitives.common.Thickness(2, 2, 2, 2);


        var itemTemplate = jQuery(
          '<div class="bp-item bp-corner-all bt-item-frame">'
            + '<div name="titleBackground" class="bp-item bp-corner-all bp-title-frame" style="background:{{itemTitleColor}};top: 2px; left: 2px; width: 216px; height: 20px;">'
                + '<div name="title" class="bp-item bp-title" style="top: 3px; left: 6px; width: 128px; height: 18px;">{{itemConfig.projectTypes}}</div>'
            + '</div>'
            + '<div class="bp-item bp-photo-frame" style="display:none;top: 26px; left: 2px; width: 50px; height: 60px;">'
                + '<img name="photo" src="{{itemConfig.image}}" style="display:none;height: 60px; width:50px;" />'
            + '</div>'
            + '<div class="bp-item" style="top: 44px; left: 56px; width: 162px; height: 18px; font-size: 12px;">{{itemConfig.organizationType}}</div>'
            + '<div name="description" class="bp-item" style="top: 35px; left: 0px; width: 100%;height:36px; font-size: 10px;text-align: center;">{{itemConfig.projectDescription}}</div>'
            + '<div style="text-align: center;"><progressbar id="progressBar" class="progress-striped active" style="height: 20px;margin-top: 71px;" animate="true" max="100" value="itemConfig.projectCompleted" type="{{itemConfig.status}}"><i>{{itemConfig.projectCompleted}}%</i></progressbar></div>'
        + '</div>'
        ).css({
            width: result.itemSize.width + "px",
            height: result.itemSize.height + "px"
        }).addClass("bp-item bp-corner-all bt-item-frame");
        result.itemTemplate = itemTemplate.wrap('<div>').parent().html();

        return result;
    };
    function getSubItemsForParent(items, parentItem) {
        var children = {},
            itemsById = {},
            index, len, item;
        for (index = 0, len = items.length; index < len; index += 1) {
            var item = items[index];
            if (children[item.parent] == null) {
                children[item.parent] = [];
            }
            children[item.parent].push(item);
        }
        var newChildren = children[parentItem.id];
        var result = {};
        if (newChildren != null) {
            while (newChildren.length > 0) {
                var tempChildren = [];
                for (var index = 0; index < newChildren.length; index++) {
                    var item = newChildren[index];
                    result[item.id] = item;
                    if (children[item.id] != null) {
                        tempChildren = tempChildren.concat(children[item.id]);
                    }
                }
                newChildren = tempChildren;
            }
        }
        return result;
    };

	   
 
	
});