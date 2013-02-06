<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">
<input type="hidden" name="defaultUrl" id="defaultUrl" value="<@spring.url relativeUrl="/"/>"/>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span2">
			<ul class="nav nav-list">
				<li class="nav-header">Edzők</li>
				<#list trainers as trainer>
					<li <#if activeTrainer = trainer.username>class="active"</#if>><a href="<@spring.url relativeUrl="/edzesek/edzo/${trainer.username}"/>">${trainer.fullName}</a></li>
				</#list>
			</ul>
		</div>
		<div class="fitness-calendar span10">
	
		</div>
		</from>
		
	</div>
</div>
</@template.masterTemplate>