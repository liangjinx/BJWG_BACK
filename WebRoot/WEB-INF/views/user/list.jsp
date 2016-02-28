<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>用户列表</title>
    
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
		//冻结/解冻用户
		function freeze(id,status){
			var showText = "";
			if(status==1){
				showText = "冻结";
			}else if(status==0){
				showText = "解冻";
			}
			if(confirm('确认'+showText+'该系统用户吗?')){
				$.ajax({  
				   		type: "POST",  
				        url: '<common:WebRoot/>/v/user/freeze',  
				        data: "id="+id,
				        success: function(data){
				        	$("#allProjectForm").submit();
						}  
				});  
			}
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
			var url = "<common:WebRoot/>/v/user/list?";
	    
	    	var phone = $("#phone").val();
	    	var userName = $("#userName").val();
	    	var userId = $("#userIdSear").val();
	    	var userType = $("#userType").val();
	       
	    	url+="startTime="+valSt+"&endTime="+valEnd+"&userType="+userType+"&filter_LIKES_t.phone="+phone+"&filter_LIKES_t.USERNAME="+userName+"&filter_EQI_t.USER_ID="+userId;
	    	window.location.href = encodeURI(url);
		}
	//用户导出
    function exportData(){
    
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
			var phone = $("#phone").val();
	    	var userName = $("#userName").val();
	    	var userId = $("#userIdSear").val();
	    	var userType = $("#userType").val();
	    
		$("button[name=btn]").attr("disabled",true);
		$.ajax({
			type: "POST",
			url: "<common:WebRoot/>/v/user/checkExport",
			dataType: "text",
			data:{"filter_EQI_t.USER_ID":userId,"filter_LIKES_t.phone":phone,"filter_LIKES_t.USERNAME":userName,"userType":userType,"startTime":valSt,"endTime":valEnd},
			success: function(data){
				if(data!="" && data!="操作成功" && data!=null && data.toString()!="" && data.charAt(data.length - 1)== "!"){
					alert(data);
					return; 
				}else{
					$("#allProjectForm").attr("action","<common:WebRoot/>/v/user/export");
	    			$("#allProjectForm").submit();
	    			$("#allProjectForm").attr("action","<common:WebRoot/>/v/user/list");
				}
			}
		});
	}
		//查看该用户的订单
		function view_order(id){
			var url = "<common:WebRoot/>/v/order/list";
	    	$("#order_user_id").val(id);
	    	$("#viewForm").attr("action",url).submit();
			disabledBtn();
		}
		
		//时间选择
		function selectDate() { 
			WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
	    } 
	    function userEidt(type,id){
			var action = "";
			switch (type) {
				case 1://新增
					action = basePath + "/v/user/editInit";
					break;
				case 2://修改
					action = basePath + "/v/user/editInit";
					$("#userId").val(id);
					break;
				case 3://单个删除
					action = basePath + "/v/user/delete";
					if(confirm("用户删除将删除用户的其他关联信息，是否确定删除")){
						$("#userId").val(id);
					}else{
						return ;
					}
					break;
				case 4://多个删除
					action = basePath + "/v/user/delete";
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
					case 5:
					action = basePath + "/v/user/myPreorder";		
	    	        $("#userId").val(id);
					break;
				default:
					break;
			}
			$("#allProjectForm").attr("action",action).submit();
			disabledBtn();
		}
		
		//冻结/解冻用户
		function freeze(id,status){
			var showText = "";
			if(status==1){
				showText = "冻结";
			}else if(status==0){
				showText = "解冻";
			}
			if(confirm('确认'+showText+'该系统用户吗?')){
				$.ajax({  
				   		type: "POST",  
				        url: '<common:WebRoot/>/v/user/freeze',  
				        data: "userId="+id,
				        success: function(data){
				        	$("#allProjectForm").submit();
						}  
				});  
			}
		}
	</script>
  </head>

<body>

