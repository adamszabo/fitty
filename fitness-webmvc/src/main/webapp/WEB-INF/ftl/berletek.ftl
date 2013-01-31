<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Bérletek">
	
<@tags.errorMessage />
<@tags.basketDialog "/berletek/megrendel"/>
	
<ul class="nav nav-pills">
  <#if missingProduct?exists>
	<li>
	  	<a href="#missesModal" data-toggle="modal">Hiányzó termékek</a>
	</li>
  </#if>
</ul>
<table class="table span6">
<thead>
	<tr>
		<th><span>Bérlet típusa</span></th>
		<th><span>Bérlet ára</span></th>
		<th><span>Kezdeti érvényesség</span></th>
	</tr>
</thead>
<tbody>
	<#list membershipTypes as membership>
		<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
			<tr>
				<td>${membership.detail}</td>
				<td>${membership.price}</td>
					<td>
						<#if membership.maxNumberOfEntries == 0>
							<input class="datepicker" name="datepicker" type="text" placeholder="Bérlet kezdő időponjta"/>
						</#if>
					</td>							
					<td>
						<input type="hidden" name="membershipId" value="${membership.id}" />
						<button type="submit" class="btn btn-primary">Kosárba</button>
					</td>
			</tr>
		</form>						
	</#list>
	</tbody>
</table>
</@template.masterTemplate>