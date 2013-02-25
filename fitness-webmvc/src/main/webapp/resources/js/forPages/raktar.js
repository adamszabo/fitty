$(document).ready(function(){
	
	$('#newProductModal').on("hide", function(){
		deleteFormDatas("newProductModal");
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
	    $("#updateMembershipModal #price").val($('#membership-' + id + ' .price').html().replace("&nbsp;", "").replace(",","").replace(".",""));
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
	
	$('.open-updateTrainingTypeModal').on("click", function () {
		id = $(this).data('id');
		$("#updateTrainingTypeModal #trainerId").val(id);
		$("#updateTrainingTypeModal #trainerName").val($('#trainer-' + id + ' .trainer-name').html());
	    $("#updateTrainingTypeModal #detail").val($('#trainer-' + id + ' .training-type-detail').html());
	    $("#updateTrainingTypeModal #price").val($('#trainer-' + id + ' .training-type-price').html().replace("&nbsp;", "").replace(",","").replace(".",""));
	});
});