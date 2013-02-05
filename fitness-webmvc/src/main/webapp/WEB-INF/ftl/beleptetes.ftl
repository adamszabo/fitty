<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Áruház">
	<#if checkedInUserName?? >
		<div class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<span>${checkedInUserName}</span> sikeresen beléptetve!
		</div>
	</#if>
	
	<div class="search-bar">
	<form class="form-search" action="<@spring.url relativeUrl="/beleptetes/kereses"/>" method="post">
		<div class="drop-down-btn-group">
			<select id="searchType" name="searchType">
				<option value="fullName">Név </option>
	            <option value="username">Felhasználó név </option>
	            <option value="email">Email </option>
	         </select>
      	</div>
      	
		<input type="text" class="input-medium search-query" name="searchInput" id="searchInput" />
		<button type="submit" class="btn btn-inverse"><i class="icon-white icon-search"></i> Keresés</button>
	</form>
	</div>
	
	<table class="table">
			<thead>
				<tr>
					<th>Azonosító</th><th>Név</th><th>Felhasználó név</th>
					<th>Email</th><th>Mobil</th><th></th>
				</tr>
			</thead>
			<tbody>
				<#if searchResult??>
					<#if (searchResult?size < 1) >
						<tr>
							<td colspan="6">Nem található személy a megadott keresési feltételre.</td>
						</tr>
					</#if>
					<#list searchResult as item>
							<tr id="${item.user.username}-tr">
								<td>${item.user.id}</td>
								<td>${item.user.fullName}</td>
								<td>${item.user.username}</td>
								<td>${item.user.email}</td>
								<td>${item.user.mobile}</td>
								<td>
									<button id="${item.user.username}" class="btn btn-primary details-button" type="button"><i class="icon-white icon-chevron-down"></i></button>
								</td>
							</tr>
							<tr id="${item.user.username}-memberships-tr" style="display:none">
								<@memberShipsTableWithCheckIn item.memberships item.user.fullName/>
							</tr>
							<tr id="${item.user.username}-baskets-tr" style="display:none">
								<@basketsTableWithCheckOut item.baskets />
							</tr>
					</#list>
				<#else>
					<tr>
						<td colspan="6">Nem található személy a megadott keresési feltételre.</td>
					</tr>
				</#if>
			</tbody>
		</table>
</@template.masterTemplate>


<#macro memberShipsTableWithCheckIn memberships fullName>
	<#if (memberships?size>0) >
		<td colspan="6">
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th colspan="6">Bérletek</th>
					</tr>
					<tr>
						<th>Azonosító</th>
						<th>Típus</th>
						<th>Belépések száma</th>
						<th>Ára</th>
						<th>Művelet</th>
					</tr>
				</thead>
				<tbody>
					<#list memberships as membership>
						<tr>
							<form class="form-search" action="<@spring.url relativeUrl="/beleptetes/leptet"/>" method="post">
								<input type="hidden" value="${fullName}" name="userFullNameHiddenField"/>
								<input type="hidden" value="${membership.id}" name="membershipIdHiddenField"/>
								<td>${membership.id}</td>
								<td>${membership.type}</td>
								<td>${membership.numberOfEntries}</td>
								<td>${membership.price}</td>
								<td><button class="btn btn-success" type="submit">Beléptet</button></td>
							</form>
						</tr>
					</#list>
				</tbody>
			</table>
		</td>
	<#else>
		<td colspan="6">
			<h5>Nincs bérlet a személyhez.</h5>
		</td>
	</#if>
</#macro>

<#macro basketsTableWithCheckOut baskets>
	<#if (baskets?size>0) >
		<td colspan="6">
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th colspan="6">Rendelések</th>
					</tr>
					<tr>
						<th>Azonosító</th>
						<th>Termékek száma</th>
						<th>Bérletek száma</th>
						<th>Edzések száma</th>
						<th>Időpont:</th>
						<th>Művelet</th>
					</tr>
				</thead>
				<tbody>
					<#list baskets as basket>
						<tr id="${basket.id}-orders-tr">
							<form id="${basket.id}-orders-form" class="basket-orders-form" action="<@spring.url relativeUrl="/"/>" method="post">
								<input type="hidden" value="${basket.id}" name="basketIdHiddenField"/>
								<td>${basket.id}</td>
								<td>${basket.orderItems?size}</td>
								<td>${basket.memberships?size}</td>
								<td>${basket.trainings?size}</td>
								<td>${basket.creationDate?date}</td>
								<td>
									<a href="#${basket.id}-modal" class="btn btn-success" data-toggle="modal">Teljesítés</a>
									<a href="#${basket.id}-basketStornoConfirmDialog" class="btn btn-danger" data-toggle="modal" >Storno</a>
								</td>
							<@tags.confirmDialog "${basket.id}-basketStorno" "Biztos, hogy törli a (ID : ${basket.id}) rendelést?" "Storno" />
							</form>
						</tr>
						<@basketModal basket />
					</#list>
				</tbody>
			</table>
		</td>
	<#else>
		<td colspan="6">
			<h5>Nem tartozik teljesítetlen megrendelés a személyhez.</h5>
		</td>
	</#if>
</#macro>

<#macro basketModal basket>
<div id="${basket.id}-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<form id="${basket.id}-modal-form" class="basket-order-form" action="<@spring.url relativeUrl="/"/>" method="post">
		<div class="modal-header">
			<h3 id="myModalLabel">Rendelés részletei</h3>
		</div>
		<div class="modal-body">
			<h5>Termékek</h5>
			<#assign sum = 0>
			<#list basket.orderItems as orderItem>
				<div class="basketModalRow">
					${orderItem.product.name} (ID:${orderItem.id})
					<div style="float:right;">
						${orderItem.quantity} x	${orderItem.product.price} <b>HUF</b>
					</div>
				</div>
				<#assign sum = sum + ( orderItem.quantity *  orderItem.product.price) >
			</#list>
			<h5>Bérletek</h5>
			<#list basket.memberships as membership>
				<div class="basketModalRow">
					${membership.type} (ID:${membership.id})
					<div style="float:right;">
						${membership.price} <b>HUF</b>
					</div>
				</div>
				<#assign sum = sum + membership.price>
			</#list>
			<h5>Edzések</h5>
			<#list basket.trainings as training>
				<div class="basketModalRow">
					${training.id}
					${training.trainer.fullName}
					${training.price}
				</div>
			</#list>
			<div class="basketModalRow">
				<b>Összesen:</b>
				<div style="float:right;">
					${sum} <b>HUF</b>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="control-group">
				<div class="controls">
					<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Mégse</button>
					<button class="btn btn-success" type ="submit">Teljesít</button>
				</div>
			</div>
		</div>
	</form>
</div>
</#macro>
