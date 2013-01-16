<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Bérletek">
	
		<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
			<table class="table span6">
			<thead>
				<tr>
					<th><span>Bérlet típusa</span></th>
					<th><span>Bérlet ára</span></th>
					<th><span>Kezdeti érvényesség</span></th>
				</tr>
			</thead>
			<tbody>
					<tr>
						<td>1 alkalmas</td>
						<td>1499</td>
						<td>Első belépéstől 1 hónap.</td>
						<td>
							<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
								<input type="hidden" name="membershipType" value="1TimeTicket" />
								<button value="1times" type="submit" class="btn btn-primary">Kosárba</button>
								<input type="hidden" name="datepicker" value="10TimeTicket" style="display:none;"/>
							</form>
						</td>
					</tr>
					<tr>
						<td><span>10 alkalmas</span></td>
						<td>12990</td>
						<td>Első belépéstől 3 hónap.</td>
						<td>
							<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
								<input type="hidden" name="membershipType" value="10TimeTicket" />
								<button value="10times" type="submit" class="btn btn-primary">Kosárba</button>
								<input type="hidden" name="datepicker" value="10TimeTicket" style="display:none;"/>
							</form>
						</td>
					</tr>
					<tr>
						<td>Havi bérlet</td>
						<td>18900</td>
						<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
							<td><input class="datepicker" name="datepicker" type="text" placeholder="Bérlet kezdő időponjta"><br/></td>
							<td>
								<input type="hidden" name="membershipType" value="MonthlyTicket" />
								<button value="monthly" type="submit" class="btn btn-primary">Kosárba</button>
							</td>
						</form>
					</tr>
					<tr>
						<td>3 havi bérlet</td>
						<td>53900</td>
						<form action="<@spring.url relativeUrl="/berletek/ujberlet"/>" method="post">
							<td><input class="datepicker" name="datepicker" type="text" placeholder="Bérlet kezdő időponjta"><br/></td>
							<td>
								<input type="hidden" name="membershipType" value="3MonthTicket" />
								<button value="3monthly" type="submit" class="btn btn-primary">Kosárba</button>
							</td>
						</form>
					</tr>
				</tbody>
			</table>
		</form>

</@template.masterTemplate>