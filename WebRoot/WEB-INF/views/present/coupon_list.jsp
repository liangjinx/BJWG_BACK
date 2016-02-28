<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>猪肉券赠送记录</title>
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
	
		var present_user = $.trim($("#present_user").val());
		var presented_user = $.trim($("#presented_user").val());
	    
	    var url = "<common:WebRoot/>/v/present/coupon/list?";
	    url+="startTime="+valSt+"&endTime="+valEnd+"&filter_LIKES_t.present_user="+present_user+"&filter_LIKES_t.presented_user="+presented_user;
	    window.location.href = encodeURI(url);
	}
	/**
	 * 用户余额列表管理
	 */
	function present_edit(type,id){
	
		var action = "";
		switch (type) {
		case 1://新增
			action = basePath + "/v/present/editInit";
			break;
		case 2://修改
			action = basePath + "/v/present/editInit";
			$("#presentId").val(id);
			break;
		case 3://单个删除
			action = basePath + "/v/present/delete";
			if(confirm("是否确定删除记录？")){
				$("#presentId").val(id);
			}else{
				return ;
			}
			break;
		case 4://多个删除
			action = basePath + "/v/present/delete";
			var idList = $(":input[name=idList]:checked");
			if(idList.length < 1){
				alert("请选择要删除的记录");
				return ;
			}else{
				if(!confirm("是否确定批量删除记录？")){
					return;
				}
			}
			break;
		default:
			break;
		}
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
        
        	<form action="<common:WebRoot/>/v/present/coupon/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="presentId" name="presentId" value=""/>
        		<input type="hidden" id="type" name="type" value="${type}"/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>猪肉券赠送记录</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left: 100px;">
			            	赠送人昵称:<input type="text" style="width: 150px;" value="${param['filter_LIKES_t.present_user']}"
								name="filter_LIKES_t.present_user" id="present_user">
							&nbsp;被赠送人昵称:<input type="text" style="width: 150px;" value="${param['filter_LIKES_t.presented_user']}"
								name="filter_LIKES_t.presented_user" id="presented_user">
							&nbsp;时间 :从
							<input type="text" style="width: 100px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<!-- 
				       		<man:hasPermission name="present_edit_init">
				       			<input type="button" class="button button-small border-blue" value="新增" onclick="javascript:present_edit(1);"/>&nbsp;
				       		</man:hasPermission>
				       		 -->
				       		 <!-- 
				       		<man:hasPermission name="present_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="present_edit(4);"/>
				            </man:hasPermission>
				             -->
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col" style="width:5%">ID </th>
							<th>赠送人昵称</th>
							<th>被赠送人昵称</th>
							<th>数量</th>
							<th>单价</th>
							<th>总额</th>
							<th>时间</th>
							<th>类型</th>
							<th>赠送关联id</th>
							<th>被赠送关联id</th>
							<man:hasPermission name="present_delete">
								<th>操作</th>
							</man:hasPermission>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td>&nbsp;&nbsp;${u.presentId}</td>
								<td>
									<c:if test="${u.presentUserId!=null && u.presentUser!=''}">
										<a href="<common:WebRoot/>/v/user/view?userId=${u.presentUserId}" class="text-sub">${u.presentUser}</a>
									</c:if>
									<c:if test="${u.presentUserId==null || u.presentUser==''}">无</c:if>
								</td>
								<td>
									<c:if test="${u.presentedUserId!=null && u.presentedUser!=''}">
										<a href="<common:WebRoot/>/v/user/view?userId=${u.presentedUserId}" class="text-sub">${u.presentedUser}</a>
									</c:if>
									<c:if test="${u.presentedUserId==null || u.presentedUser==''}">无</c:if>
								</td>
								<td>${u.presentNum}</td>
								<td><fmt:formatNumber value="${u.price}" pattern="0.00"/></td>
								<td><fmt:formatNumber value="${u.totalMoney}" pattern="0.00"/></td>
								<td><fmt:formatDate value='${u.ctime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td>
									<c:if test="${u.type == 1 }">送猪仔</c:if>
			   						<c:if test="${u.type == 2 }">送猪肉券</c:if>
								</td>
								<td>${u.presentRelationId}</td>
								<td>${u.presentedRelationId}</td>
								<td>
									<!-- 
									<man:hasPermission name="present_edit_init">
										<a class="button border-blue button-little" href="javascript:present_edit(2,${u.presentId});">编辑</a>
									</man:hasPermission>
									 -->
									 <!-- 
									<man:hasPermission name="present_delete">
				            			<a class="button border-yellow button-little" href="javascript:present_edit(3,${u.presentId});">删除</a>
				            		</man:hasPermission>
				            		 -->
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