$(document).ready(function() {
	//activate appropriate link	
	$('#navDiv ul li a').removeClass('active');
	
	setActiveMenuOnNavBar();
	
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
	
	$('#basketMergingModal').modal('show');
	
	
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
	
	// /raktar 
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
	
	$('#new-product-button').on('click', function(e) {
		$inputs=$('#newProductModal .input-check');
		filled=true;
		isPriceValid = true;
		$inputs.each(function(index,value){
			input=$(value);
			if(input.val()=="")
				filled=false;
		});
		if(filled) {
			$('#new-product-inputs').hide();
		} else {
			$('#new-product-inputs').show();
		}
		
		if($('#newProductModal #price').val() < 1) {
			$('#new-product-price').show();
			isPriceValid = false;
		} else {
			$('#new-product-price').hide();
			isPriceValid = true;
		}
		if(filled && isPriceValid) {
			$('#newProductModal form').submit();
		}
	});
	
	//beleptetes
	$('#searchType').selectBoxIt({
		aggressiveChange: true
	});
	
	$('#searchTypeSelectBoxIt').addClass("btn btn-primary");
	
	$('.details-button').on("click", function(e){
		$membershipsTr=$('#'+this.id+'-memberships-tr');
		$basketsTr=$('#'+this.id+'-baskets-tr');
		if($membershipsTr.css('display')=='none'){
			$membershipsTr.fadeIn('fast');
			$basketsTr.fadeIn('fast');
			$('#'+this.id+' i').removeClass('icon-chevron-down').addClass('icon-chevron-up');
			$('#'+this.id+'-tr').addClass('info');
		}
		else{
			$membershipsTr.fadeOut('fast');
			$basketsTr.fadeOut('fast');
			$('#'+this.id+' i').removeClass('icon-chevron-up').addClass('icon-chevron-down');
			$('#'+this.id+'-tr').removeClass('info');
		}
	});
	
	$('.basket-order-form').on('submit', function(e){
		BasketManager.deliverBasket(this.id.split('-')[0], this.action);
		return false;
	});
	
	$('.basket-orders-form').on('submit', function(e){
		$('.modal').modal('hide');
		BasketManager.stornoBasket(this.id.split('-')[0], this.action);
		return false;
	});
	
	//edzesek
	if($('#trainers-selector .trainer-name-li:first').length>0){
		FitnessCalendar.init('fitness-calendar',true);
		
		$('#trainers-selector .trainer-name-li').on('click', function(e){
			$this=$(this);
			$('#trainers-selector .trainer-name-li').removeClass('active');
			$this.addClass('active');
			
			var selectedTrainerUsername=$this.data('username');
			var forTrainer=isLoggedInTrainerCalendarSelected(selectedTrainerUsername);
			
			FitnessCalendar.setForTrainer(forTrainer);
			FitnessCalendar.setTrainersTrainingsOnCalendar(selectedTrainerUsername);
		});
		$('#trainers-selector .trainer-name-li:first').click();
	}
	
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
	
});

function setRoleInput(roleName) {
	if($changeRoleDiv.children('div.btn-group').children('.'+ roleName +'-btn').hasClass('active')==true) {
		$changeRoleDiv.children('div.btn-group').children('.'+ roleName +'-input').val(true);
	}
}

function setActiveMenuOnNavBar(){
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