<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!-- 公用的页面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>公用的页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="head/head.jsp"></jsp:include>
  </head>
<body>
<input type="hidden" id="webRoot" value="<common:WebRoot/>" />
<div class="lefter">
    <!-- <div class="logo"><a href="#" target="_blank"><img src="<common:WebRoot/>/resources/modular/images/haizhand.png" alt="后台管理系统" /></a></div> -->
    <div class="logo">&nbsp;<img src="<common:WebRoot/>/resources/images/logo.png" alt="后台管理系统" /></div>
</div>
<div class="righter nav-navicon" id="admin-nav">
    <div class="mainer">
        <div class="admin-navbar">
            <span class="float-right">
                <a class="button button-little bg-yellow" href="javascript:if(confirm('确认退出登录?'))window.location='<common:WebRoot/>/nv/logout.do'">注销登录</a>
            </span>
            <ul class="nav nav-inline admin-nav">
            	<c:forEach items="${NAV_CONST_VIEW}" var="view">
            		<c:if test="${view.key.code!='' && view.key.code!= null}">
            			<man:hasPermission name="${view.key.code}">
	            			<li <c:if test="${fn:contains(view.key.status,requestScope.nav)}"> class="active"</c:if>>
		            			<a href="<common:WebRoot/>${view.key.link}" class="${view.key.icon}"> ${view.key.name}</a>
		            			<ul>
		            				<c:forEach items="${view.value}" var="value">
		            				<c:if test="${value.code!='' && value.code!= null}">
	            						<man:hasPermission name="${value.code}">
	            							<li <c:if test="${fn:contains(value.status,requestScope.nav)}"> class="active"</c:if>><a href="<common:WebRoot/>${value.link}">${value.name}</a></li>
	            						</man:hasPermission>
	            					</c:if>
	            					<c:if test="${value.code=='' || value.code== null}">
	            						<li <c:if test="${fn:contains(value.status,requestScope.nav)}"> class="active"</c:if>><a href="<common:WebRoot/>${value.link}">${value.name}</a></li>
	            					</c:if>
		            				</c:forEach>
		            			</ul>
		            		</li>
            			</man:hasPermission>
            		</c:if>
	            	<c:if test="${view.key.code=='' || view.key.code== null}">
	            		<li <c:if test="${fn:contains(view.key.status,requestScope.nav)}"> class="active"</c:if>>
	            			<a href="<common:WebRoot/>${view.key.link}" class="${view.key.icon}"> ${view.key.name}</a>
	            			<ul>
	            				<c:forEach items="${view.value}" var="value">
            						<c:if test="${value.code!='' && value.code!= null}">
            						<man:hasPermission name="${value.code}">
            							<li <c:if test="${fn:contains(value.status,requestScope.nav)}"> class="active"</c:if>><a href="<common:WebRoot/>${value.link}">${value.name}</a></li>
            						</man:hasPermission>
	            					</c:if>
	            					<c:if test="${value.code=='' || value.code== null}">
	            						<li <c:if test="${fn:contains(value.status,requestScope.nav)}"> class="active"</c:if>><a href="<common:WebRoot/>${value.link}">${value.name}</a></li>
	            					</c:if>
	            				</c:forEach>
	            			</ul>
		            	</li>
	            	</c:if>
	            		
            	</c:forEach>
            </ul>
        </div>
        <div class="admin-bread">
            <span>您好，${sessionScope.session_manager.username }，上次登录时间：<fmt:formatDate value="${sessionScope.session_manager.lastLoginTime }" pattern="yyyy/MM/dd  HH:mm:ss" />&nbsp;&nbsp;</span>
            <ul class="bread">
            	<li><a href="<common:WebRoot/>/v/home.do" class="icon-home"> 开始</a></li>
            	<c:forEach items="${NAV_CONST}" var="item">
            		<c:if test="${requestScope.nav == item.key }">
            			<c:set value="${ fn:split(item.value, '-') }" var="names" />
            			<c:forEach items="${names}" var="name">
	            			<li>${name}</li>
	            		</c:forEach>
            		</c:if>
            	</c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>