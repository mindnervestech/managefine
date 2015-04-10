app.controller("createProjectController",function($scope,$http,ngDialog,$upload) {
	
	$scope.index = 10;
    $scope.Message = "";
    maximumId = 2;
    $scope.MainInstance = 0;
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
    
   
/*	$scope.addProjectName = function(projectInfo){
		 console.log("jjjjj");
		 console.log(projectInfo);
		$http({method:'POST',url:'saveprojectTypeandName',data:projectInfo}).success(function(response) {
 			console.log("Ok"); 
 			console.log(response);
 			 
 			
 			});
	}
    */
    
    //buttons.push(new primitives.orgdiagram.ButtonConfig("delete", "ui-icon-close", "Delete"));
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
        		
        		break;
        	case "add":
        		console.log("=-=-=-=-=");
        		console.log(data.context.id);
        		console.log($scope.MainInstance);
        		console.log("=-=-=-=-=");
        		
        		$http({method:'GET',url:'AddJspPage',params:{id:data.context.id,mainInstance:$scope.MainInstance}}).success(function(data) {
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
        		
        		console.log($scope.MainInstance);
        		console.log(data.context.id);
        		
        		$http({method:'GET',url:'EditJspPage',params:{id:data.context.id,mainInstance:$scope.MainInstance}}).success(function(data) {
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
		}
	};
   
	    
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
    
    /*ngDialog.open({
		template:$scope.projectType,
		plain:true,
		//controller: 'ProjectHierarchyController',
		scope:$scope,
		closeByDocument:false,
		className: 'ngdialog-theme-plain'
	});*/
    
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
   
    $scope.viewHierarchy = function(id,rootId){
    	console.log(id);
    	console.log(rootId);
    	 $scope.MainInstance = rootId;
    	$scope.selectRootNode = id;
    	console.log($scope.selectRootNode);
    	items = [];
    	$http({method:'GET',url:'selectProjectType',params:{id:id}}).success(function(data) {
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
    	
    }

    	$scope.projectT = 0;
    	$scope.projectD = 0;
    	$scope.saveCreateProject = function(pro){
    		
    		console.log("HIHIHIIHIHIIH");
    		console.log(pro.string);
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
            + '<div name="description" class="bp-item" style="top: 40px; left: 0px; width: 100%;height:36px; font-size: 16px;text-align: center;">{{itemConfig.projectDescription}}</div>'
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