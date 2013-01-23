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
	
	$('#newProductModal').on("hide", function(){
		deleteFormDatas("newProductModal");
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
	
	$('.open-newMembershipModal').on("click", function() {
		$('#occassinally-membershipType').click();
	});
	
	$('.open-updateMembershipModal').on("click", function () {
		id = $(this).data('id');
		$("#updateMembershipModal #id").val(id);
	    $("#updateMembershipModal #detail").val($('#membership-' + id + ' .detail').html());
	    $("#updateMembershipModal #maxNumberOfEntries").val($('#membership-' + id + ' .entries').html());
	    $("#updateMembershipModal #expireDateInDays").val($('#membership-' + id + ' .expire').html());
	    $("#updateMembershipModal #price").val($('#membership-' + id + ' .price').html().replace("&nbsp;", ""));
	    $('#updateMembershipModal').modal('show');
	    if($('#membership-' + id + ' .intervally').html()=="igen") {
	    	$('#updateMembershipModal #intervally-membershipType').click();
	    } else {
	    	$('#updateMembershipModal #occassinally-membershipType').click();
	    }
	});
	
	$('#updateMembershipModal #occassinally-membershipType').on("click", function () {
		$('#updateMembershipModal #maxNumberOfEntries').attr("disabled", false);
		$('#updateMembershipModal #expireDateInDays').attr("disabled", true);
		$('#updateMembershipModal #isIntervally').val('false');
	});
	
	$('#updateMembershipModal #intervally-membershipType').on("click", function () {
		$('#updateMembershipModal #expireDateInDays').attr("disabled", false);
		$('#updateMembershipModal #maxNumberOfEntries').attr("disabled", true);
		$('#updateMembershipModal #isIntervally').val('true');
	});
	
	$('#newMembershipModal #occassinally-membershipType').on("click", function () {
		$('#newMembershipModal #maxNumberOfEntries').attr("disabled", false);
		$('#newMembershipModal #expireDateInDays').attr("disabled", true);
		$('#newMembershipModal #isIntervally').val('false');
	});
	
	$('#newMembershipModal #intervally-membershipType').on("click", function () {
		$('#newMembershipModal #expireDateInDays').attr("disabled", false);
		$('#newMembershipModal #maxNumberOfEntries').attr("disabled", true);
		$('#newMembershipModal #isIntervally').val('true');
	});
	
	//beleptetes
	$('#searchType').selectBoxIt({
		aggressiveChange: true
	});
	
	$('#searchTypeSelectBoxIt').addClass("btn btn-primary");
	
	$('.details-button').on("click", function(e){
		$detailsTr=$('#'+this.id+'-details-tr');
		if($detailsTr.css('display')=='none'){
			$detailsTr.fadeIn('fast');
			$('#'+this.id+' i').removeClass('icon-chevron-down').addClass('icon-chevron-up');
		}
		else{
			$detailsTr.fadeOut('fast');
			$('#'+this.id+' i').removeClass('icon-chevron-up').addClass('icon-chevron-down');
		}
	});
	
});
	
//After invalid login drop down login dialog
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