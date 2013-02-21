$(document).ready(function(){
	
	$('.detailSlimScroll').slimScroll({
	    height: '75px',
	    width: '90%',
	});
	
	$('#missesModal #max-quantity-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/hianyzo/max";
		$form.submit();
	});
	
	$('#missesModal #delete-missing-products-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/hianyzo/torol";
		$form.submit();
	});
	
	$('#missesModal #delete-basket-button').on('click', function() {
		$form = $(this).closest('form')[0];
		$form.action += "aruhaz/1/deleteBasket";
		$form.submit();
	});
	
	paginatorCheck();
});

function paginatorCheck(){
	$('previousPage').removeClass('disabled');
	$('nextPage').removeClass('disabled');
	
	if($('#actualPageNumber').html()==1){
		$('#previousPage').addClass('disabled');
	}
}