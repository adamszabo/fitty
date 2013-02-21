$(document).ready(function(){
	// /admin/jogosultsagok userDelete confirm dialog
	$('#deleteUserConfirmButton').on("click", function(){
		$form=document.getElementById(deleteUserFormId);
		$form.submit();
	});
	
	$('.submit-change-button').on('click', function() {
		$changeRoleDiv = $(this).closest('.modal-body').children('.change-role');
		setRoleInput('client');
		setRoleInput('trainer');
		setRoleInput('recepcionist');
		setRoleInput('productAdmin');
		setRoleInput('systemAdmin');
		$changeRoleDiv.children('.changeRoleForm').submit();
	});
	
	$('.change-role .role-btn').on('click', function() {
		if($(this).hasClass('active')) {
			$(this).removeClass('btn-success');
			$(this).addClass('btn-danger');
		} else {
			$(this).removeClass('btn-danger');
			$(this).addClass('btn-success');
		}
	});
	
	$('.submit-ban-button').on('click', function() {
		$(this).closest('.modal-body').children('.deleteUserForm').submit();
	});
});

function setRoleInput(roleName) {
	if($changeRoleDiv.children('div.btn-group').children('.'+ roleName +'-btn').hasClass('active')==true) {
		$changeRoleDiv.children('div.btn-group').children('.'+ roleName +'-input').val(true);
	}
}