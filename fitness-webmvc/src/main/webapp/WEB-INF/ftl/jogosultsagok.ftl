<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />

<@template.masterTemplate title="Jogosultságok">
	<@tags.basketDialog "/aruhaz/confirmBasket"/>
	<table class="table">
		<thead>
			<tr>
				<th class="span2">Teljes név</th>
				<th class="span2">Felhasználó név</th>
				<th class="span2">Kliens</th>
				<th class="span2">Edző</th>
				<th class="span2">Recepciós</th>
				<th class="span2">Termék adminisztrátor</th>
				<th class="span2">Adminisztrátor</th>
				<th class="span2">Engedélyezve</th>
				<th class="span2"></th>
			</tr>
		</thead>
		<tbody>
			<#list UsersWithRoles as userwithroles>
					<tr>
						<td>${userwithroles.user.fullName}</td>
						<td>${userwithroles.user.username}</td>
						<td>
							<@checkedOrUnchecked userwithroles.roleNames?seq_contains("Client")?string("yes", "no")/>
						</td>
						<td>
							<@checkedOrUnchecked userwithroles.roleNames?seq_contains("Trainer")?string("yes", "no")/>
						</td>
						<td>
							<@checkedOrUnchecked userwithroles.roleNames?seq_contains("Recepcionist")?string("yes", "no")/>
						</td>
						<td>
							<@checkedOrUnchecked userwithroles.roleNames?seq_contains("ProductAdmin")?string("yes", "no")/>
						</td>
						<td>
							<@checkedOrUnchecked userwithroles.roleNames?seq_contains("SystemAdmin")?string("yes", "no")/>
						</td>
						<td>
							<#if userwithroles.user.enabled!=false>
								<img src="<@spring.url relativeUrl="/resources/img/checked.gif"/>">
							<#else>
								<img src="<@spring.url relativeUrl="/resources/img/unchecked.gif"/>">
							</#if>
						</td>
						<td><a href="#roleDetailsModal-${userwithroles.user.username}" data-toggle="modal" class="btn btn-primary"><i class="icon-pencil icon-white"></i> Részletek</a></td>
						<@tags.roleDetailsModal userwithroles/>	
					</tr>
			</#list>
		</tbody>
	</table>
	
	<@loadScripts.loadScripts />
	<!-- jogosultsagok -->
	<script src="<@spring.url relativeUrl="/resources/js/forPages/jogosultsagok.js"/>"></script>

</@template.masterTemplate>

<#macro checkedOrUnchecked value>
	<#if value=="yes">
		<img src="<@spring.url relativeUrl="/resources/img/checked.gif"/>">
	<#else>
		<img src="<@spring.url relativeUrl="/resources/img/unchecked.gif"/>">
	</#if>
</#macro>
