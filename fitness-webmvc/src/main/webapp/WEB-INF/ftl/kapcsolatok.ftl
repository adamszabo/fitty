<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/tag/tags.ftl" as tags />

<@template.masterTemplate title="Kapcsolatok">
	<@tags.basketDialog "/kosar/rendel"/>
	
	<div class="row contact-page">
		<div class="hero-unit">
			<h2>Maradjon kapcsolatban!</h2>
			<p class="tagline">Bármilyen kérdéssel kapcsolatban, keressen minket az alábbi elérhetőségeken.</p>
		</div> 
		<section class="span6">
			<div class="control-group">
				<label class="control-label">
					<i class="icon-map-marker"></i> Cím
				</label>
				<div class="controls controls-row">Zsír Szabó Fitnesz Klub - 1082 Budapest Futó utca 1</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					<i class="icon-envelope"></i> E-mail
				</label>
				<div class="controls controls-row">info@zsirszabo.com</div>
			</div>
			<div class="control-group">
				<label class="control-label"><i class="icon-volume-up"></i> Telefon</label>
				<div class="controls controls-row">+36-30-111-1122</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					<i class="icon-cog"></i> Nyitvatartás
				</label>
				<div class="controls controls-row">Hétfő - Vasárnap : 8:00-22:00</div>
			</div>
		</section>
		<section class="span6">
			<div class="well">
				<div id="googleMap" style="width:100%;height:350px;"></div>
			<div>
		</section>
		<div id="init"></div>
	</div>
	
	<@loadScripts.loadScripts />
	<!-- kapcsolatok -->
	<script src="<@spring.url relativeUrl="/resources/js/forPages/kapcsolatok.js"/>"></script>
	<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCwSFeY_IgGT75hyovNzFeHRALmsJ9YhLQ&sensor=false"></script>
	
</@template.masterTemplate>