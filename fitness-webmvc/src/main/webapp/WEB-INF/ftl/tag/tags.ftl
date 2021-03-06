<#import "/spring.ftl" as spring />
<#assign springTags=JspTaglibs["http://www.springframework.org/tags"] />

<#macro login>
	<div style="padding-top:4px;">
    	<form action="<@spring.url relativeUrl="/j_spring_security_check"/>" method="POST" class="form-inline" style="margin-bottom:0px">
		  <fieldset>
		    <input type="text" id="j_username" name="j_username" placeholder="Felhasználó név">
		    <input type="password" name="j_password" placeholder="Jelszó">
		    <button type="submit" class="btn btn-medium btn-primary">OK</button>
		  </fieldset>
		  <#if SPRING_SECURITY_LAST_EXCEPTION?? ><div id="loginErrorDialog" style="color:white; margin-bottom:5px;">${SPRING_SECURITY_LAST_EXCEPTION.message}</div></#if>
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
				<input id="checkUserUrl" type="hidden" value="<@spring.url relativeUrl="/checkUser"/>" />
				<div class="control-group">
			    	<label class="control-label" for="fullName">Teljes név</label>
				    <div class="controls">
				    	<input type="text" id="fullName" name="fullName" placeholder="Teljes név">
				    </div>
			  	</div>
			  	<@errorAlert "usernameAlert" "Ilyen névvel már létezik felhasználó!" />
			  	<div class="control-group">
			    	<label class="control-label" for="username">Felhasználó név</label>
				    <div class="controls">
				    	<input type="text" id="username" name="username" placeholder="Felhasználó név">
				    </div>
			  	</div>
			  	<@errorAlert "emailAlert" "Nem email címet adott meg!" />
			  	<@errorAlert "emailCheckAlert" "Ezzel az email címmel már regisztráltak!" />
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

<#macro errorMessage>
	<#if message?exists>
		<div class="alert alert-block">
	  	<button type="button" class="close" data-dismiss="alert">x</button>
	  		<h4>Hiba! </h4>
			${message}
		</div>
	</#if>
</#macro>

<#macro errorAlert id message>
	<div id="${id}" class="alert alert-error" style="display:none;">
	  <!--button type="button" class="close" data-dismiss="alert">&times;</button-->
	  <strong>Hiba! </strong><span>${message}</span>
	</div>
</#macro>

<#macro basketDialog confirmPath>
	<#if successCheckOut??>
		<div class="alert alert-success">
              <button type="button" class="close" data-dismiss="alert">×</button>
              <strong>Megrendelve!</strong> A kosár tartalma sikeresen megrendelve!
        </div>
	</#if>

	<#if basket?exists>
	<div id="basketModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Kosár tartalma</h3>
	  </div>
	  <div class="modal-body">
		<@basketElementsInTable basket "basket"/>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">Bezárás</button>
	    <a href="<@spring.url relativeUrl="${confirmPath}"/>" class="btn btn-primary">Megrendelés</a>
	  </div>
	</div>
	</#if>
</#macro>

