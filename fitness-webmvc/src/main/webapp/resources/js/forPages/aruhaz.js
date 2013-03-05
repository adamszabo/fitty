$(document).ready(function(){
	
	$('.detailSlimScroll').slimScroll({
	    height: '75px',
	    width: '90%',
	});
	
	$('#missesModal #max-quantity-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/hianyzo/max";
		$form.submit();
	});
	
	$('#missesModal #delete-missing-products-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/hianyzo/torol";
		$form.submit();
	});
	
	$('#missesModal #delete-basket-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/1/deleteBasket";
		$form.submit();
	});
	
	paginatorCheck();
	
	push();
});

function paginatorCheck(){
	$('previousPage').removeClass('disabled');
	$('nextPage').removeClass('disabled');
	
	if($('#actualPageNumber').html()==1){
		$('#previousPage').addClass('disabled');
	}
}

function push() {
	console.log('pushhhh');
	var socket = $.atmosphere;
    var request = new $.atmosphere.AtmosphereRequest();
	   request.transport = 'websocket';
	   request.url = "reklamok";
	   request.contentType = "application/json";
	   request.fallbackTransport = 'streaming';
	   
	   request.onMessage = function(response){
	       buildTemplate(response);
	   };
	   
	   var subSocket = socket.subscribe(request);
	 
	   function buildTemplate(response){
	       
	     if(response.state = "messageReceived"){
	       
	    	  var data = response.responseBody;
	 
	        if (data) {
	 
	            try {
	                var result =  $.parseJSON(data);
	                console.log(result);
	 
	            } catch (error) {
	                console.log("An error ocurred: " + error);
	            }
	        } else {
	            console.log("response.responseBody is null - ignoring.");
	        }
	   	}
	}
}