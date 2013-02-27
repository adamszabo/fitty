<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzők">
	
<@tags.basketDialog "/aruhaz/confirmBasket"/>
<@tags.basketMergingDialog />
<@tags.errorMessage />

	<h2>Edzők</h2>
	<#if trainingTypes?exists>
		<#if (trainingTypes?size > 0) >
			<table class="table ">
				<thead>
					<tr>
						<th><span>Név</span></th>
						<th><span>Edzés típusa, leírása</span></th>
						<th><span>Ár (HUF)</span></th>
					</tr>
				</thead>
				<tbody>
						<#list trainingTypes as trainingType>
							<tr>
								<td>${trainingType.trainer.fullName}</td>
								<td>${trainingType.detail}</td>
								<td>${trainingType.price}</td>							
							</tr>
						</#list>
				</tbody>
			</table>
			<p><a class="btn btn-mini btn-inverse" href="<@spring.url relativeUrl="/edzesek"/>">Edzők naptárai</a></p>
		<#else>
			<div class="alert alert-warning">
				<span>Nincs edző a rendszerben.</span>
			</div>
		</#if>
	</#if>
	
	<@loadScripts.loadScripts />

</@template.masterTemplate>