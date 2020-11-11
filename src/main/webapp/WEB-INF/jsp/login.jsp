<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/login.css" rel="stylesheet">
</head>
<body>
	<section class="form-login">
		<div class="background-login">
			<div class="row">
				<div class="col-lg-6">
					<img src="static/img/register.jpg" class="img-fluid" id="img-bg">
				</div>
				<div class="col-lg-6">
					<form action="login" method="post" class="login-group">
						<div class="form-group">
							<label for="email">Email address</label>
							<div class="login-content">
								<input type="email" class="form-control" name="username"
									id="email">

							</div>

						</div>
						<div class="form-group">
							<label for="pwd">Password</label>
							
							<div class="login-content">
								<input type="password" name="password" class="form-control"
									id="pwd">

							</div>
						</div>
						<div class="form-group form-check">
							<input type="checkbox" class="form-check-input"
								id="exampleCheck1"> <label class="form-check-label"
								for="chkremember">Remember password</label> <a href=""
								class="forgot">Quên mật khẩu</a>
						</div>

						<button type="submit" class="btn btn-primary">Login</button>
					</form>

				</div>
			</div>
		</div>


	</section>
	<script src="static/bootstrap/js/jquery-1.11.1.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>