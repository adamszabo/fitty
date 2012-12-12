<#import "/template/master-template.ftl" as template />

<@template.masterTemplate title="Áruház">
	
<h2>Áruház</h2>

<ul class="thumbnails">
	<#list products as product>
		<li class="span4">
			<div class="thumbnail">
				<img src="300x200.png" alt="">
				<dl class="dl-horizontal">
					<dt style="font-size:17px">Termék név</dt>
				  	<dd style="font-size:17px">${product.name}</dd>
					<dt>Gyártó</dt>
				  	<dd>${product.manufacturer}</dd>
				  	<dt>Leírás</dt>
				  	<dd><div class="slimScroll">${product.details}</div></dd>
				  	<dt>Ár</dt>
				  	<dd>${product.price}</dd>
				</dl>
				<form class="form-search" action="addToCart" method="post" style="text-align:center">
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
	
<ul class="pager">
  <li><a href="#">Previous</a></li>
  <li><a href="#">Next</a></li>
</ul>
</@template.masterTemplate>