	
		function checkPasswordMatch(fieldConfirmPassword) {
			if (fieldConfirmPassword.value != $("#password").val()) {
				fieldConfirmPassword
						.setCustomValidity("mật khẩu không trùng khớp");
			} else {
				fieldConfirmPassword.setCustomValidity("");
			}
		}
		function checkPass() {
			var pass = document.getElementById("password").value;
			var rpass = document.getElementById("rpassword").value;
			if (pass != rpass) {
				document.getElementById("submit").disabled = true;
				$('.missmatch').html(
						"Entered Password is not matching!! Try Again");
			} else {
				$('.missmatch').html("");
				document.getElementById("submit").disabled = false;
			}
		}
	