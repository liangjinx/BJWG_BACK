<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>日志列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
	//时间选择控件
	function selectDate() { 
		WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
    } 
	/**
	*搜索
	*/
	function search(){
		
		var valSt = $("#startTime").val();
		var valEnd = $("#endTime").val();
		if(valSt!="" && valEnd!=""){
	    	var date1 = new Date(valSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date2 = new Date(valEnd.replace(/\-/g, "\/"));
	    	var a = (date2 - date1)/(1000*60);
		    if (a < 0) {
		        alert("开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
	
		var url = "<common:WebRoot/>/v/log/list?";
	    
	    var opeId = $("#opeIdSear").val();
	    var ope_module = $("#ope_module_sear").val();
	    var ope_type = $("#ope_type_sear").val();
	    var operator_name = $("#operator_name_sear").val();
	    var currentPage = $("#currentPage").val();
	    var pageCount = $("#pageCount").val();

	    url+="startTime="+valSt+"&endTime="+valEnd+"&filter_EQI_op.OPE_ID="+opeId+"&filter_LIKES_op.OPE_MODULE="+ope_module+"&filter_LIKES_op.OPE_TYPE="+ope_type+"&filter_LIKES_op.OPERATOR_NAME="+operator_name+"&currentPage="+currentPage+"&pageCount="+pageCount;
	    window.location.href = encodeURI(url);
	}
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/log/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="opeId" name="opeId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>日志列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
			            	ID:<input type="text" style="width: 150px;" value="${param['filter_EQI_op.OPE_ID']}"
								name="filter_EQI_op.OPE_ID" id="opeIdSear">
							&nbsp;操作人：<input type="text" style="width: 120px;" value="${param['filter_LIKES_op.OPERATOR_NAME']}"
								name="filter_LIKES_op.OPERATOR_NAME" id="operator_name_sear">
							&nbsp;操作模块：<select name="filter_LIKES_op.OPE_MODULE" id="ope_module_sear" style="width: 80px;">
								<option value="">所有</option>
								<c:forEach var="per" items="${opemodules}" >
									<option value="${per}" <c:if test="${param['filter_LIKES_op.OPE_MODULE']==per}">selected</c:if> >${per}</option>
								</c:forEach>
							</select>
				       		&nbsp;操作类型：<select name="filter_LIKES_op.OPE_TYPE" id="ope_type_sear" style="width: 80px;">
				       			<option value="">所有</option>
								<c:forEach var="per" items="${opetypes}" >
									<option value="${per}" <c:if test="${param['filter_LIKES_op.OPE_TYPE']==per}">selected</c:if> >${per}</option>
								</c:forEach>
								</select>
							&nbsp;操作时间 :从
							<input type="text" style="width: 100px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
								
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col">ID</th>
							<th>操作人</th>
							<th>操作类型</th>
							<th>操作模块</th>
							<th>操作时间</th>
							<th>操作对象ID</th>
							<th>操作对象</th>
							<th>描述</th>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${u.opeId}" />&nbsp;&nbsp;${u.opeId}</td>
								<td><a href="<common:WebRoot/>/nv/managerView?userId=${u.operator}" class="text-sub">${u.operatorName}</a></td>
								<td>${u.opeType}</td>
								<td>${u.opeModule}</td>
								<td><fmt:formatDate value='${u.opeTime}' pattern='yyyy-MM-dd HH:mm' /></td>
								<td>${u.opeObjectId}</td>
								<td>${u.opeObject}</td>
								<td>${u.remark}</td>
				            </tr>
			        	</c:forEach>
			        </table>
			        <jsp:include page="/WEB-INF/views/page.jsp" flush="true"></jsp:include>
			    </div>
			 </form>
        </div>
    </div>
</div>
</body>
</html>