<#macro basketElementsInTable basketName nameInString>
				  <#assign sum = 0>
		  <#if basketName["orderItems"]?has_content>
		  Termékek:
		  	<table class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Név</th>
							<th>Gyártó</th>
							<th>Egység Ár</th>
							<th>Mennyiség</th>
							<th>Össz ár</th>
						</tr>
					</thead>
					<tbody>
						<#assign iterate = 0>
						<#list basketName["orderItems"] as item>
							<#assign iterate = iterate +1>
							<#assign sum = sum + item["quantity"] * item["product"]["price"]>
							<tr>
								<td>${iterate}</td>
								<td>${item.product.name}</td>
								<td>${item.product.manufacturer}</td>
								<td>${item.product.price}</td>
								<td>${item.quantity}</td>
								<td>${item.quantity * item.product.price}</td>
								<td><#if nameInString = "anonymousBasket">
									<a href="<@spring.url relativeUrl="/aruhaz/torles/anonymous/${item.product.id}"/>" class="btn btn-mini btn-danger"><i class="icon-white icon-remove"></i></a>
									<#else>
									<a href="<@spring.url relativeUrl="/aruhaz/torles/${item.product.id}"/>" class="btn btn-mini btn-danger"><i class="icon-white icon-remove"></i></a>
									</#if>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
			</#if>
			
			<#if basketName["memberships"]?has_content>
			Bérletek:
		  	<table class="table table-hover">
		  		<#list basketName["memberships"] as membership>
					<thead>
						<tr>
							<th>Típus</th>
							<#if membership.isIntervally?string = "true">
								<th>Kezdés dátuma</th>
								<th>Lejárat dátuma</th>
							<#else>
								<th>Alkalmak száma</th>
							</#if>
							<th>Ár</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${membership.type}</td>
							<#if membership.isIntervally?string = "true">
								<td>${membership.startDate?date}</td>
								<td>${membership.expireDate?date}</td>
							<#else>
								<td>${membership.maxNumberOfEntries}
							</#if>
							<td>${membership.price}</td>
							<td><#if nameInString = "anonymousBasket">
									<a href="<@spring.url relativeUrl="/berletek/torles/anonymous/${membership.id}"/>" class="btn btn-mini btn-danger"><i class="icon-white icon-remove"></i></a>
									<#else>
									<a href="<@spring.url relativeUrl="/berletek/torles/${membership.id}"/>" class="btn btn-mini btn-danger"><i class="icon-white icon-remove"></i></a>
									</#if>
							</td>
						</tr>
					</tbody>
				</#list>
			</table>
			</#if>
			
			<#if basketName["trainings"]?has_content>
			Edzések:
		  	<table class="table table-hover">
				<thead>
					<tr>
						<th>Edző</th>
						<th>Időpont</th>
						<th>Ár</th>
					</tr>
				</thead>
				<tbody>
			  		<#list basketName["trainings"] as training>
							<tr>
								<td>${training.trainer.fullName}</td>
								<td>${training.date?string("yyyy.MM.dd HH:mm")}</td>
								<td>${training.price}</td>
								<td>
									<#if nameInString = "anonymousBasket">
										<a href="<@spring.url relativeUrl="/edzesek/torles/anonymous/${training.trainer.username}"/>" class="btn btn-mini btn-danger"><i class="icon-white icon-remove"></i></a>
										<#else>
										<a href="<@spring.url relativeUrl="/edzesek/torles/${training.trainer.username}"/>" class="btn btn-mini btn-danger"><i class="icon-white icon-remove"></i></a>
									</#if>
								</td>
							</tr>
					</#list>
				</tbody>
			</table>
			</#if>
			
			<div style="float:right">
			Össz ár: ${basketName.getPrice()}
			</div>
</#macro>

<#macro newProductDialog>
<div id="newProductModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Új termék</h3>
    <@errorAlert "new-product-inputs" "Töltsön ki minden mezőt!" />
  </div>
  <div class="modal-body">
		<form class="form-horizontal" action="<@spring.url relativeUrl="/raktar/termek/ujtermek"/>" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
			<div class="control-group">
				<label class="control-label" for="name">Termék név</label>
				<div class="controls">
					<input type="text" id="name" class="input-check" name="name" placeholder="Termék neve">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="details">Leírás</label>
				<div class="controls">
					<input type="text" id="details" class="input-check" name="details" placeholder="Termékhez kapcsolódó leírás">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="manufacturer">Gyártó</label>
				<div class="controls">
					<input type="text" id="manufacturer" class="input-check" name="manufacturer" placeholder="Termék gyártója">
				</div>
			</div>
			<@errorAlert "new-product-price" "Az ár legyen legalább 1!" />
			<div class="control-group">
				<label class="control-label" for="price">Ár</label>
				<div class="controls">
					<input type="number" id="price" class="input-check" name="price" min="1" value="1" placeholder="Termék ára">
				</div>
			</div>
			<div class="control-group">
					<@bootstrapFileUpload />
			</div>
			<div class="modal-footer">
				<div class="control-group">
					<div class="controls">
						<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Bezár</button>
						<button id="new-product-button" class="btn btn-success" type="button">Hozzáadás</button>
					</div>
				</div>
			</div>
		</form>
  </div>
</div>
</#macro>

<#macro bootstrapFileUpload>
	<div class="fileupload fileupload-new" data-provides="fileupload">
		<div class="control-label">
			<div class="fileupload-new thumbnail" style="width: 100px; height: 75px;"><img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&text=no+image" /></div>
	  		<div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 100px; max-height: 75px; line-height: 20px;"></div>
		</div>
	  <div class="controls">
	    <span class="btn btn-file btn-primary"><span class="fileupload-new">Kép tallózása</span><span class="fileupload-exists">Változtat</span><input type="file" name="file" accept="image/gif,image/png,image/pjpeg"/></span>
	    <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Eltávolít</a>
	  </div>
	</div>
