app.controller("createProjectController",function($scope,$http,$rootScope,ngDialog,$upload,$timeout,$filter,$compile) {
	
	$scope.progressValue = 150;
	
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
    
    var buttons = [];
    
     
    //buttons.push(new primitives.orgdiagram.ButtonConfig("add", "ui-icon-person", "Add"));
	buttons.push(new primitives.orgdiagram.ButtonConfig("edit", "ui-icon-gear", "Edit"));
	buttons.push(new primitives.orgdiagram.ButtonConfig("editInfo", "ui-icon-close", "EditInfo"));
	

	$scope.myOptions = {
    		cursorItem : 0,
    		highlightItem : 0,
    		hasSelectorCheckbox : primitives.common.Enabled.True,
    		templates : [getContactTemplate()],
    		defaultTemplateName : "contactTemplate",
    		buttons:buttons,
    	
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
        		
    			$http({method:'GET',url:'/time/selectedUser',params:{mainInstance:$rootScope.MainInstance,projectId:data.context.id}}).success(function(response) {
    				console.log(response);
	    	    	var selectedUsers = [];
        	    	angular.forEach($scope.allUser,function(user,key) {
	        	    	angular.forEach(response,function(value,key1) {
	        	    		if(user.id == value){
	        	    			selectedUsers.push(user);
	        	    		}
	        	    	});
        	    	});
        	    	$scope.findSelectedUser = response;
        	    	$scope.findUser = selectedUsers;
        	    	if(data.context.id == 1)
        	    		$scope.findUser = $scope.allUser;
	    	    });
    			
    			 $http({method:'GET',url:'/time/selectedSupplier',params:{mainInstance:$rootScope.MainInstance}}).success(function(response) {
    		  	    	console.log(response);
    		  	    	$scope.findSelectedSupplier = response;
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
    				projectDescription: "Pune",
    				itemTitleColor: primitives.common.Colors.RoyalBlue
    			}));
        	}
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
    	  
    	 
    	    
    	    $http({method:'GET',url:'/time/findUser'}).success(function(response) {
    	    	console.log(response);
    	    	$scope.findUser = response;
    	    	$scope.allUser = $scope.findUser; 
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
        				projectDescription: "Pune",
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
            + '<div name="description" class="bp-item" style="top: 40px; left: 0px; width: 100%;height:36px; font-size: 16px;text-align: center;">{{itemConfig.projectDescription}}</div>'
            + '<div><progressbar id="progressBar" class="progress-striped active" style="height: 20px;margin-top: 71px;" animate="true" max="100" value="itemConfig.projectCompleted" type="{{itemConfig.status}}"><i>{{itemConfig.projectCompleted}}</i></progressbar></div>'
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