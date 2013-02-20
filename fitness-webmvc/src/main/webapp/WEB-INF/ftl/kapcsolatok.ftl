<#import "/template/master-template.ftl" as template />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />

<@template.masterTemplate title="Kapcsolatok">
	<@tags.basketDialog "/aruhaz/confirmBasket"/>
	
	<div class="row contact-page">
		<div class="hero-unit">
			<h2>Maradj kapcsolatban!</h2>
			<p class="tagline">Bármilyen kérdéssel kapcsolatban keress fel minket az alábbi elérhetőségeken.</p>
		</div> 
		<section class="span6">
			<div class="control-group">
				<label class="control-label">
					<i class="icon-envelope"></i> Cím
				</label>
				<div class="controls controls-row">Zsír Szabó Fitnesz klub - 1082 Budapest Futó utca 1</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					<i class="icon-info-sign"></i> E-mail
				</label>
				<div class="controls controls-row">info@zsirszabo.com</div>
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
</@template.masterTemplate>