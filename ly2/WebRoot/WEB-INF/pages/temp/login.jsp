<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<base href="<%=basePath%>">
<title>My JSP 'login.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
body {
	background: url("../img/1.jpg");
	animation-name: myfirst;
	animation-duration: 12s;
	/*变换时间*/
	animation-delay: 2s;
	/*动画开始时间*/
	animation-iteration-count: infinite;
	/*下一周期循环播放*/
	animation-play-state: running;
	/*动画开始运行*/
}

@
keyframes myfirst { 0% {
	background: url("../img/1.jpg");
}

34%
{
background
:url
("../img/2
.jpg
");
}
67%
{
background
:url
("../img/3
.jpg
");
}
100%
{
background
:url
("../img/1
.jpg
");
}
}
.form {
	background: rgba(255, 255, 255, 0.2);
	width: 400px;
	margin: 120px auto;
}
/*阴影*/
.fa {
	display: inline-block;
	top: 27px;
	left: 6px;
	position: relative;
	color: #ccc;
}

input[type="text"], input[type="password"] {
	padding-left: 26px;
}

.checkbox {
	padding-left: 21px;
}
</style>
</head>
<body>

	<div class="container">
		<div class="form row">
			<div class="form-horizontal col-md-offset-3" id="login_form">
				<h3 class="form-title">LOGIN</h3>
				<div class="col-md-9">
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i> <input class="form-control required" type="text" placeholder="Username" id="username" name="username" autofocus="autofocus" maxlength="20" />
					</div>
					<div class="form-group">
						<i class="fa fa-lock fa-lg"></i> <input class="form-control required" type="password" placeholder="Password" id="password" name="password" maxlength="8" />
					</div>
					<div class="form-group">
						<label class="checkbox"> <input type="checkbox" name="remember" value="1" />记住我
						</label>
					</div>
					<div class="form-group col-md-offset-9">
						<button type="submit" class="btn btn-success pull-right" name="submit">登录</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
