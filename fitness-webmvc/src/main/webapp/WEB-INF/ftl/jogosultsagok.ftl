<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Jogosultságok">

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
				<form id="${key}" action="<@spring.url relativeUrl="/admin/jogosultsagok/valtoztat"/>" method="post">
					<input type="hidden" name="username" value="${key}">
					<tr>
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
							  <input type="checkbox" id="systemAdmin" name="ProductAdmin"<#if roles[key]?seq_contains("ProductAdmin")?string("yes", "no")=="yes">checked</#if>>
							</label>
						</td>
						<td>
							<label class="checkbox inline">
							  <input type="checkbox" id="admin" name="Admin" <#if roles[key]?seq_contains("Admin")?string("yes", "no")=="yes">checked</#if>>
							</label>
						</td>
						<td><button class="btn" type="submit">Változtat</button></td>
					</tr>
				</form>
			</#list>
		</tbody>
	</table>
</@template.masterTemplate>