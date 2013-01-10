<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Jogosultságok">
	<@tags.confirmDialog "deleteUser" "Biztos, hogy törli a felhasználót?" "Törlés" />
	<table class="table">
		<thead>
			<tr>
				<th class="span2">User név</th>
				<th class="span2">Kliens</th>
				<th class="span2">Edző</th>
				<th class="span2">Recepciós</th>
				<th class="span2">Termék adminisztrátor</th>
				<th class="span2">Adminisztrátor</th>
				<th class="span2"></th>
			</tr>
		</thead>
		<tbody>
			<#list roles?keys as key>
					<tr>
						<form id="${key}" action="<@spring.url relativeUrl="/admin/jogosultsagok/valtoztat"/>" method="post">
							<input type="hidden" name="username" value="${key}">
							<td>${key}</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="client" name="Client" <#if roles[key]?seq_contains("Client")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="trainer" name="Trainer" <#if roles[key]?seq_contains("Trainer")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="receptionist" name="Receptionist" <#if roles[key]?seq_contains("Receptionist")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="productAdmin" name="ProductAdmin"<#if roles[key]?seq_contains("ProductAdmin")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="systemAdmin" name="SystemAdmin" <#if roles[key]?seq_contains("SystemAdmin")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td><button class="btn btn-primary" type="submit">Változtat</button></td>
						</form>
						<form id="${key}delete" class="deleteUserForm" action="<@spring.url relativeUrl="/admin/jogosultsagok/torol"/>" method="post">
							<input type="hidden" name="username-torol" value="${key}">
							<td><button class="btn btn-danger" type="submit"><i class="icon-trash"></i> Töröl</button></td>
						</form>
					</tr>
			</#list>
		</tbody>
	</table>
</@template.masterTemplate>