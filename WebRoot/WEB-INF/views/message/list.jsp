<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>用户消息列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
	$(document).ready(function(){
		commonTip("${requestScope.msg}");
	});
	//时间选择控件
	function selectDate() { 
		WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
    } 
	//搜索
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
	
		var url = "<common:WebRoot/>/v/message/list?";
	    var user_id = $("#user_id_sear").val();
	    var message_id = $("#message_id_sear").val();
	    var username = $.trim($("#username_sear").val());
	    var message_type = $("#message_type_sear").val();
	    var relation_type = $("#relation_type_sear").val();
	    var currentPage = $("#currentPage").val();
	    var pageCount = $("#pageCount").val();

	    url+="startTime="+valSt+"&endTime="+valEnd+"&filter_EQI_t.USER_ID="+user_id+"&filter_LIKES_u.username="+username+"&filter_EQI_t.MESSAGE_ID="+message_id+"&filter_EQS_t.message_type="+message_type+"&filter_EQS_t.relation_type="+relation_type+"&currentPage="+currentPage+"&pageCount="+pageCount;
	    window.location.href = encodeURI(url);
	}
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/message/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="messageId" name="messageId" value=""/>
        		<input type="hidden" id="user_id_sear" name="filter_EQI_t.USER_ID" value="${param['filter_EQI_t.USER_ID']}"/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong><c:if test="${username!=null && username!=''}">【<a href="<common:WebRoot/>/nv/user/view?userId=${param['filter_EQI_t.USER_ID']}" class="text-sub">${username}</a>】的</c:if>消息列表</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left:20px;">
			            	ID:<input type="text" style="width: 150px;" value="${param['filter_EQI_t.MESSAGE_ID']}"
								name="filter_EQI_t.MESSAGE_ID" id="message_id_sear">
							&nbsp;用户名称：<input type="text" style="width: 120px;" value="${param['filter_LIKES_u.username']}"
								name="filter_LIKES_u.username" id="username_sear">
							&nbsp;消息类型：<select name="filter_EQS_t.message_type" id="message_type_sear" style="width:180px;">
								<option value="">所有</option>
								<c:forEach var="per" items="${sysStatusMap}" >
									<option value="${per.key}" <c:if test="${param['filter_EQS_t.message_type']==per.key}">selected</c:if> >${per.value}</option>
								</c:forEach>
							</select>
							&nbsp;关联类型：<select name="filter_EQS_t.relation_type" id="relation_type_sear" style="width:180px;">
								<option value="">所有</option>
								<c:forEach var="per" items="${relationStatusMap}" >
									<option value="${per.key}" <c:if test="${param['filter_EQS_t.relation_type']==per.key}">selected</c:if> >${per.value}</option>
								</c:forEach>
							</select>
							&nbsp;发送时间 :从
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
							<th>用户名称</th>
							<th>发送时间 </th>
							<th>消息类型</th>
							<th>关联类型</th>
							<th>关联ID</th>
							<th>内容</th>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td>&nbsp;&nbsp;${u.messageId}</td>
								<td>
									<c:if test="${u.userId!=null && u.userId!='' && u.username!='' && u.username!='无'}">
				   						<a href="<common:WebRoot/>/v/user/view?userId=${u.userId}" class="text-sub">${u.username}</a>
				   					</c:if>
					   				<c:if test="${u.userId==null || u.userId=='' || u.username=='' || u.username=='无'}">
					   					无
					   				</c:if>
								</td>
								<td><fmt:formatDate value='${u.ctime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td>
									<c:forEach items="${sysStatusMap}" var="s">
										<c:if test="${u.messageType==s.key}">${s.value}</c:if>
									</c:forEach>
								</td>
								<td>
									<c:forEach items="${relationStatusMap}" var="s">
										<c:if test="${u.relationType==s.key}">${s.value}</c:if>
									</c:forEach>
								</td>
								<td>${u.relationId}</td>
								<td>${u.content}</td>
				            </tr>
			        	</c:forEach>
			        </table>
			        <jsp:include page="../page.jsp" flush="true"></jsp:include>
			    </div>
			 </form>
        </div>
    </div>
</div>
</body>
</html>