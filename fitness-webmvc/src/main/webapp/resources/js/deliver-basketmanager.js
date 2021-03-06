var BasketManager=function(){
	
	function deliverBasket(basketId, defaultUrl){
		$.ajax({
			 url: defaultUrl+'rendeles/atad',
	         type: 'POST',
	         data: ({
	             basketId: basketId
	         }),
	         success: function (data){
	        	 console.log(data);
	        	 generateMembershipsOnPage(data, defaultUrl);
	        	 $('#'+basketId+'-orders-tr').hide();
	        	 $('#'+basketId+'-modal').modal('hide');
	         } 
	     });
	}
	
	function stornoBasket(basketId, defaultUrl){
		$.ajax({
			 url: defaultUrl+'rendeles/torol',
	         type: 'POST',
	         data: ({
	             basketId: basketId
	         }),
	         success: function (data){
	        	 deleteBasketFromPage(data, basketId);
	         } 
	     });
	}
	
	function deleteBasketFromPage(data, basketId){
		if(data){
			$('#'+basketId+'-orders-tr').hide();
		}
	}
	
	function generateMembershipsOnPage(data, defaultUrl){
		if(data.length>0){
			$.get(defaultUrl+'resources/membershipRowTemplate.htm',function(template){
				userName=data[0].basket.user.username;
				tbody=$('#'+userName+'-memberships-tr tbody');
				
				// if the user do not have any valid membership, add table
				if(tbody.html()==undefined){
					generateTbody(username, defaultUrl);
					tbody=$('#'+userName+'-memberships-tr tbody');
				}
				
				// add each membership to a row and to the tbody
				for(var i=0;i<data.length;++i){
					templateData=generateTemplateDataFromItem(data[i], defaultUrl);
					html=Mustache.to_html(template, templateData);
					tbody.append(html);
				};
			});
		}
	}
	
	
	function generateTemplateDataFromItem(element, defaultUrl){
		var numberOfEntriesOrExpireDate;
		
		if(element.isIntervally){
			numberOfEntriesOrExpireDate = formatDate(new Date(element.expireDate));
		}
		else{
			numberOfEntriesOrExpireDate = (element.maxNumberOfEntries - element.numberOfEntries) + ' alkalom';
		}
		console.log('numberOfEntriesOrExpireDate??::::'+numberOfEntriesOrExpireDate);
		
		return {
			targetUrl: defaultUrl+'beleptetes/leptet',
			fullName: element.basket.user.fullName,
			id: element.id,
			type: element.type,
			numberOfEntries: element.numberOfEntries,
			validationExp: numberOfEntriesOrExpireDate,
			price: element.price
		};
	}
	
	function generateTbody(username, defaultUrl){
		tableTemplate='<td colspan="6"><table class="table table-hover table-bordered"><thead><tr><th colspan="6">Bérletek</th></tr><tr><th>Azonosító</th><th>Típus</th><th>Belépések száma</th><th>Érvényesség</th><th>Ára</th><th>Művelet</th></tr></thead><tbody></tbody></table></td>';
		$('#'+userName+'-memberships-tr').html(tableTemplate);
	}
	
	function formatDate(date){
		return date.getFullYear() +'.'+(date.getMonth()+1) +'.' +date.getDate();
	}
	
	return{
		deliverBasket : deliverBasket,
		stornoBasket : stornoBasket
	};
}();