</#macro>

<#macro missingElements>
	<#if missingProduct?exists>
	<div id="missesModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Az alábbi termékekből nem taláható megfelelő mennyiség a raktárban:</h3>
	  </div>
	  <div class="modal-body">
	  	<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>Név</th>
					<th>Gyártó</th>
					<th>Egység Ár</th>
					<th>Mennyiség</th>
					<th>Raktár mennyisége</th>
				</tr>
			</thead>
			<tbody>
				<#assign iterate = 0>
				<#list missingProduct as item>
					<#assign iterate = iterate +1>
					<tr>
						<td>${iterate}</td>
						<td>${item.product.name}</td>
						<td>${item.product.manufacturer}</td>
						<td>${item.product.price}</td>
						<td>
							<#list basket["orderItems"] as orderItem>
								<#if orderItem.product.id == item.product.id>
									${orderItem.quantity}
								</#if>
							</#list>
						</td>
						<td>${item.quantity}</td>
					</tr>
				</#list>
			</tbody>
		</table>
	  </div>
	  <div class="modal-footer">
	  	<form action="<@spring.url relativeUrl="/"/>">
	  		<button id="max-quantity-button" class="btn btn-success" type="button">Max mennyiség</button>
	  		<button id="delete-missing-products-button" class="btn btn-warning" type="button">Termékek törlése</button>
		    <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Bezárás</button>
	  	</form>
	  </div>
	</div>
	</#if>
</#macro>

<#macro reservedElements>
	<#if reservedTraining?exists>
	<div id="reservedModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Az alábbi edzőknek már van edzése az adott időpontban:</h3>
	  </div>
	  <div class="modal-body">
	  	<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>Edző</th>
					<th>Dátum</th>
				</tr>
			</thead>
			<tbody>
				<#assign iterate = 0>
				<#list reservedTraining as training>
					<#assign iterate = iterate +1>
					<tr>
						<td>${iterate}</td>
						<td>${training.trainer.fullName}</td>
						<td>${training.date?string("yyyy.MM.dd HH:mm")}</td>>
					</tr>
				</#list>
			</tbody>
		</table>
	  </div>
	  <div class="modal-footer">
	  	<form action="<@spring.url relativeUrl="/"/>">
	  		<button id="delete-reserved-trainings-button" class="btn btn-warning" type="button">Edzések törlése</button>
		    <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Bezárás</button>
	  	</form>
	  </div>
	</div>
	</#if>
</#macro>


<#macro confirmDialog type message confirmButtonMessage mainLabel="Figyelem!">
	<div id="${type}ConfirmDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <!--button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button-->
	    <h3 id="myModalLabel">${mainLabel}</h3>
	  </div>
	  <div class="modal-body">
	  	${message}
	  </div>
	  <div class="modal-footer">
	  	<button id="${type}ConfirmButton" class="btn btn-danger" type="submit">${confirmButtonMessage}</button>
	    <button id="${type}CancelButton" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Mégse</button>
	  </div>
	</div>
</#macro>

<#macro newMembershipDialog>
<div id="newMembershipModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Új bérlet</h3>
  </div>
  <div class="modal-body">
		<form class="form-horizontal" action="<@spring.url relativeUrl="/raktar/berlet/ujberlet"/>" method="post" accept-charset="UTF-8">
			<div class="control-group">
				<label class="control-label" for="isIntervally">Típus</label>
				<div class="controls">
					<div class="btn-group" data-toggle="buttons-radio">
						<input type="hidden" id="isIntervally" name="isIntervally" value="false"/>
	  					<button type="button" id="occassinally-membershipType" class="btn btn-primary">Alkalmi bérlet</button>
	  					<button type="button" id="intervally-membershipType" class="btn btn-primary">Időintervallum bérlet</button>
	  				</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="detail">Bérlet leírása</label>
				<div class="controls">
					<input type="text" id="detail" name="detail" placeholder="Bérlet típusa"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="maxNumberOfEntries">Alkalmak száma</label>
				<div class="controls">
					<input type="number" id="maxNumberOfEntries" name="maxNumberOfEntries" placeholder="Alkalmak száma" min="0" value="0"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="expireDateInDays">Érvényesség ideje</label>
				<div class="controls">
					<input type="number" id="expireDateInDays" name="expireDateInDays" placeholder="Érvényesség ideje napokban" min="0" value="0"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="price">Ár</label>
				<div class="controls">
					<input type="number" id="price" required="required" value="1" min="1" name="price" placeholder="Bérlet ára"/>
				</div>
			</div>
			<div class="modal-footer">
				<div class="control-group">
					<div class="controls">
						<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Bezár</button>
						<button class="btn btn-success" type ="submit">Hozzáadás</button>
					</div>
				</div>
			</div>
		</form>
  </div>
