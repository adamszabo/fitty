<#import "/template/master-template.ftl" as template />
<#import "/tag/loadScripts.ftl" as loadScripts />
<#import "/spring.ftl" as spring />
<#import "/tag/tags.ftl" as tags />

<@template.masterTemplate title="Kezdőlap">			

	<@tags.basketDialog "/kosar/rendel"/>
	
	<#if registrationMessage?exists>
		<div class="alert alert-success">
	  	<button type="button" class="close" data-dismiss="alert">x</button>
	  		<h4>Sikeres regisztráció!</h4>
			${registrationMessage}
		</div>
	</#if>
	<div class="row">
		<section class="span3">
		<h1>Csatlakozz most!</h1>
		<p>
				Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus.
				</p> 
		</section>
		<section class="span9">
			<div id="myCarousel" class="carousel slide">
			  <!-- Carousel items -->
			  <div class="carousel-inner">
			    <div class="active item">
			    	<img src="/fitness-webmvc/resources/img/tennis.jpg">
			    	<div class="carousel-caption">
		            	<h4>First Thumbnail label</h4>
		                <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
		            </div>
			    </div>
			    <div class="item">
			    	<img src="/fitness-webmvc/resources/img/running.jpg">
			    	<div class="carousel-caption">
		            	<h4>First Thumbnail label</h4>
		                <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
		            </div>
			    </div>
			    <div class="item">
			    	<img src="/fitness-webmvc/resources/img/pool.jpg">
			    	<div class="carousel-caption">
		            	<h4>First Thumbnail label</h4>
		                <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
		            </div>
			    </div>
			  </div>
			  <!-- Carousel nav -->
			  <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			  <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
			</div>
			<div class="row">
				<div class="span3">
				<h2>Termékek</h2><p>
				Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus.</p> 
				</div>
				<div class="span3">
				<h2>Edzések</h2><p>
				Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus.</p> 
				</div>
				<div class="span3">
				<h2>Bérletek</h2><p>
				Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. </p>
				</div>
			</div>
		</section>
	</div>
	
	<@loadScripts.loadScripts />
	
</@template.masterTemplate>