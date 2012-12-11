<#import "/directives/tags.ftl" as tags />

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
                                    	<@tags.url path="" name="Kezdőlap" />
										<@tags.url path="aruhaz" name="Áruház" />
										<@tags.url path="edzesek" name="Edzések" />
										<li><a id="registrationButton">Regisztráció</a></li>
                                        <li><button type="button" id="loginFormButton" class="btn btn-primary"><i class="icon-white icon-chevron-down"></i> Bejelentkezés</button></li>
                                    </ul>
                            </div>
                            <!--/.nav-collapse -->
                    </div>
            </div>
    </div>
    <@tags.registrationDialog />
   <div id="loginForm" class="navbar-inverse" style="background-color:black;position:absolute; z-index:2;width: 100%;" hidden="true">
	    <div class="navbar-inner">
	    	<div class="container">
				<@tags.login/>
	        </div>
	   	</div>	
	</div>
    
    <header class="jumbotron subhead headerback" id="overview">
            <div class="container">
                    <h1>Zsír-Szabó Fitnesz</h1>
                    <p class="lead">Welcome</p>
            </div>
    </header>
</#macro>