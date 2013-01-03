<#import "/spring.ftl" as spring />
<#assign springTags=JspTaglibs["http://www.springframework.org/tags"] />

<#macro login>
	<div style="padding-top:4px;">
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

<#macro errorAlert id message>
	<div id="${id}" class="alert alert-error" style="display:none;">
	  <!--button type="button" class="close" data-dismiss="alert">&times;</button-->
	  <strong>Hiba! </strong><span>${message}</span>
	</div>
</#macro>
<#macro basketDialog>
	<#if basket?exists>
	<div id="basketModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Kosár tartalma</h3>
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
					<th>Össz ár</th>
				</tr>
			</thead>
			<tbody>
				<#assign iterate = 0>
				<#assign sum = 0>
				<#list basket["orderItems"] as item>
					<#assign iterate = iterate +1>
					<#assign sum = sum + item["quantity"] * item["product"]["price"]>
					<tr>
						<td>${iterate}</td>
						<td>${item.product.name}</td>
						<td>${item.product.manufacturer}</td>
						<td>${item.product.price}</td>
						<td>${item.quantity}</td>
						<td>${item.quantity * item.product.price}</td>
					</tr>
				</#list>
						<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>${sum}</td>
					</tr>
			</tbody>
		</table>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">Bezárás</button>
	    <button class="btn btn-primary">Megrendelés</button>
	  </div>
	</div>
	</#if>
</#macro>

<#macro newProductDialog>
<div id="newProductModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Új termék</h3>
  </div>
  <div class="modal-body">
  		<form class="form-horizontal" action="<@spring.url relativeUrl="/admin/ujtermek"/>" method="post" accept-charset="UTF-8">
		  <div class="control-group">
		    <label class="control-label" for="name">Termék név</label>
		    <div class="controls">
		      <input type="text" id="name" name="name" placeholder="Termék neve">
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="details">Leírás</label>
		    <div class="controls">
		      <input type="text" id="details" name="details" placeholder="Termékhez kapcsolódó leírás">
		    </div>
		  </div>
  		  <div class="control-group">
		    <label class="control-label" for="manufacturer">Gyártó</label>
		    <div class="controls">
		      <input type="text" id="manufacturer" name="manufacturer" placeholder="Termék gyártója">
		    </div>
		  </div>
  		  <div class="control-group">
		    <label class="control-label" for="price">Ár</label>
		    <div class="controls">
		      <input type="number" id="price" name="price" placeholder="Termék ára">
		    </div>
		  </div>
		  <div class="modal-footer">
		  	<div class="control-group">
		  		<div class="controls">
			    	<button class="btn" data-dismiss="modal" aria-hidden="true">Bezár</button>
			    	<button class="btn btn-primary" type ="submit">Hozzáadás</button>
		 		</div>
		 	</div>
		 </div>
		  
		</form>
  </div>
</div>
</#macro>

<#macro missingElements>
	<#if missingProduct?exists>
	<div id="missesModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Az alábbi termékből nem taláható meg megfelelő mennyiség a raktárban:</h3>
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
						<td>${item.name}</td>
						<td>${item.manufacturer}</td>
						<td>${item.price}</td>
					</tr>
				</#list>
			</tbody>
		</table>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">Bezárás</button>
	  </div>
	</div>
	</#if>
</#macro>