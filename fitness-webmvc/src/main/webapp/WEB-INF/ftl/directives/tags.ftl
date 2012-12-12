<#import "/spring.ftl" as spring />

<#macro login>
	<div style="padding-top:4px">
    	<form action="<@spring.url relativeUrl="/j_spring_security_check"/>" method="POST" class="form-inline" style="margin-bottom:0px">
		  <fieldset>
		    <input type="text" name="j_username" placeholder="Felhasználó név">
		    <input type="password" name="j_password" placeholder="Jelszó">
		    <button type="submit" class="btn btn-medium btn-primary">OK</button>
		  </fieldset>
		</form>
    </div>
</#macro>

<#macro registrationDialog>
	<div id="registrationDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<!--button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button-->
			<h3 id="myModalLabel">Regisztráció</h3>
		</div>
		<div class="modal-body">
			<form id="registrationForm" class="form-horizontal">
				<div class="control-group">
			    	<label class="control-label" for="fullName">Teljes név</label>
				    <div class="controls">
				    	<input type="text" id="fullName" placeholder="Teljes név">
				    </div>
			  	</div>
			  	<div class="control-group">
			    	<label class="control-label" for="userName">Felhasználó név</label>
				    <div class="controls">
				    	<input type="text" id="userName" placeholder="Felhasználó név">
				    </div>
			  	</div>
				<div class="control-group">
				    <label class="control-label" for="inputEmail">Email</label>
				    <div class="controls">
				    	<input type="text" id="inputEmail" placeholder="Email">
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="mobile">Mobil</label>
				    <div class="controls">
				    	<input type="text" id="mobile" placeholder="Mobil">
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="inputPassword">Jelszó</label>
				    <div class="controls">
				    	<input type="password" id="inputPassword" placeholder="Jelszó">
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="inputPasswordRe">Jelszó újra</label>
				    <div class="controls">
				    	<input type="password" id="inputPasswordRe" placeholder="Jelszó újra">
				    </div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Mégse</button>
			<button class="btn btn-primary">Regisztrál</button>
		</div>
	</div>
</#macro>