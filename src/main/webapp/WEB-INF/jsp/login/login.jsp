<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title>TimeMinder</title>

<meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <meta name="description" content="Fullscreen Background Image Slideshow with CSS3 - A Css-only fullscreen background image slideshow" />
        <meta name="keywords" content="css3, css-only, fullscreen, background, slideshow, images, content" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet"  href='<c:url value="/resources/stylesheets/demo.css"/>'>
        <link rel="stylesheet"  href='<c:url value="/resources/stylesheets/style1.css"/>'>
		<script type="text/javascript" src="/resources/stylesheets/modernizr.custom.86080.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap -->
<script src='<c:url value="resources/javascripts/jquery-1.9.0.min.js"/>' type="text/javascript"></script>
<link rel="stylesheet" media="screen" href='<c:url value="resources/stylesheets/bootstrap.css"/>'>
<link rel="stylesheet" href='<c:url value="resources/stylesheets/bootstrap-responsive.css"/>'>
<link rel="stylesheet"  type="text/css" media="all" href='<c:url value="resources/stylesheets/jquery.pnotify.default.css"/>' />
<link rel="stylesheet"  type="text/css" media="all" href='<c:url value="resources/stylesheets/jquery.pnotify.default.icons.css"/>' />
<script src='<c:url value="resources/javascripts/jquery.pnotify.js"/>' type="text/javascript"></script> 
</head>

<body id="page">

		 <ul class="cb-slideshow">
            <li><span>Image 01</span>
            <li><span>Image 02</span>
            <li><span>Image 03</span>
            <li><span>Image 04</span>
            <li><span>Image 05</span>
            <li><span>Image 06</span>
        </ul> 
	<div class="container">
		<div class="heading">
			
			<h1>TimeMinder</h1>
			<br> <br> <br>
			
			<br> <br>
			<h1>${emailPart}</h1>
			<c:if test="${not empty error}">
				<p class="error" style="color: red;">${error}</p>
			</c:if>

			<c:if test="${not empty success}">
				<p class="success">${success}</p>
			</c:if>

			<c:if test="${not empty registered}">
				<p class="registered" class="serif" style="color: #00ff00;">
					${registered}</p>
			</c:if>
			
			<form:form action="login" method="POST">
				<fieldset id="login_field">
					<div class="well" style="width: 350px;background-color: rgba(0, 0, 0, 0.2);">
						<p>
							<input type="email" name="email" value="${loginForm.data().get('email')}"
								placeholder="Email" rel="popover" required="required"
								data-content="What's your Email Address?">
						</p>

						<p>
							<input type="password" name="password" placeholder="Password"
								rel="popover" required="required"
								data-content="What's your password on Time Minder?">
						</p>
						<p>
							<button type="submit" class="btn btn-success">Login</button>
						</p>
					</div>
				</fieldset>
			</form:form>

			<div class="forgotPass" style="float: left;margin-left: 1%;">
				<a href="forgot">Forgot Password?</a>
			</div>
			<div class="registration" style="margin-top: 5%;margin-right: 38%;">
				<h2>
					Don't have an account yet? <a href="registration"
						class="btn btn-large btn-warning">Register Now</a>
				</h2>
			</div>

			<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<h3 id="myModalLabel">Privacy & Terms</h3>
				</div>
				<div class="modal-body">
					<p>
					<h4>Company</h4>
										</p>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				</div>
			</div>
			<footer style="margin-top: 17%;">
				<p>
					&copy; 2014 MindNerves Technologies. &middot; <a href="#myModal"
						data-toggle="modal">Privacy & Terms</a>
				</p>
			</footer>
		</div>
	</div>
	
	<script src='<c:url value="resources/javascripts/bootstrap.min.js"/>'></script>
	<script src='<c:url value="resources/javascripts/jqBootstrapValidation.js"/>'>
	<script type="text/javascript" src='<c:url value="resources/javascripts/jquery.validate.js"/>'></script>
</body>
	
<script type="text/javascript">
	$('#login_field input').on('mouseenter',function() {
		$(this).popover('show')
	});
	$('#login_field input').on('mouseleave',function() {
		$(this).popover('hide')
	});
	$(document).ready(function(){
		if(localStorage.getItem("notification_mesg")){
			
			$.pnotify({
				history:false,
		        
		        text: localStorage.getItem("notification_mesg")
		    });
			localStorage.removeItem("notification_mesg")
		}
	});
</script>

<style>
body {
	padding-bottom: 40px;
	color: #5a5a5a;
}

h1 {
	margin: 10px 0;
	font-family: walkaway;
	font-weight: bold;
	line-height: 20px;
	color: black;
	text-rendering: optimizelegibility;
	font-style: italic;
}

.copy{
padding-left:60px;
color: grey;

}

.heading {
	padding-top: 40px;
}

.content {
	padding-top: 100px;
	border-color: black;
	border-style: solid;
	border-width: 2px;
}

/* CUSTOMIZE THE NAVBAR
    -------------------------------------------------- */

/* Special class on .container surrounding .navbar, used for positioning it into place. */
.navbar-wrapper {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	z-index: 10;
	margin-top: 20px;
	margin-bottom: -90px;
	/* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
}

.navbar-wrapper .navbar {
	
}

/* Remove border and change up box shadow for more contrast */
.navbar .navbar-inner {
	border: 0;
	-webkit-box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
	-moz-box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
	box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
}

#navbar-inner-scroll {
	border: 0;
	-webkit-box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
	-moz-box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
	box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
	position: relative;
	top: 10px;
}

/* Downsize the brand/project name a bit */
.navbar .brand {
	font-size: 20px;
	font-family: apple chancery;
	font-weight: bold;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, .5);
	padding: 14px 20px 16px;
}

.navbar-inverse .brand,.navbar-inverse .nav>li>a {
	color: #F87217;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
}

/* Navbar links: increase padding for taller navbar */
.navbar .nav>li>a {
	padding: 15px 20px;
}

/* Offset the responsive button for proper vertical alignment */
.navbar .btn-navbar {
	margin-top: 10px;
}

/* MARKETING CONTENT
    -------------------------------------------------- */

/* Center align the text within the three columns below the carousel */
.marketing .span4 {
	text-align: center;
}

.marketing h2 {
	font-weight: normal;
	color: #F87217;
}

.marketing .span4 p {
	margin-left: 10px;
	margin-right: 10px;
}
</style>