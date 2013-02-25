<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />
<@template.masterTemplate title="Edzések">
<input type="hidden" name="defaultUrl" id="defaultUrl" value="<@spring.url relativeUrl="/"/>"/>

<@tags.errorMessage />
<@tags.basketDialog "/aruhaz/confirmBasket"/>
<@tags.basketMergingDialog />

	<#if reservedMessage?exists>
		<div class="alert alert-block">
	  	<button type="button" class="close" data-dismiss="alert">x</button>
	  		<h4>Hiba!</h4>
			${reservedMessage} <#if reservedTraining??><a class="btn btn-small btn-warning" href="#reservedModal" data-toggle="modal">Foglalt időpontok</a></#if>
		</div>
	</#if>
	
	<@tags.reservedElements />

<div class="container-fluid">
	<div class="row-fluid">
			<#if trainingTypes?exists>
				<#if (trainingTypes?size > 0) >
					<div id="trainers-selector" class="span2">
						<ul class="nav nav-list">
							<li class="nav-header"><h4>Edzők</h4></li>
								<#list trainingTypes as trainingType>
									<li class="trainer-name-li" data-username="${trainingType.trainer.username}" data-price="${trainingType.price}"><a>${trainingType.trainer.fullName}</a></li>
								</#list>
						</ul>
					</div>
				<#else>
					<div class="alert alert-warning">
						<span>Nincs edző a rendszerben.</span>
					</div>
				</#if>
			</#if>
		
		<div class="fitness-calendar span10"></div>
	</div>
</div>

<@loadScripts.loadScripts />
<!-- edzesek -->
<script src="<@spring.url relativeUrl="/resources/js/fitness-calendar.js"/>"></script>
<script src="<@spring.url relativeUrl="/resources/js/forPages/edzesek.js"/>"></script>

</@template.masterTemplate>