<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Adminisztráció">
<ul class="nav nav-pills">
	<li>
		<a href="<@spring.url relativeUrl="/admin/raktar"/>">Raktár</a>
	</li>
	<li>
	  	<a href="<@spring.url relativeUrl="/admin/jogosultsagok"/>">Jogosultságok</a>
	</li>
</ul>
</@template.masterTemplate>