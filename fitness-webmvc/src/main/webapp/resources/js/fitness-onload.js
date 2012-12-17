$(document).ready(function() {
	//activate appropriate link	
	$('#navDiv ul li a').removeClass('active');
	$('#navDiv ul li a[href="'+location.pathname+'"]').parent().addClass('active');
	
	//show and hide dialog to login
	var isLoginFormHided=true;
	$('#loginFormButton').click(function(e){
		if(isLoginFormHided){
			$('#loginForm').fadeIn('slow');
			$('#loginFormButton i').removeClass('icon-chevron-down');
			$('#loginFormButton i').addClass('icon-chevron-up');
			isLoginFormHided=false;
		}
		else{
			$('#loginForm').fadeOut('slow');
			$('#loginFormButton i').removeClass('icon-chevron-up');
			$('#loginFormButton i').addClass('icon-chevron-down');
			isLoginFormHided=true;
		}
	});
	
	$('.detailSlimScroll').slimScroll({
	    height: '75px',
	    width: '90%',
	});
	
	percentedHeight=window.innerHeight*0.65;
	$('.pageSlimScroll').slimScroll({
		height: ''+percentedHeight + 'px'
	});
	
	
	paginatorCheck();
	
	$('#registrationButton').click(function(e){
		$('#registrationDialog').modal('show');
	});
	
	$('#reRegistrationDialogButton').click(function(e){
		$('#registrationDialog').modal('show');
	});
	
	$('#registrationDialog').on("hide", function(){
		deleteFormDatas("registrationForm");
		$('.alert').hide();
	});
	
	$('#registerButton').on("click",validateRegistration);
	
});

function validateRegistration(){
		emailAddress=$('#email').val();
		isValidEmail=validateEmailWithRegular(emailAddress);
		isValidPasswordRe=validatePasswords();
		isAllInputFilled=isAllInputsFilled();
		
		if( isAllInputFilled){
			$('#inputs').hide();
		}
		else{
			$('#inputs').show();
		}
		
		if(isValidEmail && isValidPasswordRe && isAllInputFilled){
			checkUsernameAndEmail($('#username').val(),emailAddress);
		}
}

function checkUsernameAndEmail(username,emailUser){
	console.log('ajax post with username: '+username+' and email: '+emailUser);
	 $.ajax({
		 url: location.href + "checkUser",
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
	console.log(data.existUsername+', '+data.existEmail);
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

function paginatorCheck(){
	console.log($('#actualPageNumber').html());
	$('previousPage').removeClass('disabled');
	$('nextPage').removeClass('disabled');
	
	if($('#actualPageNumber').html()==1){
		$('#previousPage').addClass('disabled');
	}
}

function deleteFormDatas(id){
	$(':input','#'+id)
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
};