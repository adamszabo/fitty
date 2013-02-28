$(document).ready(function() {

	// Full name validation
	$('#userDataUpdate-fullName').on('focusout', function() {
		var fullName = $(this).val();
		if (!validateStringLength(fullName, 6)) {
			$('#userDataUpdate-fullNameAlert').show();
		}
	});
	$('#userDataUpdate-fullName').on('focusin', function() {
		$('#userDataUpdate-fullNameAlert').hide();
	});

	// Email validation
	validateEmailWhenLeaveInput();
	$('#userDataUpdate-email').on('focusin', function() {
		$('#userDataUpdate-emailAlert').hide();
	});

	// Password validation and send modified user details
	$('#userDataUpdateButton').on('click', function() {
		if (isAllInputsFilled('userDataUpdateForm')) {
			$('#userDataUpdate-inputsAlert').hide();
			validateAndSendUserData();
		} else {
			$('#userDataUpdate-inputsAlert').show();
		}
	});

	// hide notification area
	$('input').on('focusin', function() {
		$('#notificationDiv').hide();
	});

	// validate and send new password with valid old password
	$('#userPasswordUpdateButton').on('click', function() {
		$('#oldPasswordAlert').hide();
		if (isAllInputsFilled('userPasswordUpdateForm')) {
			$('#userPasswordUpdate-inputsAlert').hide();
			checkNewPasswordAndItsConfirmationInput();
		} else {
			$('#userPasswordUpdate-inputsAlert').show();
		}
	});

});

function checkNewPasswordAndItsConfirmationInput() {
	var newPassword = $('#newPassword').val();
	var newPasswordRe = $('#newPasswordRe').val();

	if (validateStringLength(newPassword, 4)) {
		if (newPassword == newPasswordRe) {
			$('#newPasswordAlert').hide();
			checkOldPasswordAndSendNew();
		} else {
			$('#newPasswordAlert>span').html('A két megadott jelszó nem egyezik meg!');
			$('#newPasswordAlert').show();
		}
	} else {
		$('#newPasswordAlert>span').html('A jelszó legalább 4 karakter hosszúságú legyen!');
		$('#newPasswordAlert').show();
	}

}

function checkOldPasswordAndSendNew() {
	var username = $('#userPasswordUpdate-username').val();
	var oldPassword = $('#oldPassword').val();

	$.ajax({
		url : $('#defaultUrl').val() + 'modositas/validatePass',
		type : 'POST',
		data : ({
			username : username,
			password : oldPassword
		}),
		success : function(data) {
			if (data) {
				sendNewPassword();
			} else {
				$('#oldPasswordAlert').show();
			}
		}
	});
}

function sendNewPassword() {
	var newPassword = $('#newPassword').val();

	$.ajax({
		url : $('#defaultUrl').val() + 'modositas/jelszo',
		type : 'POST',
		data : ({
			newPassword : newPassword
		}),
		success : function(data) {
			if (data) {
				$('#notificationDiv>span').html('Jelszavának módosítása sikeresen megtörtént!');
				$('#notificationDiv').removeClass('alert-error').addClass('alert-success').show();
			} else {
				$('#notificationDiv>span').html('Jelszó módosítása sikertelen!');
				$('#notificationDiv').removeClass('alert-success').addClass('alert-error').show();
			}
		}
	});
}

function sendModifiedData() {
	var fullName = $('#userDataUpdate-fullName').val();
	var email = $('#userDataUpdate-email').val();
	var mobile = $('#userDataUpdate-mobile').val();

	if (validateStringLength(fullName, 5) && RegistrationValidator.validateEmailWithRegular(email)) {
		$.ajax({
			url : $('#defaultUrl').val() + 'modositas/adatok',
			type : 'POST',
			data : ({
				fullName : fullName,
				email : email,
				mobile : mobile
			}),
			success : function(data) {
				if (data) {
					$('#notificationDiv>span').html('Adatok módosítása sikeresen megtörtént!');
					$('#notificationDiv').removeClass('alert-error').addClass('alert-success').show();
				} else {
					$('#notificationDiv>span').html('Adatok módosítása sikertelen!');
					$('#notificationDiv').removeClass('alert-success').addClass('alert-error').show();
				}
			}
		});
	}
}

function validateEmailWhenLeaveInput() {
	$('#userDataUpdate-email').on('focusout', function() {
		var email = $(this).val();
		if (!RegistrationValidator.validateEmailWithRegular(email)) {
			$('#userDataUpdate-emailAlert').show();
		}
	});
}

function validateAndSendUserData() {
	var username = $('#userDataUpdate-username').val();
	var password = $('#userDataUpdate-confirmPassword').val();

	$.ajax({
		url : $('#defaultUrl').val() + 'modositas/validatePass',
		type : 'POST',
		data : ({
			username : username,
			password : password
		}),
		success : function(data) {
			if (data) {
				$('#userDataUpdate-passwordAlert').hide();
				$('#userDataUpdate-confirmPassword').val('');
				sendModifiedData();
			} else {
				$('#userDataUpdate-passwordAlert').show();
			}
		}
	});
}

function validateStringLength(fullName, minLength) {
	return minLength <= fullName.length;
}

function isAllInputsFilled(formId) {
	var $inputs = $('#' + formId + ' .control-group .controls input');
	var filled = true;
	$inputs.each(function(index, value) {
		var input = $(value);
		if (input.val() == "")
			filled = false;
	});
	return filled;
}