<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>角色列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		//搜索
		function search(){
			var url = "<common:WebRoot/>/v/roleList.do?";
	    
	    	var name = $("#queryRoleName").val();
	    	url+="queryRoleName="+name;
	    	window.location.href = encodeURI(url);
		}
	</script>
	
  </head>

<body>
<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/roleList.do" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="roleId" name="roleId" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>角色列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            
			            <strong style="padding-left: 100px;">
				       		角色名：<input type="text" name="queryRoleName" id="queryRoleName" value="${requestScope.queryRoleName}" size="15" maxlength="15" />&nbsp;
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="role_edit_init">
				            	<input type="button" class="button button-small border-green" value="添加角色" onclick="roleEidt(1)"/>&nbsp;
				            </man:hasPermission>
				            <man:hasPermission name="role_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="roleEidt(4)"/>&nbsp;
				            </man:hasPermission>
			            </strong>
			       		
			        </div>
			        <table class="table table-hover">
			        	<tr>
				        	<th style="width:5%">选择</th>
				        	<th style="width:5%">角色名</th>
				        	<th style="width:50%">用户</th>
				        	<th style="width:5%">描述</th>
				        	<th style="width:15%">操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td style="width:5%" nowrap="nowrap"><input type="checkbox" name="idList" value="${l.roleId }" /></td>
				            	<td style="width:5%" nowrap="nowrap">${l.roleName }</td>
				            	<td style="width:50%;"><div style="width:400px;display: block;word-wrap: break-word;">${requestScope.map[l.roleId + '']}</div></td>
				            	<td style="width:5%" nowrap="nowrap">${l.description }</td>
				            	<td style="width:15%" nowrap="nowrap">
				            		<man:hasPermission name="role_edit_init">
				            			<a class="button border-blue button-little" href="#" onclick="roleEidt(2,${l.roleId });return false;">编辑</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="role_user_list">
				            			<a class="button border-blue button-little" href="#" onclick="roleEidt(5,${l.roleId });return false;">用户分配</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="role_resc_list">
				            			<a class="button border-blue button-little" href="#" onclick="roleEidt(6,${l.roleId });return false;">权限分配</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="role_child_list">
				            			<a class="button border-blue button-little" href="#" onclick="roleEidt(7,${l.roleId });return false;">分配可选角色</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="role_delete">
				            			<a class="button border-yellow button-little" href="#" onclick="roleEidt(3,${l.roleId });return false;">删除</a>
				            		</man:hasPermission>
				            	</td>
				            </tr>
				            
				            <c:set var="len" value="${len + 1 }"></c:set>
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
