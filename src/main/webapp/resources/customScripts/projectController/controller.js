app.controller("ProjectHierarchyController",function($scope,$http,ngDialog,$upload) {
	$scope.index = 10;
    $scope.Message = "";
    maximumId = 2;
    $scope.selectRootNode= null;
    $scope.projectsearch= {
    		projectValue:[],
    		parentId:'',
    		projectId:'',
    		projectTypes:'',
    		projectDescription:'',
    		projectColor:'',
    		level:''	
    	};
    
    	
    
    var buttons = [];
    buttons.push(new primitives.orgdiagram.ButtonConfig("delete", "ui-icon-close", "Delete"));
    buttons.push(new primitives.orgdiagram.ButtonConfig("add", "ui-icon-person", "Add"));
	buttons.push(new primitives.orgdiagram.ButtonConfig("edit", "ui-icon-gear", "Edit"));

	$scope.myOptions = {
    		cursorItem : 0,
    		highlightItem : 0,
    		hasSelectorCheckbox : primitives.common.Enabled.True,
    		templates : [getContactTemplate()],
    		defaultTemplateName : "contactTemplate",
    		buttons:buttons,
    };
	
	$scope.myOptions.onButtonClick = function (e, data) {
		switch (data.name) {
        	case "delete":
        		if (data.parentItem == null) {
        			alert("You are trying to delete root item !");
        		}
        		else {
        			if(confirm("Are you sure you want to remove "+data.context.projectTypes+"?")) {
        				
        				console.log(data);
        				console.log(data.context.id);
        				console.log($scope.selectRootNode);
        				
        				$http({method:'GET',url:'deleteProjectChild',params:{id:data.context.id}}).success(function(response) {
        					if(response) {
        						
        						$http({method:'GET',url:'selectProjectType',params:{id:$scope.selectRootNode}}).success(function(data) {
                		        	console.log(data);
                		        	console.log($scope.myOptions.items);
                		        	$scope.myOptions.items = [];
                		        	console.log($scope.myOptions.items);
                		        	
                		        	console.log(items);
                		        	items = [];
                		        	
                		        	if(data.length>0) {
                		        		$scope.projectid = $scope.selectRootNode;
                		        		angular.forEach(data,function(value,key) {
                		        			items.push(new primitives.orgdiagram.ItemConfig({
                		        				id: value.id,
                		        				parent: value.parentId,
                		        				projectTypes: value.projectTypes,
                		        				projectDescription: value.projectDescription,
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
                		        	
                		    		console.log($scope.myOptions);	
                		    	});
        				
        					}
        				});
        				
        			}
        		}
        		break;
        	case "add":
        		$scope.projectsearch= {
            		projectValue:[],
            		parentId:'',
            		projectId:'',
            		projectTypes:'',
            		projectDescription:'',
            		projectColor:'',
                	level:''	
            	};
        		//$scope.level = 0;
        		$scope.projectsearch.projectValue.push({});
        		console.log(data);
        		$scope.currentParentId = data.context.id;
        		$http({method:'GET',url:'editProjectTypeInfo',params:{id:$scope.currentParentId}}).success(function(data) {
        			$scope.level = data[0].level;
        			console.log($scope.level);
            		console.log($scope.currentParentId);
            		$scope.pro.projectColor = "#e78f08";
            		ngDialog.open({
            			template:'addProjectTypeValue',
            			//controller: 'ProjectHierarchyController',
            			scope:$scope,
            			closeByDocument:false
            			
            		});
        		});
        		
        		break;
        	case "edit":
        		
        		$scope.currentParentId = data.context.id;
        		console.log($scope.currentParentId);
        		$http({method:'GET',url:'editProjectTypeInfo',params:{id:$scope.currentParentId}}).success(function(data) {
        			console.log("Ok");
        			console.log(data);
        			$scope.projectsearch = data[0];
        			console.log($scope.projectsearch);
        			//if($scope.projectsearch.projectValue.length == 0){
        				//$scope.projectsearch.projectValue.push({});
        			//}
        			$scope.level =  $scope.projectsearch.level;
        			$scope.pro.projectTypes =  $scope.projectsearch.projectTypes;
        			$scope.pro.projectDescription = $scope.projectsearch.projectDescription;
        			$scope.pro.projectColor = $scope.projectsearch.projectColor;
        			ngDialog.open({
            			template:'editProjectTypeValue',
            			scope:$scope,
            			closeByDocument:false
            		});
        		});
        		
        		break;	
		}
	};
   
	
    
    var items = [
    ];
    
    $scope.pro = {};
    $http({method:'GET',url:'AllProjectType'}).success(function(response) {
    	console.log(response);
    	$scope.projectType = response;
    });
    
    $scope.overWrite = 0;
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
 			 if(response == ""){
 				$scope.overWrite = 1;
 			 }else{
 				$scope.overWrite = 0;
 				ngDialog.close();
 			 }
 			
 			});
    	
    }
   
    $scope.onProjectTypeChange = function(id){
    	console.log(id);
    	
    }
    
    $scope.addHierarchyProjectType = function(){
    	$scope.pro = {};
    	ngDialog.open({
			template:'addProjectType',
			scope:$scope,
			closeByDocument:false
		});
    }
    
    $scope.viewHierarchy = function(id){
    	console.log(id);
    	$scope.selectRootNode = id;
    	console.log($scope.selectRootNode);
    	items = [];
    	$http({method:'GET',url:'selectProjectType',params:{id:id}}).success(function(data) {
        	console.log(data);
        	if(data.length>0) {
        		$scope.projectid = data[0].projectId;
        		angular.forEach(data,function(value,key) {
        			items.push(new primitives.orgdiagram.ItemConfig({
        				id: value.id,
        				parent: value.parentId,
        				projectTypes: value.projectTypes,
        				projectDescription: value.projectDescription,
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
        	
    		console.log($scope.myOptions);	
    	});
    	
    }

    	/*$scope.projectsearch.projectValue.push({});
    	$scope.newprojectValue = function($event){
    		
    		$scope.projectsearch.projectValue.push( {  } );
    		$event.preventDefault();
    	};*/

    	$scope.projectT = 0;
    	$scope.projectD = 0;
    	$scope.saveProjectInfo = function(){
    		
    		console.log($scope.pro.projectDescription);
    		console.log($scope.pro.projectColor);
    		 $scope.projectsearch.parentId = $scope.currentParentId;
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
    			 
    		 $http({method:'POST',url:'saveProjectChild',data:$scope.projectsearch}).success(function(response) {
    			console.log("Ok"); 
    			console.log(response);
    			console.log($scope.myOptions);
    			$scope.myOptions.items.push({id:response,parent: $scope.projectsearch.parentId,projectTypes:$scope.projectsearch.projectTypes,projectDescription:$scope.projectsearch.projectDescription});
    			//$scope.myOptions.items = items;
    			ngDialog.close();
    			});
    	      }
    	};
    	
    	$scope.editProjectInfo = function(){
    		console.log($scope.pro.projectTypes);
   		 $scope.projectsearch.parentId = $scope.currentParentId;
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

        result.itemSize = new primitives.common.Size(150, 80);
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
            + '<div name="description" class="bp-item" style="top: 31px; left: 0px; width: 100%;height:36px; font-size: 10px;text-align: center;">{{itemConfig.projectDescription}}</div>'
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