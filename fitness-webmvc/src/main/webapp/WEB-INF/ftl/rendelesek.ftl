<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Rendelések">
	
<@tags.basketDialog "/kosar/rendel"/>
<@tags.basketMergingDialog />
<@tags.errorMessage />

	<ul class="nav nav-tabs">
	  <li <#if baskets?exists>class="active"</#if>>
	    <a href="<@spring.url relativeUrl="/rendelesek/atveheto"/>">Átvehető rendelések</a>
	  </li>
	  <li <#if deliveredBaskets?exists>class="active"</#if>>
	  	<a href="<@spring.url relativeUrl="/rendelesek/teljesitett"/>">Teljesített rendelések</a>
	  </li>
	</ul>
	
	<#if baskets??>
		<#if (baskets?size > 0) >
			<@basketsTable baskets/>
		<#else>
			<div class="alert alert-error">
				<span>Nem található rendelés az adatbázisban!</span>
			</div>
		</#if>
	<#elseif deliveredBaskets??>
		<#if (deliveredBaskets?size > 0) >
			<@basketsTable deliveredBaskets/>
		<#else>
			<div class="alert alert-error">
				<span>Nem található teljesített rendelés az adatbázisban!</span>
			</div>
		</#if>
	<#else>
		<div class="alert alert-error">
			<span>Nem található rendelés az adatbázisban!</span>
		</div>
	</#if>	
	
	<@loadScripts.loadScripts />

</@template.masterTemplate>

<#macro basketsTable baskets>
	<#if (baskets?size>0) >
			<table class="table table-hover table-bordered thiner-lines">
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
									<a href="#${basket.id}-modal" class="btn btn-small btn-primary" data-toggle="modal">Rendelt elemek</a>
								</td>
							</form>
						</tr>
						<@basketModal basket />
					</#list>
				</tbody>
			</table>
	<#else>
		<h5>Nem tartozik teljesítetlen megrendelés a személyhez.</h5>
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
						${training.trainer.username} ${training.date?date} (ID:${training.id})
						<div style="float:right;">
							5000<b> HUF</b>
						</div>
					</div>
					<#assign sum = sum + 5000>
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
						<button class="btn btn-success" data-dismiss="modal" aria-hidden="true">Bezár</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</#macro>