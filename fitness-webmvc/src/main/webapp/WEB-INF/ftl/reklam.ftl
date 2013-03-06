<#import "/template/master-template.ftl" as template />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />

<@template.masterTemplate title="Kezdőlap">	

	<@tags.basketDialog "/kosar/rendel"/>
	
	<div class="hero-unit" style="margin-top:15px">
  		<h3 style="color:#2f96b4">Új reklám hozzáadása</h3>
  		<p>
			<textarea id="broadcast-input" class="span4" rows="4" maxlength="140"></textarea>
		</p>
  		<p>
    		<button class="btn btn-info" id="broadcaster" type="button">Elküld</button>
  		</p>
	</div>
	
	<@loadScripts.loadScripts />
</@template.masterTemplate>