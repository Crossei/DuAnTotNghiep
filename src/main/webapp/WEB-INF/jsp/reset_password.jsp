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
<script type="text/javascript" src="static/js/index.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<body>

	
	<section class="form-register">
		<div class="background-register">
			<div class="row">
				<div class="col-lg-6">
					<img src="static/img/register.jpg" class="img-bg-register">
				</div>

				<div class="col-lg-6">
					<form action="reset-password" method="post">
						<input type="hidden" name="token" value="${token}"/>
						<div class="form-group">
							<label>Nhập mật khẩu mới:</label>
							<div class="login-content">
								<input type="password" name="password" id="password"
									class="form-control" required autofocus
									placeholder="Nhập mật khẩu mới của bạn" />
							</div>
							<br />
							<div class="login-content">
								<input type="password" name="rpassword" id="rpassword"
									class="form-control" required autofocus
									placeholder="Nhập lại mật khẩu của bạn"
									oninput="checkPasswordMatch(this)"
									 />
							</div>
						</div>

						<button type="submit" class="btn btn-primary">Thay đổi
							mật khẩu</button>
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