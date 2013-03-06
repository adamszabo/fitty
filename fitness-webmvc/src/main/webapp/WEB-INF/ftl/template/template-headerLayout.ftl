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
                            </a> <@userLayout/>
                            <div id="navDiv" class="nav-collapse collapse">
                                    <ul class="nav">
	                                    <li id="homeLi"><a href="<@spring.url relativeUrl="/"/>"><i class="icon-home"></i></a></li>
                                    	<@security.authorize access="hasAnyRole('Client', 'ROLE_ANONYMOUS', 'Trainer')">
	                                    	<li><a href="<@spring.url relativeUrl="/aruhaz"/>">Áruház</a></li>
											<li><a href="<@spring.url relativeUrl="/edzesek"/>">Edzések</a></li>
											<li><a href="<@spring.url relativeUrl="/berletek"/>">Bérletek</a></li>
											<li><a href="<@spring.url relativeUrl="/edzok"/>">Edzők</a></li>
										</@security.authorize>
										<@security.authorize access="hasRole('Recepcionist')">
											<li><a href="<@spring.url relativeUrl="/beleptetes"/>">Beléptetés</a></li>
										</@security.authorize>
										<@security.authorize access="hasRole('ProductAdmin')">
											<li><a href="<@spring.url relativeUrl="/raktar"/>">Raktár</a></li>
										</@security.authorize>
										<@security.authorize access="hasRole('SystemAdmin')">
											<li><a href="<@spring.url relativeUrl="/admin"/>">Jogosultsagok</a></li>
											<li><a href="<@spring.url relativeUrl="/reklam"/>">Reklám</a></li>
										</@security.authorize>
										<li><a href="<@spring.url relativeUrl="/kapcsolatok"/>">Kapcsolat</a></li>
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
											<@basketLayout/>
										</@security.authorize>
									</div>
                            </div>
                            <!--/.nav-collapse -->
                    </div>
            </div>
            <div id="loginForm" class="navbar-inverse loginform-layout">
			    <div class="navbar-inner">
			    	<div class="container">
						<@tags.login/>
			        </div>
			   	</div>	
			</div>
    </div>
    <@tags.registrationDialog />
    
    <form action="<@spring.url relativeUrl="/kosar/torol"/>">
		<@tags.confirmDialog "basket-delete" "Biztos, hogy törli a kosarat?" "Törlés" "Kosár törlése"/>
	</form>
	
   
    
    <header class="jumbotron subhead headerback" id="overview">
            <div class="container">
                    <h1>Zsír-Szabó Fitnesz</h1>
                    <p class="lead">Üdvözöljük!</p>
            </div>
    </header>
    <input type="hidden" name="defaultUrl" id="defaultUrl" value="<@spring.url relativeUrl="/"/>"/>
</#macro>

<#macro basketLayout>
	<#if basket?exists>
			<div class="dropdown" style="vertical-align: middle;display:inline-block;">
				  <a class="btn btn-inverse dropdown-toggle" data-toggle="dropdown" href="#">
				    <i class="icon-white icon-shopping-cart"></i> 
				    <span class="caret"></span>
				  </a>
				  <ul class="dropdown-menu">
					<li><a href="#basketModal" data-toggle="modal">Kosár tartalma</a></li>
					<li><a data-toggle="modal" href="#basket-deleteConfirmDialog">Kosár törlése</a></li>
				  </ul>
			</div>
	 </#if>
</#macro>

<#macro userLayout>
	<@security.authorize access="isAuthenticated()">
		<div class="dropdown" style="vertical-align: middle;display:inline-block;float:right;margin-left:5px">
			  <a class="brand dropdown-toggle" data-toggle="dropdown" href="#">
			    <span id="username-security-span"><@security.authentication property="principal.username"/></span>
			  </a>
			  <ul class="dropdown-menu">
			  	<li><a id="userOrdersDetailsButton" href="<@spring.url relativeUrl="/rendelesek/atveheto"/>"><i class="icon-th-list"></i> Rendelések</a></li>
				<li><a id="updateUserDetailsButton" href=""><i class="icon-wrench"></i> Beállítások</a></li>
				<li><a href="<@spring.url relativeUrl="/j_spring_security_logout"/>"><i class="icon-off"></i> Kijelentkezés</a></li>
			  </ul>
		</div>
	</@security.authorize>
</#macro>