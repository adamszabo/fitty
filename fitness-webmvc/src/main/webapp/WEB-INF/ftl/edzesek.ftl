<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">
	<#list trainers as trainer>
		${trainer.fullName}
		<br/>
	</#list>
</@template.masterTemplate>