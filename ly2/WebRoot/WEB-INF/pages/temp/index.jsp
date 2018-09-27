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
	width: 80px;
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
			/* setInterval(function() {
				loadingData();
			}, 1000); */
	
		})
		//"<span class='itemList'>"+dataSet[i].code+"</span>"
	
		function loadingData() {
			$("#list").empty();
			var _html = "";
			$.ajax({
				url : "stockAll",
				beforeSend : function(data) {},
				success : function(data) {
					var dataSet = data.total;
					_html += "<li class='list-group-item'><span class='itemList'>代码</span><span class='itemList'>名称</span><span class='itemList'>价格</span><span class='itemList'>幅度</span><span class='itemList'>最低</span><span class='itemList'>昨收</span></li>";
					for (var i = 0; i < dataSet.length; i++) {
						_html += "<li class='list-group-item'><span class='itemList'>" + dataSet[i].code + "</span><span class='itemList'>" + dataSet[i].name + "</span><span class='itemList'>" + dataSet[i].current_price + "</span><span class='itemList'>" + dataSet[i].risePrice + "</span><span class='itemList'>" + dataSet[i].minprice + "</span><span class='itemList'>" + dataSet[i].prevclose + "</span></li>";
					}
	
					$("#list").append(_html);
				}
			});
	
	
		}
	</script>

</body>
</html>
