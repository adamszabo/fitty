<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Áruház">

<#if message?exists>
	<div class="alert alert-block">
  	<button type="button" class="close" data-dismiss="alert">x</button>
  		<h4>Hiba!</h4>
		${message}
	</div>
</#if>


<ul class="nav nav-pills">
<#if basket?exists>
	<li class="dropdown">
		  <a class="dropdown-toggle" data-toggle="dropdown" href="#">
		    Kosár
		    <span class="caret"></span>
		  </a>
		  <ul class="dropdown-menu">
			<li><a href="#basketModal" data-toggle="modal">Kosár tartalma</a></li>
			<li><a data-toggle="modal" href="<@spring.url relativeUrl="/aruhaz/${pageNumber}/confirmBasket"/>">Megrendelés</a></li>
			<li><a data-toggle="modal" href="<@spring.url relativeUrl="/aruhaz/${pageNumber}/deleteBasket"/>">Kosár törlése</a></li>
		  </ul>
	</li>
  </#if>
  <#if missingProduct?exists>
	<li>
	  	<a href="#missesModal" data-toggle="modal">Hiányzó termékek</a>
	</li>
  </#if>
</ul>


<div id="page-container" class="wrapper wrapper-home" style="padding-top: 1%;">
<ul class="thumbnails">
	<#list products as product>
		<li class="span4">
			<div class="thumbnail">
				<#if product.productImage??>
					<img src="<@spring.url relativeUrl="/imageController/${product.productImage.id}"/>" alt="${product.name}" style="max-width: 300px;max-height: 200px;">
				<#else>
					<img src="<@spring.url relativeUrl="/resources/img/300x200.png"/>" alt="${product.name}" style="max-width: 300px;max-height: 200px;">
				</#if>
				<dl class="dl-horizontal">
					<dt style="font-size:17px">Termék név</dt>
				  	<dd style="font-size:17px">${product.name}</dd>
					<dt>Gyártó</dt>
				  	<dd>${product.manufacturer}</dd>
				  	<dt>Leírás</dt>
				  	<dd><div class="detailSlimScroll">${product.details}</div></dd>
				  	<dt>Ár</dt>
				  	<dd>${product.price}</dd>
				</dl>
				<form class="form-search" action="<@spring.url relativeUrl="/aruhaz/${pageNumber}/addToCart"/>" method="post" style="text-align:center">
				  <div class="input-append">
				    <input name="quantity" type="number" class="span1 search-query" min="0" max="20" step="1" value="1">
				   	<input name="productId" type="hidden" value="${product.id}"> 
				    <button type="submit" class="btn">Kosárba</button>
				  </div>
				</form>
			</div>
		 </li>
	 </#list>
</ul>
	
<@tags.missingElements />

<@tags.basketDialog />

<ul class="pager">
  <li id="previousPage"><a href="<@spring.url relativeUrl="/aruhaz/${pageNumber-1}"/>">&larr; Előző</a></li>
  <li><span id="actualPageNumber" class="badge badge-info" style="color:#08C;">${pageNumber}</span></li>
  <li id="nextPage"><a href="<@spring.url relativeUrl="/aruhaz/${pageNumber+1}"/>">Következő &rarr;</a></li>
</ul>

</div>
</@template.masterTemplate>