$(document).ready(function(){
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
});