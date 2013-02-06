<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">
<input type="hidden" name="defaultUrl" id="defaultUrl" value="<@spring.url relativeUrl="/"/>"/>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span2">
			<form id="new-active-trainer-form" action="<@spring.url relativeUrl="/edzesek/edzo/naptar"/>" method="post">
				<ul class="nav nav-list">
					<li class="nav-header">Edzők</li>
					<#if trainers?exists>
						<#list trainers as trainer>
							<li <#if activeTrainer?exists><#if activeTrainer = trainer.username>class="active"</#if></#if>><a data-username="${trainer.username}" class="new-trainer-name">${trainer.fullName}</a></li>
						</#list>
					</#if>
				</ul>
				<input type="hidden" name="new-active-trainer-input" id="new-active-trainer-input">
			</form>
		</div>
		
		<div class="fitness-calendar span10">
	
		</div>
		</from>
		
	</div>
</div>
</@template.masterTemplate>