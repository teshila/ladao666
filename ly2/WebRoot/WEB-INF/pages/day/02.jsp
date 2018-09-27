<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 指标库列表 -->

<!-- 数据表 -->
<table class="easyui-datagrid" title="指标库列表" fit="true" fitColumns="true" toolbar="#toolbar" id="List3">
</table>
<!-- /数据表 -->

<!-- 数据表工具栏 -->
<div class="toolbar" id="toolbar">
	<div class="search-div">
		<label>指标名称：</label> <input type="text" class="easyui-textbox" /> <label>是否公用：</label> <select class="easyui-combobox" data-options="editable:false">
			<option value="0">是</option>
			<option value="1">否</option>
		</select> <label>是否自定义：</label> <select class="easyui-combobox" data-options="editable:false">
			<option value="0">是</option>
			<option value="1">否</option>
		</select> <a href="#" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
	<div class="ctrl-div">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBtn">新增</a> <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">编辑</a> <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a> <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true">打印</a> <a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true">导入</a> <a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
<!-- /数据表工具栏 -->

<!-- 弹出框 -->
<div class="easyui-dialog" title="新增/编辑" iconCls="icon-save" modal="true" closed="true" buttons="#dlg-btns" id="dlg">
	<form id="fm" method="post">
		<div class="fitem">
			<label>指标名称：</label> <input class="easyui-textbox" value="利润率" /> <label>是否公用：</label> <select class="easyui-combobox">
				<option value="0">是</option>
				<option value="1">否</option>
			</select>
		</div>
		<div class="fitem">
			<label>维护部门：</label> <select class="easyui-combobox">
				<option value="">财务部</option>
				<option value="">人事部</option>
			</select> <label>维护角色：</label> <select class="easyui-combobox">
				<option value="0">主办会计</option>
				<option value="1">行政总监</option>
				<option value="2">总经理</option>
			</select>
		</div>
		<div class="fitem">
			<label>审核部门：</label> <select class="easyui-combobox">
				<option value="">总经办</option>
				<option value="">行政部</option>
			</select>
		</div>
		<div class="fitem">
			<label>备注说明：</label> <input class="easyui-textbox" multiline="true" style="width:452px;height:52px;" value="考核项目的实际利润情况" />
		</div>
	</form>
</div>
<!-- /弹出框 -->

<!-- 弹出框按钮组-->
<div id="dlg-btns">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="saveBtn">保存</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="cancelBtn">取消</a>
</div>
<!-- /弹出框 按钮组-->

<script type="text/javascript">
	$(function() {
		//打开弹出框
		$("#addBtn, #editBtn").click(function(e) {
			e.preventDefault();
			$("#dlg").dialog("open");
		});
		//关闭弹出框
		$("#saveBtn, #cancelBtn").click(function(e) {
			e.preventDefault();
			$("#dlg").dialog("close");
		});
	});
</script>

<script type="text/javascript">
	$(function() {

		loadingData();
		setInterval(function() {
			//loadingData();
		}, 1000)
	});




	function loadingData() {
		$('#List3').datagrid({
			url : 'stockGrid02',
			width : $(window).width() - 10,
			methord : 'post',
			height : $(window).height() - 35,
			fitColumns : true,
			sortName : 'code',
			sortOrder : 'desc',
			idField : 'code',
			striped : true, //奇偶行是否区分
			singleSelect : false, //单选模式
			rownumbers : true, //行号
			pageSize : 30, //每页显示的记录条数，默认为10 
			pageList : [ 30, 40, 80, 120 ], //选择一页显示多少数据               
			pagination : true, //在DataGrid控件底部显示分页工具栏。
			rowStyler : function(index, record) {
				if (record.current_price > record.prevclose) {
					return 'color:red';
				} else if (record.current_price == record.prevclose) {
					return 'color:grey';
				} else {
					return 'color:green';
				}
			},
			onClickRow : function(value, rec) {
				alert(rec.name)
			},
			columns : [ [
				{
					field : 'ok',
					checkbox : true
				},
				{
					field : 'code',
					title : '代码',
					width : 120,
					
				},
				{
					field : 'name',
					title : '名称',
					width : 280
				},
				{
					field : 'prevclose',
					title : '昨收',
					width : 80,
					align : 'right'
				},
				{
					field : 'current_price',
					title : '当前',
					width : 80,
					align : 'right'
				},
				{
					field : 'maxprice',
					title : '本日最高',
					width : 80,
					align : 'right'
				},
				{
					field : 'minprice',
					title : '本日最低',
					width : 80,
					align : 'right'
				},
				{
					field : 'open_price',
					title : '开盘',
					width : 80,
					align : 'right'
				},
				{
					field : 'marketType',
					title : '市场代码',
					width : 80,
					align : 'right'
				},
				{
					field : 'risePrice',
					title : '幅度',
					width : 80,
					align : 'right'
				},
				{
					field : 'stockPinYin',
					title : '简拼',
					width : 80,
					align : 'right'
				},
				{
					field : 'op',
					title : '编辑',
					width : 100,
					align : 'right',
					formatter : formatOpt
				},
				{
					field : 'operate',
					title : '删除',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						var paramvalue = JSON.stringify(row.code);
						var str = '<a href="javascript:;" title="编辑" class="easyui-tooltip" '
							+ 'onclick=editInfor(' + paramvalue + ')>'
							+ '<img src="static/images/delete.png" class="operate-button"></a>';
						return str;
					}
				}
			] ]
		});
	}


	function formatDisplayColor(val, row, index) {
		if (val > row.prevclose) {
			return '<span style="color:red;">' + val + '</span>';
		} else {
			return val;
		}
	}


	function formatOpt(value, row, index) {
		var paramvalue = JSON.stringify(row.code);
		return '<a href="javascript:;" onclick=editInfor(' + paramvalue + ')><img src="static/images/edit.png" class="operate-button"></a>';
	}

	function editInfor(paramvalue) {
		alert("内容" + paramvalue);
	}
</script>

