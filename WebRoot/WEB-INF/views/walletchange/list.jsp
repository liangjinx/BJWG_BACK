<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>余额变更记录列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<link rel="stylesheet" href="<common:WebRoot/>/resources/jquery/jquery-ui-1.8.4.custom.css">
	<script src="<common:WebRoot/>/resources/jquery/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		//删除左右两端的空格
		function trim(str){ 
			return str.replace(/(^\s*)|(\s*$)/g, "");
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
		var url = "<common:WebRoot/>/v/walletchange/list?";
	    
	    var username = $.trim($("#username_sear").val());
	    var change_type = $.trim($("#change_type_sear").val());
	    var currentPage = $("#currentPage").val();
	    var pageCount = $("#pageCount").val();
	    
	    url+="startTime="+valSt+"&endTime="+valEnd+"&filter_LIKES_u.username="+username+"&filter_EQS_t.change_type="+change_type+"&currentPage="+currentPage+"&pageCount="+pageCount;
	    window.location.href = encodeURI(url);
	}
	//日期选择框
	function selectDate() { 
		WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
    } 
    /**
	 * 提现记录查看
	 */
	function withdraw_view(id){
		var action = "<common:WebRoot/>/v/withdraw/view";
		$("#relationId").val(id);
		$("#allProjectForm").attr("action",action).submit();
		$("button[name=btn]").attr("disabled",true);
	}
	/**
	 * 退款记录查看
	 */
	function refund_view(id){
		var action = "<common:WebRoot/>/v/refund/view";
		$("#refundApplyId").val(id);
		$("#allProjectForm").attr("action",action).submit();
		$("button[name=btn]").attr("disabled",true);
	}
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/walletchange/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="walletChangeId" name="walletChangeId" value=""/>
        		<input type="hidden" id="relationId" name="relationId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>余额变更记录列表</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left: 100px;">
			            	用户名称:<input type="text" style="width: 150px;" value="${param['filter_LIKES_u.username']}"
								name="filter_LIKES_u.username" id="username_sear">
				       		&nbsp;变更类型:<select name="filter_EQS_t.change_type" id="change_type_sear" style="width: 150px;">
								<option value="">--请选择--</option>
								<option value="1" <c:if test="${param['filter_EQS_t.change_type']=='1'}"> selected</c:if>>在线支付</option>
								<option value="2" <c:if test="${param['filter_EQS_t.change_type']=='2'}"> selected</c:if>>收入</option>
								<option value="3" <c:if test="${param['filter_EQS_t.change_type']=='3'}"> selected</c:if>>提现</option>
								<option value="4" <c:if test="${param['filter_EQS_t.change_type']=='4'}"> selected</c:if>>支出</option>
								<option value="5" <c:if test="${param['filter_EQS_t.change_type']=='5'}"> selected</c:if>>逾期扣款</option>
							</select>
				       		&nbsp;变更日期 :从
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
                    		<th scope="col" style="width:5%">ID </th>
							<th>余额ID</th>
							<th>用户名称</th>
							<th>变更前金额</th>
							<th>变更金额</th>
							<th>变更后金额</th>
							<th>变更类型</th>
							<th>变更时间</th>
							<th>提现ID</th>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td>&nbsp;&nbsp;${u.walletChangeId}</td>
				            	<td>${u.walletId}</td>
								<td>
									<c:if test="${u.userId!=null && u.username!='无'}">
										<a href="<common:WebRoot/>/v/user/view?userId=${u.userId}" class="text-sub">${u.username}</a>
									</c:if>
									<c:if test="${u.userId==null || u.username=='无'}">${u.username}</c:if>
								</td>
								<td><fmt:formatNumber value="${u.beforeMoney}" pattern="0.00"/></td>
								<td><fmt:formatNumber value="${u.changeMoney}" pattern="0.00"/></td>
								<td><fmt:formatNumber value="${u.afterMoney}" pattern="0.00"/></td>
								<td>
									<c:if test="${u.changeType == 1 }">在线支付</c:if>
			   						<c:if test="${u.changeType == 2 }">收入</c:if>
			   						<c:if test="${u.changeType == 3 }">提现</c:if>
			   						<c:if test="${u.changeType == 4 }">支出</c:if>
			   						<c:if test="${u.changeType == 5 }">逾期扣款</c:if>
								</td>
								<td><fmt:formatDate value='${u.changeTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td>
									<a href="javascript:withdraw_view(${u.relationId});" class="text-sub">${u.relationId}</a>
								</td>
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