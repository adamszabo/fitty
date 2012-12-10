$(document).ready(function() {
			$('#navDiv ul li a').removeClass('active');
			$('#navDiv ul li a[href="'+location.pathname+'"]').parent().addClass('active');
			$('#loginForm').hide();
			var isLoginFormHided=true;
			$('#loginFormButton').click(function(e){
				if(isLoginFormHided){
					$('#loginForm').show();
					isLoginFormHided=false;
				}
				else{
					$('#loginForm').hide();
					isLoginFormHided=true;
				}
			});
		});