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
	
	$('.slimScroll').slimScroll({
	    height: '75px',
	    width: '90%',
	});
	
	paginatorCheck();
	
	$('#registrationButton').click(function(e){
		$('#registrationDialog').modal('show');
	});
	
	$('#registrationDialog').on("hide", function(){
		deleteFormDatas("registrationForm");
		$('.alert').hide();
	});
	
	$('#registerButton').on("click",validateRegistration);
	
});

function validateRegistration(){
		var emailReg = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
		emailAddress=$('#email').val();
		isValidEmail=false;
		if(!emailReg.test(emailAddress)){
			$('#emailAlert').show();
			isValidEmail=false;
		}
		else{
			$('#emailAlert').hide();
			isValidEmail=true;
		}
		
		password=$('#password').val();
		passwordRe=$('#passwordRe').val();
		isValidPasswordRe=false;
		if(password!=passwordRe){
			$('#passwordAlert').show();
			isValidPasswordRe=false;
		}
		else{
			$('#passwordAlert').hide();
			isValidPasswordRe=true;
		}
		
		if(isValidEmail && isValidPasswordRe && isAllInputsFilled()){
			$('#registrationForm').submit();
			$('#inputs').hide();
		}
		else{
			$('#inputs').show();
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