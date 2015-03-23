
(function(window, angular, undefined) {
	'use strict';

	angular.module('angularWizard', [])
	



		.directive('ngWizard', ['$window','$parse', function ($window,$parse) {
			return {
				
				
				
				link: function (scope, element, attr) {
					element.steps({
						headerTag : "h2",
						bodyTag : "div",
						transitionEffect : "slideLeft",
						onStepChanging: function (event, currentIndex, newIndex) { 
							console.log("event : "+event);
							console.log("currentIndex : "+currentIndex);
							console.log("newIndex : "+newIndex);
							return true; 
						},
						onFinished: function (event, currentIndex) {
							$( "#submit" ).trigger( "click" );
						}
					})
					
				}
				
			};
		}]);

})(window, window.angular);
