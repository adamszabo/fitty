$(document).ready(function() {
			$('#navDiv ul li a').removeClass('active');
			$('#navDiv ul li a[href="'+location.pathname+'"]').parent().addClass('active');
			var isLoginFormHided=true;
			$('#loginForm').hide();
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
		});