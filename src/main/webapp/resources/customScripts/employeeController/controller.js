app.controller("EmployeeController",function($scope,$http,ngDialog,$upload) {
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
    				employeeName: value.employeeName,
    				designation: value.designation,
    				image: "employeeProfile/"+value.id,
       				itemTitleColor: primitives.common.Colors.RoyalBlue
    			}));
    		});
    	} else {
    		items.push(new primitives.orgdiagram.ItemConfig({
				id: 0,
				parent: null,
				employeeName: "Root",
				designation: "",
				itemTitleColor: primitives.common.Colors.RoyalBlue
			}));
    	}
    	options.items = items;
		options.cursorItem = 0;
		options.highlightItem = 0;
		options.hasSelectorCheckbox = primitives.common.Enabled.True;
		options.templates = [getContactTemplate()];
		options.defaultTemplateName = "contactTemplate";
		
	
		$scope.myOptions = options;
		
		console.log("--------");
		console.log($scope.myOptions);
		
    };
    
    $scope.org = {};
    
    $scope.overWrite = 0;


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
                + '<div name="title" class="bp-item bp-title" style="top: 3px; left: 6px; width: 144px; height: 18px;">{{itemConfig.employeeName}}</div>'
            + '</div>'
            + '<div class="bp-item bp-photo-frame" style="top: 27px !important; left: 2px; width: 50px; height: 46px;">'
                + '<img name="photo" src="{{itemConfig.image}}" style="height: 60px; width:50px;" />'
            + '</div>'
            + '<div class="bp-item" style="top: 44px; left: 56px; width: 162px; height: 18px; font-size: 12px;">{{itemConfig.organizationType}}</div>'
            + '<div name="description" class="bp-item" style="top: 40px; left: 62px; width: 100%;height:36px; font-size: 16px;">{{itemConfig.designation}}</div>'
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