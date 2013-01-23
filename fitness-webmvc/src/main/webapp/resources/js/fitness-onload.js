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
			$('#j_username').focus();
			isLoginFormHided=false;
		}
		else{
			$('#loginForm').fadeOut('slow');
			$('#loginFormButton i').removeClass('icon-chevron-up');
			$('#loginFormButton i').addClass('icon-chevron-down');
			isLoginFormHided=true;
		}
	});
	
	checkLoginErrors();
	
	$('.detailSlimScroll').slimScroll({
	    height: '75px',
	    width: '90%',
	});
	
	$('.datepicker').datepicker({
	    format: 'yyyy-mm-dd'
	});
	
	$('.trainer-select-radio-button').on('click', function(e) {
		$('.trainer-select-radio-button').removeClass("active");
		$(e.target).addClass("active");
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
	
	$('#registerButton').on("click",RegistrationValidator.validateRegistration);
	
	// /admin/jogosultsagok userDelete confirm dialog
	var deleteUserFormId=null;
	$('.deleteUserForm').on("submit", function(e){
		$('#deleteUserConfirmDialog').modal('show');
		deleteUserFormId=e.srcElement.id;
		return false;
	});
	
	$('#deleteUserConfirmButton').on("click", function(){
		$form=document.getElementById(deleteUserFormId);
		$form.submit();
	});
	
	
	$('.open-updateMembershipModal').on("click", function () {
	    var membershipId = $(this).data('id');
	    var membershipDetail = $(this).data('detail');
	    console.log(membershipDetail);
	    $("#id").val( membershipId);
	    $("#detail").val( membershipDetail );
	    $('#updateMembershipModal').modal('show');
	});
	
});



function checkLoginErrors(){
	errorDialog=$('#loginErrorDialog');
	if(errorDialog.html()!=undefined){
		$('#loginFormButton').click();
		$('#j_username').focus();
	}
}

function paginatorCheck(){
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