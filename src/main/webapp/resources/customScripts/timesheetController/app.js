var app = angular.module('timeSheetApp', ['ngDialog'])
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
		      }, true);
			element.borderify(scope.data);
			var hr = new Date().getHours();
			if(hr>1) {
				hr= hr-1;
			}
			$("#scheduler-wrapper").scrollTop(hr*(scope.data.unitValueInMin/scope.data.gradationBetweenPerUnit)*scope.data.gradationBetweenPerUnitpx);
		}
	}
});