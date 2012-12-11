<#macro login>
	<div style="padding-top:4px">
    	<form class="form-inline" style="margin-bottom:0px">
		  <fieldset>
		    <input type="text" placeholder="Felhasználó név">
		    <input type="text" placeholder="Jelszó">
		    <button type="submit" class="btn btn-medium btn-primary">OK</button>
		  </fieldset>
		</form>
    </div>
</#macro>

<#macro url path name>
	<script type="text/javascript">
		var url = location.pathname;
		url = url.split('/');
		url = url[1];
		document.write('<li><a href="/' + url +'/${path}">${name}</a></li>');
	</script>
</#macro>
