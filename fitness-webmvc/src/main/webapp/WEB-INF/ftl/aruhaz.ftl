<#import "/directives/tags.ftl" as tags/>

<!DOCTYPE html>
<html lang="hu">
<head>
	<meta charset="utf-8">
	<title>Zsír-Szabó Fitness</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	 
	<!-- Le styles -->
	<link href="resources/css/bootstrap.css" rel="stylesheet">
	<link href="resources/css/docs.css" rel="stylesheet">
	<style> body {}	</style>
	<link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
	 
	<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	    <![endif]-->
	 
	<!-- Le fav and touch icons -->
	<link rel="shortcut icon" href="resources/ico/favicon.ico">
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
</head>
 
<body>
 
        <div class="navbar navbar-inverse navbar-fixed-top">
                <div class="navbar-inner">
                        <div class="container">
                                <a class="btn btn-navbar" data-toggle="collapse"
                                        data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                                        class="icon-bar"></span> <span class="icon-bar"></span>
                                </a> <a class="brand" href="#">Fitness</a>
                                <div id="navDiv" class="nav-collapse collapse">
                                        <ul class="nav">
                                        	<@tags.url path="" name="Kezdőlap" />
											<@tags.url path="aruhaz" name="Áruház" />
											<@tags.url path="edzesek" name="Edzések" />
                                            <li><a id="loginFormButton" class="btn-small btn-primary">Bejelentkezés</a></li>
                                        </ul>
                                </div>
                                <!--/.nav-collapse -->
                        </div>
                </div>
        </div>
        
        <div id="loginForm" class="navbar-inverse" style="background-color:black;">
	        <div class="navbar-inner">
	        	<div class="container">
					<@tags.login />
	            </div>
	       	</div>	
        </div>
        
        <header class="jumbotron subhead headerback" id="overview">
                <div class="container">
                        <h1>Zsír-Szabó Fitnesz</h1>
                        <p class="lead">Welcome</p>
                </div>
        </header>
        <div class="container">
		
		<h2>Áruház</h2>
		<h2>Server time: ${serverTime}</h2>     
	 
	</div>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="resources/js/bootstrap-transition.js"></script>
    <script src="resources/js/bootstrap-alert.js"></script>
    <script src="resources/js/bootstrap-modal.js"></script>
    <script src="resources/js/bootstrap-dropdown.js"></script>
    <script src="resources/js/bootstrap-scrollspy.js"></script>
    <script src="resources/js/bootstrap-tab.js"></script>
    <script src="resources/js/bootstrap-tooltip.js"></script>
    <script src="resources/js/bootstrap-popover.js"></script>
    <script src="resources/js/bootstrap-button.js"></script>
    <script src="resources/js/bootstrap-collapse.js"></script>
    <script src="resources/js/bootstrap-carousel.js"></script>
    <script src="resources/js/bootstrap-typeahead.js"></script>
	<script src="resources/js/fitness-onload.js"></script>
	
  </body>
</html>