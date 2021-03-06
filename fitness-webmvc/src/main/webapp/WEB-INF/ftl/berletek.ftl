<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Bérletek">
	
<@tags.basketDialog "/kosar/rendel"/>
<@tags.basketMergingDialog />
<@tags.errorMessage />

<#if membershipTypes?size != 0>
	<ul class="nav nav-pills">
	  <#if missingProduct?exists>
		<li>
		  	<a href="#missesModal" data-toggle="modal">Hiányzó termékek</a>
		</li>
	  </#if>
	</ul>
	<div id="membershipNotValid">
	
	</div>
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
							<button type="submit" class="membership-submit-button btn btn-primary">Kosárba</button>
						</td>
				</tr>
			</form>						
		</#list>
		</tbody>
	</table>
<#else>
	<div class="alert alert-warning">
		<span>Nincs bérlet a rendszerben.</span>
	</div>
</#if>

<@loadScripts.loadScripts />
<!-- berletek -->
<script src="<@spring.url relativeUrl="/resources/js/forPages/berletek.js"/>"></script>

</@template.masterTemplate>