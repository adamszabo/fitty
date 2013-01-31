<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">

	<iframe src="https://www.google.com/calendar/embed?showTitle=0&amp;
	showTabs=0&amp;showCalendars=0&amp;showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=1&amp;
	bgcolor=%23FFFFFF&amp;src=fitnessfitty%40gmail.com&amp;color=%232F6309&amp;ctz=Europe%2FBudapest" 
	style=" border-width:0 " width="1024" height="600" frameborder="0" scrolling="no"></iframe>

	<form action="<@spring.url relativeUrl="/edzesek/felvesz"/>" method="post">
		<input name="message" type="text">
		<button type="submit" class="btn">Felvétel a naptárba</button>
	</form>
	
	<a class="btn" href="<@spring.url relativeUrl="/edzesek/torles"/>" >Törlés</a>
	
	<br/> <br/>

	<form action="">
		<input id="datepicker" class="datepicker" type="text" value="12-02-2012"> 
		<div class="input-append bootstrap-timepicker-component">
		    <input type="text" class="timepicker-default input-small">
		    <span class="add-on">
		        <i class="icon-time"></i>
		    </span>
		</div>
		<br/>

		<table class="table span3">
			<thead>
				<tr>
					<th>Edző neve</th>
					<th>Kiválasztott edző</th>
				</tr>
			</thead>
			<tbody>
				<#list trainers as trainer>
					<tr>
						<td>${trainer.fullName}</td>
						<td><button type="button" class="trainer-select-radio-button btn btn-primary"></button></td>
					</tr>
				</#list>
			</tbody>
		</table>
		
		<table class="table span3">
			<thead>
				<tr>
					<th>Edző neve</th>
				</tr>
			</thead>
			<tbody>
				<#list trainers as trainer>
					<tr>
						<td>${trainer.fullName}</td>
						<td><button type="button" class="trainer-select-radio-button btn btn-primary">Edző kiválaszt</button></td>
					</tr>
				</#list>
			</tbody>
		</table>
	</form>
</@template.masterTemplate>