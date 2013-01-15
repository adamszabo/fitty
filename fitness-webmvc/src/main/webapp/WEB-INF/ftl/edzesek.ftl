<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">

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