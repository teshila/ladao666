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
<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="bt/css/bootstrap.min.css">
<script type="text/javascript" src="static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="btp/bootstrap-paginator.js"></script>
<style>
.itemList {
	padding:0 20px;
	width:250px;
	display: inline-block;
	text-align: center;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="row clearfix">
					<div class="col-md-2 column">
						
					</div>
					<div class="col-md-8 column">
						<ul class="list-group" id="list" style="min-height: 450px;height: 640px;">
						</ul>
					</div>
					<div class="col-md-2 column"></div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			loadingData();
	
		})
	
		function loadingData() {
			$("#list").empty();
			var _html = "";
			$.ajax({
				url : "getEmails",
				beforeSend : function(data) {},
				success : function(data) {
					var dataSet = data.list;
					_html += "<li class='list-group-item'><span class='itemList'>Email地址</span><span class='itemList'>名称</span><span class='itemList'>是否发送</span><span class='itemList'>操作</span></li>";
					for (var i = 0; i < dataSet.length; i++) {
						_html += "<li class='list-group-item'><span class='itemList'>" + dataSet[i].emailAddress + "</span><span class='itemList'>" + dataSet[i].name + "</span><span class='itemList'>" + dataSet[i].isSender + "</span><span class='	glyphicon glyphicon-trash'></span><span class='glyphicon glyphicon-pencil'></span></li>";
					}
	
					$("#list").append(_html);
				}
			});
	
	
		}
	</script>

</body>
</html>
