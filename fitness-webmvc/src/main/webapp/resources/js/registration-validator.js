var RegistrationValidator = function() {
	
	function validateRegistration() {
		emailAddress = $('#email').val();
		isValidEmail = validateEmailWithRegular(emailAddress);
		isValidPasswordRe = validatePasswords();
		isAllInputFilled = isAllInputsFilled();

		if (isAllInputFilled) {
			$('#inputs').hide();
		} else {
			$('#inputs').show();
		}

		if (isValidEmail && isValidPasswordRe && isAllInputFilled) {
			checkUsernameAndEmail($('#username').val(), emailAddress);
		}
	}
	
	function checkUsernameAndEmail(username,emailUser){
//		console.log(location.hostname+':'+location.port+'/'+location.pathname.split("/")[1]+'/'+ "checkUser");
		 $.ajax({
			 url: $('#checkUserUrl').val(),
	         type: 'POST',
	         data: ({
	             username: username,
	             email: emailUser
	         }),
	         beforeSend: function () {
	         },
	         success: function (data){
	        	 ajaxCheckSuccess(data);
	         } 
	     });
	}
	
	function ajaxCheckSuccess(data){
//		console.log(data.existUsername+', '+data.existEmail);
		$('#usernameAlert').hide();
		$('#emailCheckAlert').hide();
		
		if(!data.existUsername && !data.existEmail){
			$('#registrationForm').submit();
		}
		else{
			if(data.existUsername){
				$('#usernameAlert').show();
			}
			if(data.existEmail){
				$('#emailCheckAlert').show();
			}
		}
	}
	
	function validateEmailWithRegular(emailAddress){
		var emailReg = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
		if(!emailReg.test(emailAddress)){
			$('#emailAlert').show();
			return false;
		}
		else{
			$('#emailAlert').hide();
			return true;
		}
	}

	function validatePasswords(){
		password=$('#password').val();
		passwordRe=$('#passwordRe').val();
		
		if(password!=passwordRe){
			$('#passwordAlert').show();
			return false;
		}
		else{
			$('#passwordAlert').hide();
			return true;
		}
	}

	function isAllInputsFilled(){
		$inputs=$('#registrationForm .control-group .controls input');
		filled=true;
		$inputs.each(function(index,value){
			input=$(value);
			if(input.val()=="")
				filled=false;
		});
		return filled;
	}
	
	return{
		validateRegistration: validateRegistration
	};
}();