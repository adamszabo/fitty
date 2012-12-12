<#macro login>
	<div style="padding-top:4px">
    	<form action="./bejelentkezes" class="form-inline" style="margin-bottom:0px" method="post">
		  <fieldset>
		    <input name="userName" type="text" placeholder="Felhasználó név">
		    <input name="password" type="text" placeholder="Jelszó">
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
