var app = angular.module('timeSheetApp', ['ngDialog','angularCharts','angularFileUpload','ui.select2','ngMask'])
.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }
})
.directive('ngTodayScheduler',function(){
	return {
		scope: {
			data: '='
	    },
	    controller: function($scope) {
        },
		link: function(scope, element, attrs,$scope) {
			scope.$watch('data', function(newValue, oldValue) {
				element.borderify(newValue);
				var hr = new Date().getHours();
				if(hr>1) {
					hr= hr-1;
				}
				
				$("#scheduler-wrapper").scrollTop(hr*(scope.data.unitValueInMin/scope.data.gradationBetweenPerUnit)*scope.data.gradationBetweenPerUnitpx);
		      }, true);
			element.borderify(scope.data);
			var hr = new Date().getHours();
			if(hr>1) {
				hr= hr-1;
			}
		
			$("#scheduler-wrapper").scrollTop(hr*(scope.data.unitValueInMin/scope.data.gradationBetweenPerUnit)*scope.data.gradationBetweenPerUnitpx);
		}
	}
}).factory('MyHttpInterceptor', function ($q) {
    return {
        request: function (config) {
                    $('#loading').show();
                    return config || $q.when(config);           
        },
   
        requestError: function (rejection) {
                    $('#loading').hide();
            return $q.reject(rejection);
        },
        
        // On response success
        response: function (response) {
                    $('#loading').hide();
            return response || $q.when(response);
        },
        
        // On response failture
        responseError: function (rejection) {
                    $('#loading').hide();
            return $q.reject(rejection);
        }
      };
})
.config(function ($httpProvider) {
     $httpProvider.interceptors.push('MyHttpInterceptor');  
});