</div>
</#macro>

<#macro updateMembershipDialog>
<div id="updateMembershipModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Új bérlet</h3>
  </div>
  <div class="modal-body modal-body-memberhipUpdate">
		<form class="form-horizontal" action="<@spring.url relativeUrl="/raktar/berlet/valtoztat"/>" method="post" accept-charset="UTF-8">
			<div class="control-group">
				<input type="hidden" id="id" name="id"/>
			</div>
			<div class="control-group">
				<label class="control-label" for="isIntervallye">Típus</label>
				<div class="controls">
					<div class="btn-group" data-toggle="buttons-radio">
						<input type="hidden" id="isIntervally" name="isIntervally"/>
	  					<button type="button" id="occassinally-membershipType" class="btn btn-primary">Alkalmi bérlet</button>
	  					<button type="button" id="intervally-membershipType" class="btn btn-primary">Időintervallum bérlet</button>
	  				</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="detail">Bérlet leírása</label>
				<div class="controls">
					<input type="text" id="detail" name="detail"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="maxNumberOfEntries">Alkalmak száma</label>
				<div class="controls">
					<input type="number" id="maxNumberOfEntries" name="maxNumberOfEntries"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="expireDateInDays">Érvényesség ideje</label>
				<div class="controls">
					<input type="number" id="expireDateInDays" name="expireDateInDays"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="price">Ár</label>
				<div class="controls">
					<input type="number" id="price" required="required" value="1" min="1" name="price"/>
				</div>
			</div>
			<div class="modal-footer">
				<div class="control-group">
					<div class="controls">
						<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Bezár</button>
						<button class="btn btn-success" type ="submit">Változtatás</button>
					</div>
				</div>
			</div>
		</form>
  </div>
</div>
</#macro>

<#macro basketMergingDialog>
	<#if anonymousBasket?exists>
	<div id="basketMergingModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Szeretné az aktuális kosár tartalmát hozzáadni a kosarához?</h3>
	  </div>
	  <div class="modal-body">
		<h3>Aktuális kosár</h3>
		<@basketElementsInTable anonymousBasket "anonymousBasket"/>
	  
		<h3>Saját kosarának tartalma:</h3>
		<#if basket?exists>
		<@basketElementsInTable basket "basket"/>
		<#else>
			<h5>A jelenlegi kosara üres!</h5>
	  	</#if>
	  </div>
	  <div class="modal-footer">
	    <a class="btn btn-danger" href="<@spring.url relativeUrl="/aruhaz/anonymKosar/torles"/>">Akutális kosár törlése</a>
	    <a class="btn btn-primary" href="<@spring.url relativeUrl="/aruhaz/anonymKosar/hozzaad"/>">Hozzáadás</a>
	  </div>
	</div>
	</#if>
</#macro>

