var app = angular.module('SupplierInfoApp', ['ui.bootstrap','ui.bootstrap.datetimepicker']);

app.factory('MyHttpInterceptor', function ($q) {
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