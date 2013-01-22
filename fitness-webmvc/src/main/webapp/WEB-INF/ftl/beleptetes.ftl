<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Áruház">
	<form class="form-search">
		<div class="drop-down-btn-group">
			<select id="searchTypeSelect">
				<option value="user-name">Név </option>
	            <option value="user-username">Felhasználó név </option>
	            <option value="user-id">Azonosító </option>
	            <option value="user-email">Email </option>
	         </select>
      	</div>
      	
		<input type="text" class="input-medium search-query">
		<button type="submit" class="btn">Keresés</button>
	</form>
</@template.masterTemplate>
