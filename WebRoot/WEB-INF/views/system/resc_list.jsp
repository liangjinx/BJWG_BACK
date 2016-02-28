<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>资源列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		//搜索
		function search(){
			var url = "<common:WebRoot/>/v/rescList.do?";
	    
	    	var rescName = $("#rescName").val();
	    	var rescCode = $("#rescCode").val();
	   		var rescGroup = $("#rescGroup").val();
	    
	    	url+="filter_LIKES_resc.RESC_NAME="+rescName+"&filter_LIKES_resc.RESC_CODE="+rescCode+"&filter_EQI_resc.RESC_GROUP="+rescGroup;
	    	window.location.href = encodeURI(url);
		}
	</script>
  </head>

<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/rescList.do" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="rescId" name="rescId" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>资源列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
				       		资源名：<input type="text" name="filter_LIKES_resc.RESC_NAME" id="rescName" value="${param['filter_LIKES_resc.RESC_NAME']}" size="15" maxlength="15" />&nbsp;
				       		资源编号：<input type="text" name="filter_LIKES_resc.RESC_CODE" id="rescCode" value="${param['filter_LIKES_resc.RESC_CODE']}" size="15" maxlength="15" />&nbsp;
				       		资源组别：
				       		<select name="filter_EQI_resc.RESC_GROUP" id="rescGroup" style="width: 80px;">
			            		<option value="">所有</option>
			            		<c:forEach var="pers" items="${permissionScops}" >
									<option value="${pers.key}" <c:if test="${param['filter_EQI_resc.RESC_GROUP']==pers.key}">selected</c:if> >${pers.value}</option>
								</c:forEach>
							</select>
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="resc_edit_init">
				            	<input type="button" class="button button-small border-green" value="添加资源" onclick="rescEidt(1)"/>
				            </man:hasPermission>
				            <man:hasPermission name="resc_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="rescEidt(4)"/>
				            </man:hasPermission>
			            </strong>
			            <!--<input type="button" class="button button-small border-blue" value="回收站" />
			        --></div>
			        <table class="table table-hover">
			        	<tr>
				        	<th width="50">选择</th>
				        	<th width="120">资源名</th>
				        	<th width="100">资源编号</th>
				        	<th width="*">资源链接</th>
				        	<th width="80">资源组别</th>
				        	<th width="100">操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${l.rescId }" /></td>
				            	<td>${l.rescName }</td>
				            	<td>${l.rescCode }</td>
				            	<td>${l.rescLink }</td>
				            	<td>
				            	<c:forEach var="pers" items="${permissionScops}" >
									<c:if test="${l.rescGroup==pers.key}">${pers.value}</c:if>
								</c:forEach>
				            	</td>
				            	<td>
				            		<man:hasPermission name="resc_edit_init">
				            			<a class="button border-blue button-little" href="#" onclick="rescEidt(2,${l.rescId });return false;">编辑</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="resc_delete">
				            			<a class="button border-yellow button-little" href="#" onclick="rescEidt(3,${l.rescId });return false;">删除</a>
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
