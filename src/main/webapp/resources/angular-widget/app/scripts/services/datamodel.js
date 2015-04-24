'use strict';

angular.module('app.service', []);

angular.module('app.service')

  .factory('RestFunnelDataModel', function (WidgetDataModel, $http) {
	    function RestFunnelDataModel() {
	    }

	    RestFunnelDataModel.prototype = Object.create(WidgetDataModel.prototype);

	    RestFunnelDataModel.prototype.init = function () {
	        WidgetDataModel.prototype.init.call(this);

	        this.load();
	    };
	    
	    RestFunnelDataModel.prototype.load = function () {
	        $http.get(contextPath + '/salesFunnelForWidget', {
	          params: {
	        	  projectType: this.dataModelOptions.projectType
	          }
	        }).success(function (data) {
	          this.updateScope(data);
	        }.bind(this));
	      };

	    RestFunnelDataModel.prototype.destroy = function () {
	      WidgetDataModel.prototype.destroy.call(this);
	      
	    };

	    return RestFunnelDataModel;
	  })

  .factory('ProjectDataModel', function (settings, WidgetDataModel, $http) {
    function ProjectDataModel() {
    }

    ProjectDataModel.prototype = Object.create(WidgetDataModel.prototype);

    ProjectDataModel.prototype.init = function () {
    	WidgetDataModel.prototype.init.call(this);
    	
    	this.load();
    };
    
    ProjectDataModel.prototype.load = function () {
    	$http.get(contextPath+'/projectsForWidget', {
            params: {
              id:this.dataModelOptions.userId
            }
          }).success(function (data) {
        	  console.log(data);
            this.updateScope(data);
          }.bind(this));
    };

    ProjectDataModel.prototype.destroy = function () {
        WidgetDataModel.prototype.destroy.call(this);
      };
    
    return ProjectDataModel;
  })
  .factory('TaskDataModel', function (settings, WidgetDataModel, $http) {
    function TaskDataModel() {
    }

    TaskDataModel.prototype = Object.create(WidgetDataModel.prototype);

    TaskDataModel.prototype.init = function () {
    	WidgetDataModel.prototype.init.call(this);
    	
    	this.load();
    };
    
    TaskDataModel.prototype.load = function () {
    	$http.get(contextPath+'/tasksForWidget', {
            params: {
              id:this.dataModelOptions.userId
            }
          }).success(function (data) {
        	  console.log(data);
            this.updateScope(data);
          }.bind(this));
    };

    TaskDataModel.prototype.destroy = function () {
        WidgetDataModel.prototype.destroy.call(this);
      };
    
    return TaskDataModel;
  });