<#macro roleDetailsModal userwithroles>
<div id="roleDetailsModal-${userwithroles.user.username}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    	<h3 id="myModalLabel">Jogosultságok</h3>
  	</div>
  	<div class="modal-body">
		<div class="change-role" style="text-align:center">
			<form id="${userwithroles.user.username}" class="changeRoleForm" action="<@spring.url relativeUrl="/admin/jogosultsagok/valtoztat"/>" method="post">
				<input type="hidden" name="username" value="${userwithroles.user.username}"/>	
				<div class="btn-group btn-group-vertical" data-toggle="buttons-checkbox" style="text-align:center;padding:5px">
					<button class="btn btn-large btn-warning disabled" type="button"><i class="icon-white icon-user"></i> ${userwithroles.user.username}</button>
				  <input type="hidden" name="Client" class="client-input"/>
				  <button type="button" class="role-btn client-btn btn <#if userwithroles.roleNames?seq_contains("Client")?string("yes", "no")=="yes">active btn-success<#else>btn-danger</#if>">Kliens</button>
				  <input type="hidden" name="Trainer" class="trainer-input"/>
				  <button type="button" class="role-btn trainer-btn btn btn-primary <#if userwithroles.roleNames?seq_contains("Trainer")?string("yes", "no")=="yes">active btn-success<#else>btn-danger</#if>">Edző</button>
				  <input type="hidden" name="Recepcionist" class="recepcionist-input"/>
				  <button type="button" class="role-btn recepcionist-btn btn btn-primary <#if userwithroles.roleNames?seq_contains("Recepcionist")?string("yes", "no")=="yes">active btn-success<#else>btn-danger</#if>">Recepciós</button>
				  <input type="hidden" name="ProductAdmin" class="productAdmin-input"/>
				  <button type="button" class="role-btn productAdmin-btn btn btn-primary <#if userwithroles.roleNames?seq_contains("ProductAdmin")?string("yes", "no")=="yes">active btn-success<#else>btn-danger</#if>">Termék Admin</button>
				  <input type="hidden" name="SystemAdmin" class="systemAdmin-input"/>
				  <button type="button" class="role-btn systemAdmin-btn btn btn-primary <#if userwithroles.roleNames?seq_contains("SystemAdmin")?string("yes", "no")=="yes">active btn-success<#else>btn-danger</#if>">Admin</button>
				</div>
			</form>
		</div>
		<form id="${userwithroles.user.username}delete" class="deleteUserForm" action="<@spring.url relativeUrl="/admin/jogosultsagok/torol"/>" method="post">
			<input type="hidden" name="username-torol" value="${userwithroles.user.username}">
		</form>
		
		<div class="modal-footer">
			<button class="btn btn-warning submit-change-button" type="button"><i class="icon-white icon-pencil"></i> Változtat</button>
			<#if userwithroles.user.enabled!=false>
				<button class="btn btn-danger submit-ban-button" type="button"><i class="icon-white icon-remove"></i> Letílt</button>
			<#else>
				<button class="btn btn-success submit-ban-button" type="button"><i class="icon-white icon-ok"></i> Engedélyez</button>
			</#if>
		</div>
	</div>	
</div>
</#macro>

<#macro updateTrainingTypeDialog>
<div id="updateTrainingTypeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Új edzés típus</h3>
  </div>
  <div class="modal-body">
		<form class="form-horizontal" action="<@spring.url relativeUrl="/raktar/edzestipus/ujedzestipus"/>" method="post" accept-charset="UTF-8">
			<input type="hidden" id="trainerId" name="trainerId"/>
			<div class="control-group">
				<label class="control-label">Edző</label>
				<div class="controls">
					<input type="text" id="trainerName" name="trainerName" placeholder="Edző" disabled/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="detail">Edzés leírása</label>
				<div class="controls">
					<input type="text" id="detail" name="detail" placeholder="Edzés leírása"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="price">Ár</label>
				<div class="controls">
					<input type="number" id="price" name="price" required="required" value="1" min="1" placeholder="Edzés típus ára"/>
				</div>
			</div>
			<div class="modal-footer">
				<div class="control-group">
					<div class="controls">
						<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Bezár</button>
						<button id="submit-btn" class="btn btn-success" type ="submit">Hozzáadás</button>
					</div>
				</div>
			</div>
		</form>
  </div>
</div>
</#macro>

<#macro updateProductDialog>
<div id="updateProductModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Új termék</h3>
    <@errorAlert "new-product-inputs" "Töltsön ki minden mezőt!" />
  </div>
  <div class="modal-body">
		<form class="form-horizontal" action="<@spring.url relativeUrl="/raktar/termek/termekmodositas"/>" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
			<input type="hidden" id="productId" name="productId">
			<div class="control-group">
				<label class="control-label" for="name">Termék név</label>
				<div class="controls">
					<input type="text" id="name" class="input-check" name="name" placeholder="Termék neve">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="details">Leírás</label>
				<div class="controls">
					<input type="text" id="details" class="input-check" name="details" placeholder="Termékhez kapcsolódó leírás">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="manufacturer">Gyártó</label>
				<div class="controls">
					<input type="text" id="manufacturer" class="input-check" name="manufacturer" placeholder="Termék gyártója">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="price">Ár</label>
				<div class="controls">
					<input type="number" id="price" class="input-check" name="price" requested="requested" min="1" value="1" placeholder="Termék ára">
				</div>
			</div>
			<div class="control-group">
					<@bootstrapFileUpload />
			</div>
			<div class="modal-footer">
				<div class="control-group">
					<div class="controls">
						<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Bezár</button>
						<button id="update-product-button" class="btn btn-success" type="submit">Hozzáadás</button>
					</div>
				</div>
			</div>
		</form>
  </div>
</div>
</#macro>