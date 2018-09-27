<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@include file="/inner/pub.jsp" %>

</head>
<body class="easyui-layout" id="layout" style="visibility:hidden;">

	<div region="north" id="header">
		<img src="static/easyui/myImg/logo.png" class="logo" />
		<div class="top-btns">
			<span>欢迎您，管理员</span> <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-lock'">修改密码</a> <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-clear'">退出系统</a>
		</div>
	</div>

	<div region="west" split="true" title="导航菜单" id="naver">
		<div class="easyui-accordion" fit="true" id="navmenu">
			<div title="自选管理">
				<ul class="navmenu">
					<li><a href="#" data-url="data00">我的自选</a></li>
					<li><a href="#" data-url="data01">自选涨</a></li>
					<li><a href="#" data-url="data02">自选平</a></li>
					<li><a href="#" data-url="data03">自选跌</a></li>
				</ul>
			</div>
			<div title="邮件管理">
				<ul class="navmenu">
					<li><a href="#" data-url="stockManage">发送列表</a></li>
					<li><a href="#" data-url="data01">发信配置</a></li>
					<li><a href="#" data-url="data03">待读列表</a></li>
					<li><a href="#" data-url="data03">已读列表</a></li>
					<li><a href="#" data-url="data03">已删除</a></li>
					<li><a href="#" data-url="data03">垃圾箱</a></li>
				</ul>
			</div>
			<div title="系统报表"></div>
			<div title="系统管理"></div>
		</div>
	</div>

	<div region="center" id="content">
		 <div class="easyui-tabs" fit="true" id="tt">
			<div title="欢迎使用" iconCls="icon-ok">
				<div class="easyui-accordion" data-options="fit:true" >
				</div>
			</div> 
		</div> 
	</div>

	<div region="south" id="footer">智投管理决策系统 V1.0</div>

	<script type="text/javascript">
		$(function() {
			//添加新的Tab页
			$("#navmenu").on("click", "a[data-url]", function(e) {
				e.preventDefault();
				var tabTitle = $(this).text();
				var tabUrl = $(this).data("url");
	
				if ($("#tt").tabs("exists", tabTitle)) { //判断该Tab页是否已经存在
					$("#tt").tabs("select", tabTitle);
				} else {
					$("#tt").tabs("add", {
						title : tabTitle,
						href : tabUrl,
						closable : true
					});
				}
				$("#navmenu .active").removeClass("active");
				$(this).parent().addClass("active");
			});
	
			//解决闪屏的问题
			window.setTimeout(function() {
				$("#layout").css("visibility", "visible");
			}, 10);
		});
	</script>
</body>
</html>
