<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Bérletek">
	
		<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
			<input id="datepicker" name="datepicker" type="text" value="12-02-2012"><br/>
			<table class="table span5">
			<thead>
				<tr>
					<th>Bérlet típusa</th>
					<th>Bérlet ára</th>
				</tr>
			</thead>
			<tbody>
					<tr>
						<td>Havi bérlet</td>
						<td>18900</td>
						<td><input name="membership" value="monthly" type="submit" class="trainer-select-radio-button btn btn-primary">Kiválaszt</button></td>
					</tr>
					<tr>
						<td>10 alkalmas</td>
						<td>12990</td>
						<td><input name="membership" value="10times" type="submit" class="trainer-select-radio-button btn btn-primary">Kiválaszt</button></td>
					</tr>
					<tr>
						<td>3 havi bérlet</td>
						<td>53900</td>
						<td><input name="membership" value="3monthly" type="submit" class="trainer-select-radio-button btn btn-primary">Kiválaszt</button></td>
					</tr>
				</tbody>
			</table>
		</form>

</@template.masterTemplate>