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
					<form action="changePass" method="post">
						<div class="form-group">
							<label>Email</label>
							<div class="login-content">
								<input type="text" name="username" id="username" class="form-control" />
								<div class="invalid-feedback">
									<div>Họ và tên không được bỏ trống</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label>Mật khẩu cũ</label>
							<div class="login-content">
								<input type="password" name="password1" id="password1" class="form-control" />
								<div class="invalid-feedback">
									<div>Họ và tên không được bỏ trống</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label>Mật khẩu mới:</label>
							<div class="login-content">
								<input type="password" name="password" id="password"
									class="form-control" />
								<div class="invalid-feedback">
									<div>Email is required</div>
									<div>Email must be a valid email address</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label>Nhập lại mật khẩu mới:</label>
							<div class="login-content">
								<input type="password" name="password" id="password"
									class="form-control" />
								<div class="invalid-feedback">
									<div>Password is required</div>
									<div>Password must be at least 6 characters</div>
								</div>
							</div>
						</div>
						<button type="submit" class="btn btn-primary">Đổi Mật khẩu</button>
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