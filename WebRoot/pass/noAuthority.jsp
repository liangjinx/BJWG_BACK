<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>主菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
  </head>
<body>
<div class="admin">
	<div class="line-big">
        <div class="xm9">
            <div class="alert">
            	您当前的ip：${sessionScope.session_manager.lastLoginIpStr }，当前系统时间：<jsp:useBean id="now" class="java.util.Date" /><fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" />
            </div>
            <div class="alert alert-yellow" style="height:  100px"><span class="close"></span><strong style="color: red;font-size: 20px;vertical-align: middle;line-height: 50px;">提醒：很抱歉您没有当前权限</strong></div>
        </div>
    </div>
    <br />
</div>
</body>
</html>
