<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menuContext.jsp" />
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/primitives/primitives.latest.css"/>'>
<link rel="stylesheet" media="screen" href='<c:url value="resources/javascripts/app/bower_components/ng-dialog/css/ngDialog.css"/>'>
<!-- <div id="basicdiagram2" style="width: 640px; height: 480px; border-style: dotted; border-width: 1px;">
</div> -->
<div ng-app="RoleApp" ng-controller="EmployeeController" ng-init='loadData(${data})'>
    <div id="centerpanel"  style="overflow: hidden; padding: 0px; margin: 0px; border: 0px;">
        <div bp-org-diagram data-options="myOptions" data-on-highlight-changed="onMyHighlightChanged()"  data-on-cursor-changed="onMyCursorChanged()" style="width: 100%; height: 600px; border-style: dotted; border-width: 1px;"></div>
    </div>
</div>
<%-- <script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/angular/angular.min.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/resources/javascripts/primitives/primitives.latest.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-dialog/js/ngDialog.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload-shim.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/javascripts/app/bower_components/ng-file-upload/angular-file-upload.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/roleController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/employeeController/controller.js"/>'></script>
