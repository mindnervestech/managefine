app.controller("OrgHierarchyController",function($scope,$http,ngDialog,$upload,$window) {
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
    				organizationName: value.organizationName,
    				organizationLocation: value.organizationLocation,
    				organizationType: value.organizationType,
    				image: "orgProfile/"+value.id,
    				itemTitleColor: primitives.common.Colors.RoyalBlue
    			}));
    		});
    	} else {
    		items.push(new primitives.orgdiagram.ItemConfig({
				id: 0,
				parent: null,
				organizationName: "Root",
				organizationLocation: "",
				organizationType: "User",
				image: "orgProfile/",
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
		buttons.push(new primitives.orgdiagram.ButtonConfig("employee", "ui-icon-gear", "Employee"));
		options.buttons = buttons;
		options.onButtonClick = function (e, data) {
			switch (data.name) {
            	case "delete":
            		if (data.parentItem == null) {
            			alert("You are trying to delete root item!");
            		}
            		else {
            			if(confirm("Are you sure you want to remove "+data.context.organizationName+"?")) {
            				$http({method:'GET',url:'deleteOrgChild',params:{id:data.context.id}}).success(function(response) {
            					if(response) {
            						/*angular.forEach($scope.myOptions.items,function(value,key) {
            							if(value.id == data.context.id) { //|| value.parent == data.context.id
            								$scope.myOptions.items.splice(key,1);
            							}
            						});*/
            						
            						$http({method:'GET',url:'loadAllData',params:{id:data.context.id}}).success(function(response) {
            							console.log("--------------------");
            							console.log(response);
            							console.log($scope.myOptions.items);
            							$scope.myOptions.items = [];
            							if(response.length>0) {
            					    		angular.forEach(response,function(value,key) {
            					    			$scope.myOptions.items.push(new primitives.orgdiagram.ItemConfig({
            					    				id: value.id,
            					    				parent: value.parent,
            					    				organizationName: value.organizationName,
            					    				organizationLocation: value.organizationLocation,
            					    				organizationType: value.organizationType,
            					    				image: "orgProfile/"+value.id,
            					    				itemTitleColor: primitives.common.Colors.RoyalBlue
            					    			}));
            					    		});
            					    	} else {
            					    		$scope.myOptions.items.push(new primitives.orgdiagram.ItemConfig({
            									id: 0,
            									parent: null,
            									organizationName: "Root",
            									organizationLocation: "Pune",
            									organizationType: "User",
            									image: "orgProfile/",
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
            		$scope.org = {};
            		$scope.overWrite = 0;
            		$scope.currentParentId = data.context.id;
            		ngDialog.open({
            			template:'addOrganization',
            			scope:$scope,
            			closeByDocument:false
            		});
            		break;
            	case "edit":
            		$scope.overWrite = 0;
            		console.log(data);
            		$scope.currentParentId = data.context.id;
            		$scope.org.organizationName = data.context.organizationName;
            		$scope.org.organizationType = data.context.organizationType;
            		$scope.org.organizationLocation = data.context.organizationLocation;
            		ngDialog.open({
            			template:'editOrganization',
            			scope:$scope,
            			closeByDocument:false
            		});
            		break;	
            	case "employee":
            			$scope.currentParentId = data.context.id;
            			
            			$window.location.href = "employeeHierarchy?id="+$scope.currentParentId;
            			
            		break;	
			}
		};
		$scope.myOptions = options;
    };
    
    $scope.org = {};
    var file = null;
    $scope.selectFile = function(files) {
    	file = files[0];
    	console.log(file);
    };
    
    $scope.overWrite = 0;
    $scope.saveOrgChild = function() {
    	
    	$scope.org.parent = $scope.currentParentId;
    	
    	console.log($scope.org);
    	
    	$http({method:'POST',url:'saveOrgChild',data:$scope.org}).success(function(data) {
			console.log(data);
		    if(data != null && data != ""){
            	$scope.overWrite = 0;
            	
            	 if($scope.org.parent == 0){
              		  items = [];
              		  $scope.myOptions.items = [];
              		 $scope.org.parent = null;
              	  	}
            	
            	$scope.org.id = data;
            	console.log($scope.org.parent);
        	$scope.myOptions.items.push(new primitives.orgdiagram.ItemConfig({
                id: data,
                parent: $scope.org.parent,
                organizationName: $scope.org.organizationName,
                organizationLocation: $scope.org.organizationLocation,
                image: "orgProfile/"+data,
                organizationType:$scope.org.organizationType
                
            }));
        	console.log($scope.myOptions.items);
        	ngDialog.close();
            }else{
            	$scope.overWrite = 1;
            }
			
    	});	
    
    };

    $scope.orgEdit = [];
    $scope.editOrgChild = function() {
    	//console.log($scope.org);
    	
    	$scope.org.parent = $scope.currentParentId;
    	
    	
    	
    	if($scope.org.parent == 0){
    		$http({method:'POST',url:'saveOrgChild',data:$scope.org}).success(function(data) {
    			console.log(data);
    		    if(data != null && data != ""){
                	$scope.overWrite = 0;
                	
                	 if($scope.org.parent == 0){
                  		  items = [];
                  		  $scope.myOptions.items = [];
                  		  $scope.org.parent = null;
                  	  }
                	
                	$scope.org.id = data;
            	$scope.myOptions.items.push(new primitives.orgdiagram.ItemConfig({
                    id: data,
                    parent: $scope.org.parent,
                    organizationName: $scope.org.organizationName,
                    organizationLocation: $scope.org.organizationLocation,
                    image: "orgProfile/"+data,
                    organizationType:$scope.org.organizationType
                    
                }));
            	console.log($scope.myOptions.items);
            	ngDialog.close();
                }else{
                	$scope.overWrite = 1;
                }
    			
        	});	
    	}else{
    	console.log(file);
    	console.log($scope.org);
    	if(file == null){

    		$scope.org.id = $scope.org.parent;
    		
    		console.log($scope.org);  
    		$http({method:'POST',url:'editOrgNotImgChild',data:$scope.org}).success(function(response) {
    			console.log(response);
    			
    			 if(response != null && response != ""){
    	            	$scope.overWrite = 0;
    	            	
    	            	 if($scope.org.parent == 0){
    	              		  items = [];
    	              		  $scope.myOptions.items = [];
    	              		
    	              	  	}
    			 }	 
    			console.log($scope.myOptions.items);

    			angular.forEach($scope.myOptions.items,function(value,key) {
            		if(response == value.id){
            			value.organizationName = $scope.org.organizationName;
            			value.organizationLocation = $scope.org.organizationLocation;
            			value.organizationType = $scope.org.organizationType;
          		   }
            	});
    			 
    			
    		});
    		ngDialog.close();
    	}else{
    	$upload.upload({
            url: 'editOrgChild',
            data: $scope.org,
            file: file,
            method:'post'
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            console.log(data);
            
           /* if(data != null && data != ""){
				  $scope.overWrite = 0;*/
        	$scope.org.id = data;
        	console.log($scope.myOptions.items);
        	$scope.org.id = data;
        	console.log($scope.org);
        	angular.forEach($scope.myOptions.items,function(value,key) {
        		if(data == value.id){
        			value.organizationName = $scope.org.organizationName;
        			value.organizationLocation = $scope.org.organizationLocation;
        			//value.image = "orgProfile/"+data;
        			value.image = "orgProfile/"+data+"?q="+new Date();
        			value.organizationType = $scope.org.organizationType;
        		
      		}
        	});
        	
        	
        	ngDialog.close();
         /*   }else{
            	 $scope.overWrite = 1;
            }*/
        });
    	}
      }
    	
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

        result.itemSize = new primitives.common.Size(155, 80);
        result.minimizedItemSize = new primitives.common.Size(5, 5);
        result.minimizedItemCornerRadius = 5;
        result.highlightPadding = new primitives.common.Thickness(2, 2, 2, 2);


        var itemTemplate = jQuery(
          '<div class="bp-item bp-corner-all bt-item-frame">'
            + '<div name="titleBackground" class="bp-item bp-corner-all bp-title-frame" style="background:{{itemTitleColor}};top: 2px; left: 2px; width: 216px; height: 20px;">'
                + '<div name="title" class="bp-item bp-title" style="top: 3px; left: 0px; width: 155px; height: 18px;">{{itemConfig.organizationName}}</div>'
            + '</div>'
            + '<div class="bp-item" style="top: 27px; width: 162px; height: 18px; font-size: 12px;text-align: center;">{{itemConfig.organizationType}}</div>'
            + '<div name="description" class="bp-item" style="top: 51px; width: 162px; height: 36px; font-size: 10px;text-align: center;">{{itemConfig.organizationLocation}}</div>'
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




/*

var itemTemplate = jQuery(
        '<div class="bp-item bp-corner-all bt-item-frame">'
          + '<div name="titleBackground" class="bp-item bp-corner-all bp-title-frame" style="background:{{itemTitleColor}};top: 2px; left: 2px; width: 216px; height: 20px;">'
              + '<div name="title" class="bp-item bp-title" style="top: 3px; left: 6px; width: 208px; height: 18px;">{{itemConfig.organizationName}}</div>'
          + '</div>'
          + '<div class="bp-item bp-photo-frame" style="top: 26px; left: 2px; width: 50px; height: 60px;">'
              + '<img name="photo" src="{{itemConfig.image}}" style="height: 60px; width:50px;" />'
          + '</div>'
          + '<div class="bp-item" style="top: 44px; left: 56px; width: 162px; height: 18px; font-size: 12px;">{{itemConfig.organizationType}}</div>'
          + '<div name="description" class="bp-item" style="top: 62px; left: 56px; width: 162px; height: 36px; font-size: 10px;">{{itemConfig.organizationLocation}}</div>'
      + '</div>'
      ).css({*/