app.controller("RoleController",function($scope,$http,ngDialog,$upload) {
	$scope.index = 10;
    $scope.Message = "";
    maximumId = 2;
    var options = {};

    var items = [
    ];

    $scope.loadData = function(data) {
    	if(data.length>0) {
    		angular.forEach(data,function(value,key) {
    			items.push(new primitives.orgdiagram.ItemConfig({
    				id: value.id,
    				parent: value.parent,
    				roleName: value.roleName,
    				roleDescription: value.roleDescription,
       				itemTitleColor: primitives.common.Colors.RoyalBlue
    			}));
    		});
    	} else {
    		items.push(new primitives.orgdiagram.ItemConfig({
				id: 0,
				parent: null,
				roleName: "Root",
				roleDescription: "Pune",
				itemTitleColor: primitives.common.Colors.RoyalBlue
			}));
    	}
    	options.items = items;
		options.cursorItem = 0;
		options.highlightItem = 0;
		options.hasSelectorCheckbox = primitives.common.Enabled.True;
		options.templates = [getContactTemplate()];
		options.defaultTemplateName = "contactTemplate";
		var buttons = [];
		buttons.push(new primitives.orgdiagram.ButtonConfig("delete", "ui-icon-close", "Delete"));
		buttons.push(new primitives.orgdiagram.ButtonConfig("add", "ui-icon-person", "Add"));
		buttons.push(new primitives.orgdiagram.ButtonConfig("edit", "ui-icon-gear", "Edit"));
		options.buttons = buttons;
		
		options.onButtonClick = function (e, data) {
			switch (data.name) {
			
            	case "delete":
            		if (data.parentItem == null) {
            			alert("You are trying to delete root item !");
            		}
            		else {
            			if(confirm("Are you sure you want to remove "+data.context.roleName+"?")) {
            				$http({method:'GET',url:'deleteRoleChild',params:{id:data.context.id}}).success(function(response) {
            					if(response) {
            						
            						$http({method:'GET',url:'loadAllRoleData',params:{id:data.context.id}}).success(function(response) {
            							console.log("--------------------");
            							console.log(response);
            							console.log($scope.myOptions.items);
            							$scope.myOptions.items = [];
            							console.log($scope.myOptions.items);
            							if(response.length>0) {
            					    		angular.forEach(response,function(value,key) {
            					    			$scope.myOptions.items.push(new primitives.orgdiagram.ItemConfig({
            					    				id: value.id,
            					    				parent: value.parent,
            					    				roleName: value.roleName,
            					    				roleDescription: value.roleDescription,
            					    				itemTitleColor: primitives.common.Colors.RoyalBlue
            					    			}));
            					    		});
            					    	} else {
            					    		$scope.myOptions.items.push(new primitives.orgdiagram.ItemConfig({
            									id: 0,
            									parent: null,
            									roleName: "Root",
            									roleDescription: "Pune",
            									itemTitleColor: primitives.common.Colors.RoyalBlue
            								}));
            					    	}
            						});
            						console.log($scope.myOptions.items);
            					}
            				});
            			}
            		}
            		break;
            	case "add":
            		$scope.currentParentId = data.context.id;
            		console.log(data);
            		ngDialog.open({
            			template:'addRoleOrganization',
            			scope:$scope,
            			closeByDocument:false
            		});
            		break;
            	case "edit":
            
            		console.log(data);
            		$scope.org.roleName = data.context.roleName;
            		$scope.org.roleDescription = data.context.roleDescription;
            		$scope.currentParentId = data.context.id;
            		ngDialog.open({
            			template:'editRoleOrganization',
            			scope:$scope,
            			closeByDocument:false
            		});
            		break;	
			}
		};
		$scope.myOptions = options;
		
		console.log("--------");
		console.log($scope.myOptions);
		
    };
    
    $scope.org = {};
    var file = null;
    $scope.selectFile = function(files) {
    	file = files[0];
    	console.log(file);
    };
    $scope.overWrite = 0;
  $scope.saveRoleChild = function() {
	  $scope.org.parent = $scope.currentParentId;
	 // $scope.org.roleName = $scope.org.roleName;
	 // $scope.org.roleDescriptio = $scope.org.roleDescriptio;
	  console.log($scope.org);
	  
	  $http({method:'POST',url:'saveRoleChild',data:$scope.org}).success(function(response) {
		 
		  console.log(response);
		  if(response != null && response != ""){
			  $scope.overWrite = 0;
			  console.log($scope.overWrite);
		    $scope.org.id = response;
		    
			$scope.myOptions.items.push({id:$scope.org.id,parent:$scope.org.parent,roleName:$scope.org.roleName,roleDescription:$scope.org.roleDescription});
			
			ngDialog.close();
		  }else{
			  $scope.overWrite = 1;
			  console.log($scope.overWrite);
		  }
		});
    	
  
};

	$scope.editRoleChild = function() {
		
		$scope.org.parent = $scope.currentParentId;
		console.log("-----***---------");
		console.log($scope.org);
		$http({method:'POST',url:'editRoleChild',data:$scope.org}).success(function(response) {
			console.log(response);
			console.log($scope.org.roleName);
			console.log($scope.myOptions.items);
        	$scope.org.id = response;
        	angular.forEach($scope.myOptions.items,function(value,key) {
        		if(response == value.id){
        			value.roleName = $scope.org.roleName;
        			value.roleDescription = $scope.org.roleDescription;
        		
      		}
        	});
			
		});
    	ngDialog.close();
		
	};
        
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
            image: "demo/images/photos/b.png"
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
        result.minimizedItemSize = new primitives.common.Size(5, 5);
        result.minimizedItemCornerRadius = 5;
        result.highlightPadding = new primitives.common.Thickness(2, 2, 2, 2);


        var itemTemplate = jQuery(
          '<div class="bp-item bp-corner-all bt-item-frame">'
            + '<div name="titleBackground" class="bp-item bp-corner-all bp-title-frame" style="background:{{itemTitleColor}};top: 2px; left: 2px; width: 216px; height: 20px;">'
                + '<div name="title" class="bp-item bp-title" style="top: 3px; left: 6px; width: 144px; height: 18px;">{{itemConfig.roleName}}</div>'
            + '</div>'
            + '<div class="bp-item bp-photo-frame" style="display:none;top: 26px; left: 2px; width: 50px; height: 60px;">'
                + '<img name="photo" src="{{itemConfig.image}}" style="display:none;height: 60px; width:50px;" />'
            + '</div>'
            + '<div class="bp-item" style="top: 44px; left: 56px; width: 162px; height: 18px; font-size: 12px;">{{itemConfig.organizationType}}</div>'
            + '<div name="description" class="bp-item" style="top: 62px; left: 0px; width: 100%;height:36px; font-size: 16px;text-align: center;">{{itemConfig.roleDescription}}</div>'
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