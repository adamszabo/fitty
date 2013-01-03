<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Raktár">

<button class="btn" type="button" data-toggle="modal" href="#newProductModal">Új termék hozzáadása</button>

<@tags.newProductDialog/>

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
					<form class="form-search" action="<@spring.url relativeUrl="/admin/ujmennyiseg"/>" method="post" style="text-align:left">
						<div class="input-append span2">
		  					<input name="quantity" class="span1 search-query" type="number">
		  					<input name="productId" type="hidden" value="${product.product.id}">
		  					<button class="btn" type="submit">Raktárba</button>
					  	</div>
					</form>
				</td>
				<td>
					<a href="<@spring.url relativeUrl="/admin/termektorles/${product.product.id}"/>" class="btn btn-danger"><i class="icon-white icon-remove"></i></a>
				</td>
			</tr>
		</#list>
	</tbody>

</table>

</@template.masterTemplate>