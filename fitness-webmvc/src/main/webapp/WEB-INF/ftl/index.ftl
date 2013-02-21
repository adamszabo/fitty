<#import "/template/master-template.ftl" as template />
<#import "/tag/loadScripts.ftl" as loadScripts />

<@template.masterTemplate title="Kezdőlap">			
	<h2>Kezdőlap</h2>
	<h2>Server time: ${serverTime}</h2>
	
	<ul>
	<#list users as user>
		<li>${user.username}</li>
	</#list>
	</ul>
	Users logged in: ${size}
	
	<@loadScripts.loadScripts />
	
</@template.masterTemplate>