<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Bérletek">
	
	<@tags.basketDialog "/kosar/rendel"/>
	<@tags.basketMergingDialog />
	<@tags.errorMessage />
	
	<@notificationDiv />
	
	<#if user??>
		<div class="span6 bs-docs-data">
			<@userDataUpdatePanel />
		</div>
		<div class="span6 bs-docs-password">
			<@userPasswordUpdatePanel />
		</div>
	<#else>
		<div class="alert alert-error">
			<!--button type="button" class="close" data-dismiss="alert">&times;</button-->
			<span>Nem található a felhasználó az adatbázisban!</span>
		</div>
	</#if>

	<@loadScripts.loadScripts />
	<script src="<@spring.url relativeUrl="/resources/js/forPages/usermodosit.js"/>"></script>
</@template.masterTemplate>

<#macro userDataUpdatePanel>
	<form id="userDataUpdateForm" class="form-horizontal" action="<@spring.url relativeUrl="/modositas/adatok"/>" method="POST">
			<input id="userDataUpdate-username" type="hidden" value="${user.username}" />
			<@tags.errorAlert "userDataUpdate-fullNameAlert" "A teljes névnek legalább 6 karakternek kell lennie!" />
			<div class="control-group">
		    	<label class="control-label" for="userDataUpdate-fullName">Teljes név</label>
			    <div class="controls">
			    	<input type="text" id="userDataUpdate-fullName" name="fullName" value="${user.fullName}" placeholder="Teljes név">
			    </div>
		  	</div>
		  	<@tags.errorAlert "userDataUpdate-emailAlert" "Nem email címet adott meg!" />
		  	<@tags.errorAlert "userDataUpdate-emailCheckAlert" "Ezzel az email címmel már regisztráltak!" />
			<div class="control-group">
			    <label class="control-label" for="userDataUpdate-email">Email</label>
			    <div class="controls">
			    	<input type="text" id="userDataUpdate-email" name="email" placeholder="Email" value="${user.email}">
			    </div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="userDataUpdate-mobile">Mobil</label>
			    <div class="controls">
			    	<input type="text" id="userDataUpdate-mobile" name="mobile" placeholder="Mobil" value="${user.mobile}">
			    </div>
			</div>
			<@tags.errorAlert "userDataUpdate-passwordAlert" "A jelszó helytelen!" />
			<div class="control-group">
			    <label class="control-label" for="userDataUpdate-confirmPassword">Jelszó</label>
			    <div class="controls">
			    	<input type="password" id="userDataUpdate-confirmPassword" name="confirmPassword" placeholder="Jelszó">
			    <span class="help-block">* Az adatok módosításához meg kell adnia jelszavát!</span>
			    </div>
			</div>
			<@tags.errorAlert "userDataUpdate-inputsAlert" "Egyik mező sem maradhat üresen!" />
			<div class="control-group">
			    <div class="controls">
					<button id="userDataUpdateButton" class="btn btn-success" type="button">Adatok módosítása</button>
			    </div>
			</div>
		</form>
</#macro>

<#macro userPasswordUpdatePanel>
	<form id="userPasswordUpdateForm" class="form-horizontal" action="<@spring.url relativeUrl="/modositas/jelszo"/>" method="POST">
		<input id="userPasswordUpdate-username" type="hidden" value="${user.username}" />
		<@tags.errorAlert "oldPasswordAlert" "Nem a jelszavát adta meg!" />
		<div class="control-group">
		    <label class="control-label" for="oldPassword">Régi jelszó</label>
		    <div class="controls">
		    	<input type="password" id="oldPassword" name="oldPassword" placeholder="Régi jelszó">
		    </div>
		</div>
		<@tags.errorAlert "newPasswordAlert" "A két megadott jelszó nem egyezik meg!" />
		<div class="control-group">
		    <label class="control-label" for="newPassword">Új jelszó</label>
		    <div class="controls">
		    	<input type="password" id="newPassword" name="newPassword" placeholder="Új jelszó">
		    </div>
		</div>
		<div class="control-group">
		    <label class="control-label" for="newPasswordRe">Új jelszó újra</label>
		    <div class="controls">
		    	<input type="password" id="newPasswordRe" name="newPasswordRe" placeholder="Új jelszó újra">
		    </div>
		</div>
		<@tags.errorAlert "userPasswordUpdate-inputsAlert" "Egyik mező sem maradhat üresen!" />
		<div class="control-group">
		    <div class="controls">
		    	<button id="userPasswordUpdateButton" class="btn btn-success" type="button">Jelszó módosítása</button>
		    </div>
		</div>
	</form>
</#macro>

<#macro notificationDiv>
	<div id="notificationDiv" class="alert alert-success" style="display:none;">
		<span></span>
	</div>
</#macro>