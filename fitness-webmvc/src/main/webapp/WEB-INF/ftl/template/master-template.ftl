<#import "/template/template-loadScripts.ftl" as loadScripts />
<#import "/template/template-head.ftl" as head />
<#import "/template/template-headerLayout.ftl" as header/>

<#macro masterTemplate title="Zsír-Szabó Fitness">

<!DOCTYPE html>
<html lang="hu">

<@head.templateHead title="${title}"/>

<body>
 
 	<@header.headerLayout/>
    
	<div class="container">
		<#nested/>			
	</div>

    <@loadScripts.loadScripts />
	
  </body>
</html>

</#macro>