<#import "/spring.ftl" as spring />

<#macro login>
	<div style="padding-top:4px;">
    	<form action="<@spring.url relativeUrl="/j_spring_security_check"/>" method="POST" class="form-inline" style="margin-bottom:0px">
		  <fieldset>
		    <input type="text" name="j_username" placeholder="Felhasználó név">
		    <input type="password" name="j_password" placeholder="Jelszó">
		    <button type="submit" class="btn btn-medium btn-primary">OK</button>
		    <!--@spring.showErrors "---" "color:white;" /-->
		  </fieldset>
		</form>
    </div>
</#macro>

<#macro registrationDialog>
	<div id="registrationDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<h3 id="myModalLabel">Regisztráció</h3>
			<@errorAlert "inputs" "Töltsön ki minden mezőt!" />
		</div>
		<div class="modal-body" style="height:100%;">
			<form id="registrationForm" class="form-horizontal" action="<@spring.url relativeUrl="/registration"/>" method="POST">
				<div class="control-group">
			    	<label class="control-label" for="fullName">Teljes név</label>
				    <div class="controls">
				    	<input type="text" id="fullName" name="fullName" placeholder="Teljes név">
				    </div>
			  	</div>
			  	<div class="control-group">
			    	<label class="control-label" for="username">Felhasználó név</label>
				    <div class="controls">
				    	<input type="text" id="username" name="username" placeholder="Felhasználó név">
				    </div>
			  	</div>
			  	<@errorAlert "emailAlert" "Nem email címet adott meg!" />
				<div class="control-group">
				    <label class="control-label" for="email">Email</label>
				    <div class="controls">
				    	<input type="text" id="email" name="email" placeholder="Email">
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="mobile">Mobil</label>
				    <div class="controls">
				    	<input type="text" id="mobile" name="mobile" placeholder="Mobil">
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="password">Jelszó</label>
				    <div class="controls">
				    	<input type="password" id="password" name="password" placeholder="Jelszó">
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="passwordRe">Jelszó újra</label>
				    <div class="controls">
				    	<input type="password" id="passwordRe" name="passwordRe" placeholder="Jelszó újra">
				    </div>
				</div>
				<@errorAlert "passwordAlert" "A két megadott jelszó nem egyezik meg!" />
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Mégse</button>
			<button id="registerButton" class="btn btn-primary">Regisztrál</button>
		</div>
	</div>
</#macro>

<#macro errorAlert id message>
	<div id="${id}" class="alert alert-error" style="display:none;">
	  <button type="button" class="close" data-dismiss="alert">&times;</button>
	  <strong>Hiba! </strong><span>${message}</span>
	</div>
</#macro>
