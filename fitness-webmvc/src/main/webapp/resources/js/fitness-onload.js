$(document).ready(function() {
	//activate appropriate link	
	$('#navDiv ul li a').removeClass('active');
	setActiveLinkOnNavBar();
	
	//show and hide dialog to login
	$('#loginFormButton').click(function(e){
		showOrHideLoginForm();
	});
	
	checkLoginErrors();
	
	$('#basketMergingModal').modal('show');
	
	percentedHeight=window.innerHeight*0.65;
	$('.pageSlimScroll').slimScroll({
		height: ''+percentedHeight + 'px'
	});
	
	$('#registrationButton').click(function(e){
		$('#registrationDialog').modal('show');
	});
	
	$('#registrationDialog').on("hide", function(){
		deleteFormDatas("registrationForm");
		$('.alert').hide();
	});
	
	$('#registerButton').on("click",RegistrationValidator.validateRegistration);
	
});

function showOrHideLoginForm(){
	var loginForm=$('#loginForm');
	if(loginForm.css('display')=='none'){
		loginForm.fadeIn('slow');
		$('#loginFormButton i').removeClass('icon-chevron-down');
		$('#loginFormButton i').addClass('icon-chevron-up');
		$('#j_username').focus();
	}
	else{
		loginForm.fadeOut('slow');
		$('#loginFormButton i').removeClass('icon-chevron-up');
		$('#loginFormButton i').addClass('icon-chevron-down');
	}
}

function setActiveLinkOnNavBar(){
	var locationPath=location.href;
	var hashSplittedPath=location.pathname.split('/');
	if(hashSplittedPath.length>2){
		locationPath='/'+hashSplittedPath[1]+'/'+hashSplittedPath[2];
	}
	$('#navDiv ul li a[href="'+locationPath+'"]').parent().addClass('active');
}
	
//After invalid login drop down login dialog
function checkLoginErrors(){
	errorDialog=$('#loginErrorDialog');
	if(errorDialog.html()!=undefined){
		$('#loginFormButton').click();
		$('#j_username').focus();
	}
}

function deleteFormDatas(id){
	$(':input','#'+id)
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
};
