<#import "/spring.ftl" as spring />
<#macro templateHead title="Zsír-Szabó Fitness">
	<head>
		<meta charset="utf-8">
		<title>${title}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		 
		<!-- Le styles -->
		<link href="<@spring.url relativeUrl="/resources/css/bootstrap.css"/>" rel="stylesheet">
		<link href="<@spring.url relativeUrl="/resources/css/docs.css"/>" rel="stylesheet">
		<link href="<@spring.url relativeUrl="/resources/css/datepicker.css"/>" rel="stylesheet" />
		<link href="<@spring.url relativeUrl="/resources/css/main.css"/>" rel="stylesheet" />
		<style> body {}	</style>
		<link href="<@spring.url relativeUrl="/resources/css/bootstrap-responsive.css"/>" rel="stylesheet">
		 
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		    <![endif]-->
		 
		<!-- Le fav and touch icons -->
		<link rel="shortcut icon" href="<@spring.url relativeUrl="/resources/favicon.ico"/>">
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
		<link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
	</head>
</#macro>