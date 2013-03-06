$(document).ready(function() {
	
	subscribe();
});

function subscribe() {
	var socket = $.atmosphere;
    var request = new $.atmosphere.AtmosphereRequest();
	   request.transport = 'websocket';
	   request.url = $('#defaultUrl').val() + "reklam/feliratkoz";
	   request.contentType = "application/json; charset=utf-8";
	   request.fallbackTransport = 'streaming';
	   request.trackMessageLength = true;
	   
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
	   
	   $('#broadcaster').on('click', function(){
		   subSocket.push($('#broadcast-input').val());
	   });
}