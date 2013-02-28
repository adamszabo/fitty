$(document).ready(function() {
	$('#googleMap').on('load', function() {
		initialize();
	});

	google.maps.event.addDomListener(window, 'load', initialize);
});

function initialize() {
	var point = new google.maps.LatLng(47.489105, 19.074358);
	var mapProp = {
		center : point,
		zoom : 16,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	new google.maps.Marker({
		position : point,
		map : map,
		title : "Zsír-Szabó Fitnesz"
	});
}