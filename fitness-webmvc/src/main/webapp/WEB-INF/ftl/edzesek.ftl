<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">
<input type="hidden" name="defaultUrl" id="defaultUrl" value="<@spring.url relativeUrl="/"/>"/>

<@tags.errorMessage />
<@tags.basketDialog "/aruhaz/confirmBasket"/>
<@tags.basketMergingDialog />

<div class="container-fluid">
	<div class="row-fluid">
			<#if trainers?exists>
				<#if (trainers?size > 0) >
					<div id="trainers-selector" class="span2">
						<ul class="nav nav-list">
							<li class="nav-header"><h4>Edzők</h4></li>
								<#list trainers as trainer>
									<li class="trainer-name-li" data-username="${trainer.username}"><a>${trainer.fullName}</a></li>
								</#list>
						</ul>
					</div>
				<#else>
					<div class="alert alert-warning">
						<span>Nincs edző a rendszerben.</span>
					</div>
				</#if>
			</#if>
		
		<div class="fitness-calendar span10"></div>
	</div>
</div>
</@template.masterTemplate>