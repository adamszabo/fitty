<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Áruház">
	<div class="search-bar">
	<form class="form-search" action="<@spring.url relativeUrl="/beleptetes/kereses"/>" method="post">
		<div class="drop-down-btn-group">
			<select id="searchType" name="searchType">
				<option value="fullName">Név </option>
	            <option value="username">Felhasználó név </option>
	            <option value="email">Email </option>
	         </select>
      	</div>
      	
		<input type="text" class="input-medium search-query" name="searchInput" id="searchInput" />
		<button type="submit" class="btn btn-inverse"><i class="icon-white icon-search"></i> Keresés</button>
	</form>
	</div>
	<table class="table table-hover">
			<thead>
				<tr>
					<th>Azonosító</th><th>Név</th><th>Felhasználó név</th>
					<th>Email</th><th>Mobil</th><th></th>
				</tr>
			</thead>
			<tbody>
				<#if searchResult??>
					<#if (searchResult?size < 1) >
						<tr>
							<td colspan="6">Nem található személy a megadott keresési feltételre.</td>
						</tr>
					</#if>
					<#list searchResult as item>
						<form id="${item.user.username}-CheckInForm" class="" action="<@spring.url relativeUrl="/"/>" method="post">
							<tr id="${item.user.username}-tr">
								<td>${item.user.id}</td>
								<td>${item.user.fullName}</td>
								<td>${item.user.username}</td>
								<td>${item.user.email}</td>
								<td>${item.user.mobile}</td>
								<td>
									<button id="${item.user.username}" class="btn btn-primary details-button" type="button"><i class="icon-white icon-chevron-down"></i></button>
								</td>
							</tr>
							<tr id="${item.user.username}-details-tr" style="display:none">
								<@memberShipsTableWithCheckIn item.memberships />
							</tr>
						</form>
					</#list>
				<#else>
					<tr>
						<td colspan="6">Nem található személy a megadott keresési feltételre.</td>
					</tr>
				</#if>
			</tbody>
		</table>
</@template.masterTemplate>


<#macro memberShipsTableWithCheckIn memberships>
	<#if (memberships?size>0) >
		<td>
			<h5>Bérletek:</h5>
		</td>
		<td colspan="5">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Azonosító</th>
						<th>Típus</th>
						<th>Belépések száma</th>
						<th>Ára</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<#list memberships as membership>
						<tr>
							<form class="form-search" action="<@spring.url relativeUrl="/beleptetes/leptet"/>" method="post">
								<td>${membership.id}</td>
								<td>${membership.type}</td>
								<td>${membership.numberOfEntries}</td>
								<td>${membership.price}</td>
								<td><button class="btn btn-success" type="button">Beléptet</button></td>
							</form>
						</tr>
					</#list>
				</tbody>
			</table>
		</td>
	<#else>
		<td colspan="6">
			<h5>Nincs bérlet a személyhez.</h5>
		</td>
	</#if>
</#macro>