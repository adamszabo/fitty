$(document).ready(function(){
	
	$('.datepicker').datepicker({
	    format: 'yyyy-mm-dd',
	    weekStart : 1
	});
	
	membershipValidation();
});

function membershipValidation() {
	$('.membership-submit-button').on('click', function() {
		if($($(this).closest('tr').children()[2]).children().length == 1){
			choosenDateString = $($($(this).closest('tr').children()[2]).children('.datepicker')[0]).val();
			split = choosenDateString.split('-');
			choosenDate = new Date();
			choosenDate.setFullYear(split[0]);
			choosenDate.setMonth(split[1]-1);
			choosenDate.setDate(split[2]);
			choosenDate.setHours(0, 0, 0, 0);
			membershipToday = new Date();
			membershipToday.setHours(0, 0, 0, 0);
			if(choosenDate.getTime() < membershipToday.getTime()) {
				if($('#membershipNotValid').children().length == 0) {
					$('#membershipNotValid').append('<div class="membership-not-valid alert alert-warning"><span>Múltbeli dátum nem választható.</span></div>');
				}
				return false;
			} else {
				$('#membershipNotValid').children('div').remove();
			}
		}
	});
}