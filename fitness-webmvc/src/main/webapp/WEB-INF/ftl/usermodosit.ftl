<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Bérletek">
	
<@tags.basketDialog "/berletek/megrendel"/>
<@tags.basketMergingDialog />
<@tags.errorMessage />

	<h2>Beállítások</h2>
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
</@template.masterTemplate>

<#macro userDataUpdatePanel>
	<form id="userDataUpdateForm" class="form-horizontal" action="<@spring.url relativeUrl="/registration"/>" method="POST">
			<input id="checkUserUrl" type="hidden" value="<@spring.url relativeUrl="/checkUser"/>" />
			<div class="control-group">
		    	<label class="control-label" for="fullName">Teljes név</label>
			    <div class="controls">
			    	<input type="text" id="fullName" name="fullName" value="${user.fullName}" placeholder="Teljes név">
			    </div>
		  	</div>
		  	<@tags.errorAlert "emailAlert" "Nem email címet adott meg!" />
		  	<@tags.errorAlert "emailCheckAlert" "Ezzel az email címmel már regisztráltak!" />
			<div class="control-group">
			    <label class="control-label" for="email">Email</label>
			    <div class="controls">
			    	<input type="text" id="email" name="email" placeholder="Email" value="${user.email}">
			    </div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="mobile">Mobil</label>
			    <div class="controls">
			    	<input type="text" id="mobile" name="mobile" placeholder="Mobil" value="${user.mobile}">
			    </div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="confirmPassword">Jelszó</label>
			    <div class="controls">
			    	<input type="password" id="confirmPassword" name="confirmPassword" placeholder="Jelszó">
			    <span class="help-block">* Az adatok módosításához meg kell adnia jelszavát!</span>
			    </div>
			</div>
			<div class="control-group">
			    <div class="controls">
					<button id="userDataUpdateButton" class="btn btn-success">Adatok módosítása</button>
			    </div>
			</div>
		</form>
</#macro>

<#macro userPasswordUpdatePanel>
	<form id="userDataUpdateForm" class="form-horizontal" action="<@spring.url relativeUrl="/registration"/>" method="POST">
		<input id="checkUserUrl" type="hidden" value="<@spring.url relativeUrl="/checkUser"/>" />
		<div class="control-group">
		    <label class="control-label" for="oldPassword">Régi jelszó</label>
		    <div class="controls">
		    	<input type="password" id="oldPassword" name="oldPassword" placeholder="Régi jelszó">
		    </div>
		</div>
		<@tags.errorAlert "passwordAlert" "A két megadott jelszó nem egyezik meg!" />
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
		<div class="control-group">
		    <div class="controls">
		    	<button id="userPasswordUpdateButton" class="btn btn-success">Jelszó módosítása</button>
		    </div>
		</div>
	</form>
</#macro>