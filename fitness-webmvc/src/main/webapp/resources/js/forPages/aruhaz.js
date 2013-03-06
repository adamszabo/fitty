var subSocket;

$(document).ready(function() {

	$('.detailSlimScroll').slimScroll({
		height : '75px',
		width : '90%',
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

	subscribeWebSocketAndHandleMessage();

	$('#broadcastButton').on('click', function() {
		subSocket.push('asdfasdfasdfasdfasfd');
		return;
	});

	$('#broadcastStopButton').on('click', function() {
		removeAdvertismentDivFromHeader();
	});

});

function paginatorCheck() {
	$('previousPage').removeClass('disabled');
	$('nextPage').removeClass('disabled');

	if ($('#actualPageNumber').html() == 1) {
		$('#previousPage').addClass('disabled');
	}
}

function subscribeWebSocketAndHandleMessage() {
	var socket = $.atmosphere;
	var request = new $.atmosphere.AtmosphereRequest();
	request.transport = 'websocket';
	request.url = $('#defaultUrl').val() + "aruhaz/reklam/reklamok";
	request.contentType = "application/json";
	request.fallbackTransport = 'streaming';

	request.onMessage = function(response) {
		buildTemplate(response);
		// console.log('invoking onMessage');
	};

	subSocket = socket.subscribe(request);

	function buildTemplate(response) {

		if (response.state = "messageReceived") {

			var data = response.responseBody;

			if (data) {

				try {
					var result = $.parseJSON(data);
					console.log(result.msg);

					rebuildAdvertismentDiv(result.msg);

				} catch (error) {
					console.log("An error ocurred: " + error);
				}
			} else {
				console.log("response.responseBody is null - ignoring.");
			}
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