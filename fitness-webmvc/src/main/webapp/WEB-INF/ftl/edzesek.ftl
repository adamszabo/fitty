<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">
<input type="hidden" name="defaultUrl" id="defaultUrl" value="<@spring.url relativeUrl="/"/>"/>

<div class="container-fluid">
	<div class="row-fluid">
		<div id="trainers-selector" class="span2">
			<ul class="nav nav-list">
				<li class="nav-header"><h4>Edzők</h4></li>
				<#if trainers?exists>
					<#list trainers as trainer>
						<li class="trainer-name-li" data-username="${trainer.username}"><a>${trainer.fullName}</a></li>
					</#list>
				</#if>
			</ul>
		</div>
		
		<div class="fitness-calendar span10"></div>
	</div>
</div>
</@template.masterTemplate>