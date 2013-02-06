<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Raktár">

<ul class="nav nav-tabs">
  <li <#if productsInStore?exists>class="active"</#if>>
    <a href="<@spring.url relativeUrl="/raktar/termek"/>">Termékek</a>
  </li>
  <li <#if membershipsInStore?exists>class="active"</#if>>
  	<a href="<@spring.url relativeUrl="/raktar/berlet"/>">Bérletek</a>
  </li>
</ul>

<#if productsInStore?exists>
	<button class="btn btn-primary" type="button" data-toggle="modal" href="#newProductModal">Új termék hozzáadása</button>
	
	<@tags.newProductDialog/>
	<link href="<@spring.url relativeUrl="/resources/css/bootstrap-fileupload.css"/>" rel="stylesheet" />
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Termék azonosító</th>
				<th>Név</th>
				<th>Gyártó</th>
				<th>Leírás</th>
				<th>Ár</th>
				<th>Mennyiség</th>
				<th>Érkezett mennyiség</th>
				<th>Törlés</th>
			</tr>
		</thead>
		<tbody>
			<#list productsInStore as product>
				<#if product.quantity < 1>
					<tr class="error">
				<#else>
					<tr>
				</#if>
					<td>${product.product.id}</td>
					<td>${product.product.name}</td>
					<td>${product.product.manufacturer}</td>
					<td class="span3">${product.product.details}</td>
					<td>${product.product.price}</td>
					<td>${product.quantity}</td>
					<td>
						<form class="form-search" action="<@spring.url relativeUrl="/raktar/termek/ujmennyiseg"/>" method="post" style="text-align:left">
							<div class="input-append span2">
			  					<input name="quantity" class="span1 search-query" type="number">
			  					<input name="productId" type="hidden" value="${product.product.id}">
			  					<button class="btn" type="submit">Raktárba</button>
						  	</div>
						</form>
					</td>
					<form action="<@spring.url relativeUrl="/raktar/termek/torles/${product.product.id}"/>">
						<td>
							<a href="#${product.product.id}-deleteMembershipConfirmDialog" data-toggle="modal" class="btn btn-danger deleteMembershipButton"><i class="icon-white icon-remove"></i></a>
							<@tags.confirmDialog "${product.product.id}-deleteMembership" "Biztos, hogy törli a (Azonosító : ${product.product.id}) terméket?" "Törlés" />
						</td>
					</form>
				</tr>
			</#list>
		</tbody>
	</table>
</#if>

<#if membershipsInStore?exists>
	<button class="open-newMembershipModal btn btn-primary" type="button" data-toggle="modal" href="#newMembershipModal">Új bérlet hozzáadása</button>
	
	<@tags.newMembershipDialog/>
	<@tags.updateMembershipDialog/>
	<table id="membershipTable"class="table table-hover">
		<thead>
			<tr>
				<th>Azonosító</th>
				<th>Időintervallumos?</th>
				<th>Leírás</th>
				<th>Alkalmak száma</th>
				<th>Érvényesség ideje</th>
				<th>Ár</th>
				<th>Törlés</th>
				<th>Változtatás</th>
			</tr>
		</thead>
		<tbody>
			<#list membershipsInStore as membership>
				<tr id="membership-${membership.id}">
					<td>${membership.id}</td>
					<td class="intervally">${membership.isIntervally?string("igen", "nem")}</td>
					<td class="detail">${membership.detail}</td>
					<td class="entries">${membership.maxNumberOfEntries}</td>
					<td class="expire span3">${membership.expireDateInDays}</td>
					<td class="price">${membership.price}</td>
					<form action="<@spring.url relativeUrl="/raktar/berlet/torles/${membership.id}"/>">
						<td>
							<a href="#${membership.id}-deleteMembershipConfirmDialog" data-toggle="modal" class="btn btn-danger deleteMembershipButton"><i class="icon-white icon-remove"></i></a>
							<@tags.confirmDialog "${membership.id}-deleteMembership" "Biztos, hogy törli a (Azonosító : ${membership.id}) bérletet?" "Törlés" />
						</td>
					</form>
					<td>
						<a class="open-updateMembershipModal btn btn-primary" data-id="${membership.id}"><i class="icon-white icon-wrench"></i></a>
					</td>
				</tr>
			</#list>
		</tbody>
	</table>
</#if>

</@template.masterTemplate>