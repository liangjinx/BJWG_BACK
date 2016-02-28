<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>管理员管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		//冻结/解冻服务
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
				        url: '<common:WebRoot/>/v/managerFreeze.do',  
				        data: "id="+id,
				        success: function(data){
				        	window.location.href = '<common:WebRoot/>/v/managerList.do';
						}  
				});  
			}
		}
		//搜索
		function search(){
			var url = "<common:WebRoot/>/v/managerList.do?";
	    
	    	var phone = $("#phone").val();
	    	var userName = $("#userName").val();
	   		var managerId = $("#managerIdSear").val();
	    
	    	url+="filter_LIKES_manager.phone="+phone+"&filter_LIKES_manager.USERNAME="+userName+"&filter_EQI_manager.MANAGER_ID="+managerId;
	    	window.location.href = encodeURI(url);
		}
	</script>
  </head>
<body>
<!-- head -->
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/managerList.do" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="managerId" name="managerId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>管理员列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
			            	电话:<input type="text" style="width: 150px;" value="${param['filter_LIKES_manager.phone']}"
								name="filter_LIKES_manager.phone" id="phone">
				       		&nbsp;管理员名称：<input type="text" name="filter_LIKES_manager.USERNAME" id="userName" value="${param['filter_LIKES_manager.USERNAME']}" size="15" maxlength="15" />
				       		&nbsp;管理员ID：<input type="text" name="filter_EQI_manager.MANAGER_ID" id="managerIdSear" value="${param['filter_EQI_manager.MANAGER_ID']}" size="15" maxlength="15" />
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="manager_edit_init">
				           		<input type="button" class="button button-small border-green" value="添加管理员" onclick="managerEidt(1)"/>
				            </man:hasPermission>
				            <man:hasPermission name="manager_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="managerEidt(4)"/>
				            </man:hasPermission>
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col" width="70">选择</th>
							<th>管理员名称</th>
							<th>中文名称</th>
							<th>电话</th>
							<th>最后登入时间</th>
							<th>创建时间</th>
							<th>状态</th>
							<man:hasPermission name="manager_edit_init_OR_manager_freeze_OR_manager_child_OR_manager_delete">
								<th>操作</th>
							</man:hasPermission>
 						</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="u">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${u.managerId}" />&nbsp;&nbsp;${u.managerId}</td>
								<td>
									<a href="<common:WebRoot/>/nv/managerView?userId=${u.managerId}" class="text-sub">${u.username}</a>
								</td>
								<td>${u.chineseName}</td>
								<td>${u.phone }</td>
								<td>
									<fmt:formatDate value='${u.lastLoginTime }' pattern='yyyy-MM-dd HH:mm' />
								</td>
								<td>
									<fmt:formatDate value='${u.registerTime }' pattern='yyyy-MM-dd HH:mm' /> 
								</td>
								<td>
									<c:if test="${u.status == 0 }">已冻结 </c:if>
			   						<c:if test="${u.status == 1 }">正在使用</c:if>
								</td>
								<td>
									<man:hasPermission name="manager_edit_init">
										<a class="button border-blue button-little" href="#" onclick="managerEidt(2,${u.managerId});return false;">编辑</a>
									</man:hasPermission>
									<man:hasPermission name="manager_freeze">
				            		<a class="button 
				            			<c:if test="${u.status == 1 }"> border-yellow</c:if>
				            			<c:if test="${u.status == 0 }"> border-blue</c:if> 
				            			button-little" href="#" onclick="freeze(${u.managerId},${u.status});return false;">
				            			<c:if test="${u.status == 0 }">解冻 </c:if>
			   							<c:if test="${u.status == 1 }">冻结</c:if>
				            		</a>
				            		</man:hasPermission>
				            		
				            		<man:hasPermission name="manager_child">
				            			<a class="button border-blue button-little" href="#" onclick="managerEidt(5,${u.managerId});return false;">分配子账号</a>
				            		</man:hasPermission>
				            		
				            		<man:hasPermission name="manager_delete">
				            			<a class="button border-yellow button-little" href="#" onclick="managerEidt(3,${u.managerId});return false;">删除</a>
				            		</man:hasPermission>
								</td>
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