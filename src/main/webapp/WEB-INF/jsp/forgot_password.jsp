<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/register.css" rel="stylesheet">
<script src="static/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<body>
	<section class="form-register">
		<div class="background-register">
			<div class="row">
				<div class="col-lg-6">
					<img src="static/img/register.jpg" class="img-bg-register">
				</div>

				<div class="col-lg-6">
					<div>
						<p class="text-danger">${error}</p>
					</div>
					<div>
						<p class="text-warning">${msg}</p>
					</div>
					<form action="forgot-password" method="post">

						<div class="form-group">
							<label>Nhap email cua ban:</label>
							<div class="login-content">
								<input type="text" name="username" id="username"
									placeholder="Nhập email của bạn" class="form-control" />
							</div>
						</div>

						<button type="submit" class="btn btn-primary">Quen mat
							khau</button>
						<hr>
						<div class="text-center">
							<a class="content-text" href="/login">Quay lại đăng nhập</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>