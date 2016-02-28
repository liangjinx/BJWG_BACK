<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>系统管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
  </head>

<body>
<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>

<div class="admin">
	<div class="line-big">
        <div class="xm9">
            <div class="alert">
            	您当前的ip：${sessionScope.session_manager.lastLoginIpStr }，当前系统时间：<jsp:useBean id="now" class="java.util.Date" /><fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" />
            </div>
            <div class="alert">
                <h4>${sessionScope.session_manager.username }当前信息</h4>
                <p class="text-gray padding-top">
                	所属角色:
                	<c:if test="${sessionScope.session_manager.managerId==1}">
                		<label class="button border-blue button-little">系统管理员</label>
                	</c:if>
                	<c:if test="${sessionScope.session_manager.managerId!=1}">
                	<c:forEach items="${roles}" var="role">
                		<label class="button border-blue button-little">${role.roleName}</label>&nbsp;
                	</c:forEach>
                	</c:if>
                </p>
                <p class="text-gray padding-top">
                	可以操作以下资源:
                	<c:if test="${sessionScope.session_manager.managerId==1}">
                		<label class="button border-blue button-little">所有资源</label>
                	</c:if>
                	<c:if test="${sessionScope.session_manager.managerId!=1}">
                	<c:forEach items="${rescs}" var="resc">
                		<label class="button border-blue button-little">${resc.rescName}</label>&nbsp;
                	</c:forEach>
                	</c:if>
                </p>
            </div>
        </div>
    </div>
    <br />
</div>
</body>
</html>