<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/user/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="userId" name="userId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>用户列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
			            	用户ID：<input type="text" name="filter_EQI_t.USER_ID" id="userIdSear" value="${param['filter_EQI_t.USER_ID']}" size="15" maxlength="15" />
			            	&nbsp;手机号:<input type="text" style="width: 150px;" value="${param['filter_LIKES_t.phone']}"
								name="filter_LIKES_t.phone" id="phone">
				       		&nbsp;用户名称：<input type="text" name="filter_LIKES_t.USERNAME" id="userName" value="${param['filter_LIKES_t.USERNAME']}" size="15" maxlength="15" />
				       		&nbsp;用户类型：<select name="userType" id="userType" style="width: 140px;">
			            		<option value="">所有</option>
								<option value="1" <c:if test="${userType== '1' }">selected="selected"</c:if>>八戒平台用户</option>
                  				<option value="2" <c:if test="${userType== '2' }">selected="selected"</c:if>>微信用户</option>
									<option value="2" <c:if test="${userType== '3' }">selected="selected"</c:if>>企业用户</option>
							</select>
							&nbsp;注册时间 :从
							<input type="text" style="width: 100px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
				       		<input type="button" class="button button-small border-blue" name="btn" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="user_export">
				       			<input type="button" class="button button-small border-blue" value="导出" onclick="exportData();"/>&nbsp;
				       		</man:hasPermission>
				       		<man:hasPermission name="user_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="userEidt(4)"/>
				            </man:hasPermission>
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col">ID </th>
							<th>用户名</th>
							<th>用户昵称</th>
							<th>手机号码</th>
							<th>最后登入时间</th>
							<th>注册时间</th>
							<th>用户类型</th>
							<man:hasPermission name="user_edit_init_OR_user_delete">
								<th>操作</th>
							</man:hasPermission>
 						</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="u">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${u.userId}" />&nbsp;&nbsp;${u.userId}</td>
								<td><a href="<common:WebRoot/>/v/user/view?userId=${u.userId}" class="text-sub">${u.username}</a></td>
								<td>${u.nickname}</td>
								<td>${u.phone}</td>
								<td>
									<fmt:formatDate value='${u.lastLoginTime }' pattern='yyyy-MM-dd HH:mm:ss' />
								</td>
								<td>
									<fmt:formatDate value='${u.registerTime }' pattern='yyyy-MM-dd HH:mm:ss' /> 
								</td>
								<td>
								<c:choose>
								  <c:when test="${u.flag=='2'}">
								  企业用户
								  </c:when>
								<c:otherwise>
								
								<c:if test="${u.userType=='1'}">八戒平台用户</c:if>
									<c:if test="${u.userType=='2'}">微信用户</c:if>
								</c:otherwise>
								</c:choose>
									
								</td>
								<td>
									<man:hasPermission name="user_edit_init">
										<a class="button border-blue button-little" href="javascript:userEidt(2,${u.userId});">编辑</a>
									</man:hasPermission>
									<man:hasPermission name="user_delete">
				            			<a class="button border-yellow button-little" href="javascript:userEidt(3,${u.userId});">删除</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="user_freeze">
				            			<a class="button 
					            			<c:if test="${u.status == 1 }"> border-yellow</c:if>
					            			<c:if test="${u.status == 0 }"> border-blue</c:if> 
					            			button-little" href="javascript:freeze(${u.userId},${u.status});">
					            			<c:if test="${u.status == 0 }">解冻 </c:if>
				   							<c:if test="${u.status == 1 }">冻结</c:if>
				            			</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="order_list">
				            			<a class="button border-blue button-little" href="javascript:view_order(${u.userId});">我的订单</a>
				            		
				            		</man:hasPermission>
				            		<a class="button border-green button-little" href="javascript:userEidt(5,${u.userId});">预抢</a>
								</td>
				            </tr>
			        	</c:forEach>
			        </table>
			        <jsp:include page="../page.jsp" flush="true"></jsp:include>
			    </div>
			 </form>
			 <form action="<common:WebRoot/>/v/order/list" id="viewForm" name="viewForm">
			 	<input type="hidden" id="order_user_id" name="filter_EQI_t.USER_ID" value=""/>
			 </form>
        </div>
    </div>
</div>
</body>
</html>