$(document).ready(function() {

	if($('#trainers-selector .trainer-name-li:first').length>0){
		FitnessCalendar.init('fitness-calendar',true);
		
		$('#trainers-selector .trainer-name-li').on('click', function(e){
			$this=$(this);
			$('#trainers-selector .trainer-name-li').removeClass('active');
			$this.addClass('active');
			
			var selectedTrainerUsername=$this.data('username');
			var forTrainer=isLoggedInTrainerCalendarSelected(selectedTrainerUsername);
			
			FitnessCalendar.setTrainersNameAndCalendarEntries(selectedTrainerUsername, forTrainer);
		});
		$('#trainers-selector .trainer-name-li:first').click();
	}
	
	$('#reservedModal #delete-basket-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/1/deleteBasket";
		$form.submit();
	});
	
	$('#reservedModal #delete-reserved-trainings-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "edzesek/torles/foglalt";
		$form.submit();
	});
	
});

function isLoggedInTrainerCalendarSelected(selectedTrainerUsername){
	var usernameSpan=$('#username-security-span');
	var result = false;
	if(usernameSpan.html()==undefined){
		result = false;
	}
	else{
		if(usernameSpan.html()==selectedTrainerUsername){
			result = true;
		}
	}
	return result;
}