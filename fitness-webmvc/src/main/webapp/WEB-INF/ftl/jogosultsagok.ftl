<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />

<@template.masterTemplate title="Jogosultságok">
	<@tags.confirmDialog "deleteUser" "Biztos, hogy törli a felhasználót?" "Törlés" />
	<table class="table">
		<thead>
			<tr>
				<th class="span2">Felhasználó név</th>
				<th class="span2">Kliens</th>
				<th class="span2">Edző</th>
				<th class="span2">Recepciós</th>
				<th class="span2">Termék adminisztrátor</th>
				<th class="span2">Adminisztrátor</th>
				<th class="span2"></th>
			</tr>
		</thead>
		<tbody>
			<#list UsersWithRoles as userwithroles>
					<tr>
						<form id="${userwithroles.user.username}" action="<@spring.url relativeUrl="/admin/jogosultsagok/valtoztat"/>" method="post">
							<input type="hidden" name="username" value="${userwithroles.user.username}">
							<td>${userwithroles.user.username}</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="client" name="Client" <#if userwithroles.roleNames?seq_contains("Client")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="trainer" name="Trainer" <#if userwithroles.roleNames?seq_contains("Trainer")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="receptionist" name="Recepcionist" <#if userwithroles.roleNames?seq_contains("Recepcionist")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="productAdmin" name="ProductAdmin"<#if userwithroles.roleNames?seq_contains("ProductAdmin")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td>
								<label class="checkbox inline">
								  <input type="checkbox" id="systemAdmin" name="SystemAdmin" <#if userwithroles.roleNames?seq_contains("SystemAdmin")?string("yes", "no")=="yes">checked</#if>>
								</label>
							</td>
							<td><button class="btn btn-primary" type="submit"><i class="icon-repeat icon-white"></i> Változtat</button></td>
						</form>
						<form id="${userwithroles.user.username}delete" class="deleteUserForm" action="<@spring.url relativeUrl="/admin/jogosultsagok/torol"/>" method="post">
							<input type="hidden" name="username-torol" value="${userwithroles.user.username}">
							<#if userwithroles.user.enabled!=false>
								<td><button class="btn btn-danger" type="submit"><i class="icon-white icon-remove"></i> Letílt</button></td>
							<#else>
								<td><button class="btn btn-success" type="submit"><i class="icon-white icon-ok"></i> Engedélyez</button></td>
							</#if>
						</form>
					</tr>
			</#list>
		</tbody>
	</table>
</@template.masterTemplate>