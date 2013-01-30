<#import "/tag/tags.ftl" as tags />
<#import "/spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro headerLayout>
	<div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                    <div class="container">
                            <a class="btn btn-navbar" data-toggle="collapse"
                                    data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                                    class="icon-bar"></span> <span class="icon-bar"></span>
                            </a> <a class="brand" href="#">Fitness</a>
                            <div id="navDiv" class="nav-collapse collapse">
                                    <ul class="nav">
                                    	<li><a href="<@spring.url relativeUrl="/"/>">Kezdőlap</a></li>
                                    	<li><a href="<@spring.url relativeUrl="/aruhaz/1"/>">Áruház</a></li>
										<li><a href="<@spring.url relativeUrl="/edzesek"/>">Edzések</a></li>
										<li><a href="<@spring.url relativeUrl="/berletek"/>">Bérletek</a></li>
										<@security.authorize access="hasRole('Recepcionist')">
											<li><a href="<@spring.url relativeUrl="/beleptetes"/>">Beléptetés</a></li>
										</@security.authorize>
										<@security.authorize access="hasRole('ProductAdmin')">
											<li><a href="<@spring.url relativeUrl="/raktar"/>">Raktár</a></li>
										</@security.authorize>
										<@security.authorize access="hasRole('SystemAdmin')">
											<li><a href="<@spring.url relativeUrl="/admin/jogosultsagok"/>">Jogosultsagok</a></li>
										</@security.authorize>
										<@security.authorize access="isAnonymous()">
											<li><a id="registrationButton">Regisztráció</a></li>
										</@security.authorize>
                                    </ul>
                                    <div style="float:right;">
                                    	<@security.authorize access="isAnonymous()">
											<@basketLayout/>
                                        	<button type="button" id="loginFormButton" class="btn btn-primary"><i class="icon-white icon-chevron-down"></i> Bejelentkezés</button>
										</@security.authorize>
                                    	<@security.authorize access="isAuthenticated()">
											<span style="color:white;vertical-align: middle;">Üdvözlet: <@security.authentication property="principal.username"/></span>
											<@basketLayout/>
											<a class="btn btn-danger" href="<@spring.url relativeUrl="/j_spring_security_logout"/>">Kijelentkezés</a>
										</@security.authorize>
									</div>
                            </div>
                            <!--/.nav-collapse -->
                    </div>
            </div>
    </div>
    <@tags.registrationDialog />
   <div id="loginForm" class="navbar-inverse" style="background-color:black;position:fixed; z-index:7;width: 100%; display: none;">
	    <div class="navbar-inner">
	    	<div class="container">
				<@tags.login/>
	        </div>
	   	</div>	
	</div>
    
    <header class="jumbotron subhead headerback" id="overview">
            <div class="container">
                    <h1>Zsír-Szabó Fitnesz</h1>
                    <p class="lead">Üdvözöljük!</p>
            </div>
    </header>
</#macro>

<#macro basketLayout>
	<#if basket?exists>
			<div class="dropdown" style="vertical-align: middle;display:inline-block;">
				  <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				    <i class="icon-shopping-cart"></i> Kosár
				    <span class="caret"></span>
				  </a>
				  <ul class="dropdown-menu">
					<li><a href="#basketModal" data-toggle="modal">Kosár tartalma</a></li>
					<li><a data-toggle="modal" href="<@spring.url relativeUrl="/aruhaz/confirmBasket"/>">Megrendelés</a></li>
					<li><a data-toggle="modal" href="<@spring.url relativeUrl="/aruhaz/deleteBasket"/>">Kosár törlése</a></li>
				  </ul>
			</div>
	 </#if>
</#macro>