<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html ng-app="app">
<head>

<link rel="stylesheet" href='<c:url value="/resources/angular-widget/app/styles/main.css"/>'>
<link rel="stylesheet" type="text/css" href='<c:url value="/resources/angular-widget/app/bower_components/jqPlot/jquery.jqplot.css"/>'>

	<script type="text/javascript" src='<c:url value="/resources/angular-widget/app/settings.js"/>'></script>

        <!-- build:js scripts/modules.js -->
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/jquery/jquery.js"/>'></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/3.7.0/lodash.min.js"></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular/angular1.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/jquery-ui/jquery-ui.js"/>'></script>
        <script language="javascript" type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/jqPlot/jquery.jqplot.min.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/jqPlot/plugins/jqplot.highlighter.min.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/jqPlot/plugins/jqplot.cursor.min.js"/>'></script>
        <script language="javascript" type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/jqPlot/plugins/jqplot.funnelRenderer.js"/>'></script>
        
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-route/angular-route.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-ui-sortable/sortable.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-resource/angular-resource.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-cookies/angular-cookies.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-sanitize/angular-sanitize.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-bootstrap/ui-bootstrap-tpls.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/ng-grid/ng-grid-2.0.11.debug.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/d3/d3.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/nvd3/nv.d3.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angularjs-nvd3-directives/dist/angularjs-nvd3-directives.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/malhar-angular-dashboard/dist/malhar-angular-dashboard.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/malhar-angular-widgets/dist/malhar-angular-widgets.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/malhar-angular-table/dist/mlhr-table.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/pines-notify/pnotify.core.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/angular-pines-notify/src/pnotify.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/bower_components/visibilityjs/lib/visibility.core.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/vendor/visibly.js"/>'></script>
        <!-- endbuild -->

        <!-- build:js({.tmp,app}) scripts/scripts.js -->
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/app.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/services/datamodel.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/services/service.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/services/gateway.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/services/widgets.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/main.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/simple.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/serverdata.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/rest.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/apps.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/discovery.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/topics.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/angular-widget/app/scripts/controllers/widgetOptions.js"/>'></script>
        <!-- endbuild -->


</head>

<jsp:include page="menuContext.jsp" />
<body ng-controller="MainCtrl">
<div class="row">
    <div class="col-md-12">
        <div template-url='<c:url value="resources/angular-widget/app/bower_components/malhar-angular-dashboard/src/components/directives/dashboard/dashboard.html"/>' 
        dashboard="dashboardOptions"></div>
        <input type="hidden" id="employeeID" ng-model="userId" value="${user.id}">
    </div>
</div>
</body>

<script>
'use strict';

angular.module('app')
  .controller('MainCtrl'
		  , function ($scope,$http, $interval, stackedAreaChartSampleData, pieChartSampleData,RestFunnelDataModel, RandomTimeSeriesDataModel, RandomTopNDataModel, ProjectDataModel, TaskDataModel)
		  {

       $scope.userId = 1;
       
       var widgetDefinitions = [
                    
<c:forEach var="project" items="${myProjects}">
	  {
	    name: '${project.name}',
	    directive: 'wt-gauge',
	    title: 'Health',
	    attrs: {
	      label:"${project.name}", 	
	      value: '${project.percentage}',
	      size: '100'
	    },
	    style: {
	      width: '150px'
	    }
	  },
</c:forEach>
<c:forEach var="projectType" items="${myProjectTypes}">	  
	  {
          directive: 'wt-funnel',
          name: '${projectType.name}',
          dataAttrName: 'data',
          dataModelType: FunnelDataModel,
          dataModelOptions: {
          	  projectType: '${projectType.name}'
          },
          style: {
            width: '33%'
          }
      },  
 </c:forEach>                        
                        
                        {
                            name: 'Tasks',
                            title: 'Tasks',
                            directive: 'wt-tasks',
                            dataAttrName: 'data',
                            dataModelOptions: {
                          	  userId: $scope.userId
                            },
                            dataModelType: TaskDataModel,
                            style: {
                              width: '40%'
                            }
                        },
                        
                        {
                            name:'Projects',
                            title:'Projects',
                            directive: 'wt-projects',
                            dataAttrName: 'data',
                            dataModelOptions: {
                          	  userId: $scope.userId
                            },
                            dataModelType: ProjectDataModel,
                            style: {
                              width: '40%'
                            }
                          },
                        
                      ];
       
       var defaultWidgets = _.map(widgetDefinitions, function (widgetDef) {
    	      return {
    	        name: widgetDef.name
    	      };
    	    });

    	    $scope.dashboardOptions = {
    	      widgetButtons: false,
    	      attrs: {
    	    	  templateUrl:'\resources\angular-widget\app\bower_components\malhar-angular-dashboard\src\components\directives\dashboard\dashboard.html1'
    	      },
    	      hideWidgetClose: true,
    	      hideWidgetSettings: true,
    	      hideWidgetName: true,
    	      widgetDefinitions: widgetDefinitions,
    	      defaultWidgets: defaultWidgets
    	    };
  });
</script>