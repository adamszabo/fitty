$(document).ready(function() {
	subscribeWebSocketAndHandleMessage();
});

function subscribeWebSocketAndHandleMessage() {
	var socket = $.atmosphere;
	var request = new $.atmosphere.AtmosphereRequest();
	request.transport = 'websocket';
	request.url = $('#defaultUrl').val() + "reklam/feliratkoz";
	request.contentType = "application/json";
	request.fallbackTransport = 'streaming';

	request.onMessage = function(response) {
		buildTemplate(response);
	};

	subSocket = socket.subscribe(request);

	$('#broadcaster').on('click', function() {
		subSocket.push($('#broadcast-input').val());
	});
}

function buildTemplate(response) {
	if (response.state = "messageReceived") {
		var data = response.responseBody;
		if (data) {

			try {
				var result = $.parseJSON(data);
				console.log(result.message);

				rebuildAdvertismentDiv(result.message);

			} catch (error) {
				console.log("An error ocurred: " + error);
			}
		} else {
			console.log("response.responseBody is null - ignoring.");
		}
	}
}

function rebuildAdvertismentDiv(msg) {
	var div = $('#advertismentDiv').html();
	if (div != undefined) {
		$('#advertismentDiv div').trigger('stop');
		$('#advertismentDiv div p').html(msg);
		$('#advertismentDiv div').trigger('start');
	} else {
		addAdvertismentDivToHeader(msg);
	}
}

function addAdvertismentDivToHeader(msg) {
	$('#overview').append('<div id="advertismentDiv"><div><p></p></div></div>');
	$('#advertismentDiv div p').html(msg);
	$('#advertismentDiv div').marquee({
		behavior : "scroll",
		scrollamount : 1,
		direction : "left",
		width : 300
	});
}

function removeAdvertismentDivFromHeader() {
	$('#advertismentDiv div').trigger('stop');
}